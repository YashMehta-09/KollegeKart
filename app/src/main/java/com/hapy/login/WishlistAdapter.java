package com.hapy.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {

    private Context wContext;
    private List<FirebaseUpload> wUploads;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseauth;


    public WishlistAdapter(Context context, List<FirebaseUpload> uploads) {
        wContext = context;
        wUploads = uploads;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(wContext).inflate(R.layout.activity_wishlist_card_view, parent, false);
        return new WishlistAdapter.WishlistViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {

        final FirebaseUpload uploadCurrent = wUploads.get(position);


        holder.mtextViewName.setText(uploadCurrent.getName());
        holder.mtextViewCategory.setText(uploadCurrent.getCategory());
        holder.mtextViewAmount.setText(uploadCurrent.getAmount());
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.mimageView);


        holder.wCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mDatabaseRef = FirebaseDatabase.getInstance().getReference();
                firebaseauth = FirebaseAuth.getInstance();
                Toast.makeText(wContext, "Added To Cart", Toast.LENGTH_SHORT).show();


                Map<String, String> map = new HashMap<>();
                map.put("ImageUrl",uploadCurrent.getImageUrl());
                map.put("Name",uploadCurrent.getName() );
                map.put("Amount",uploadCurrent.getAmount());
                map.put("Category",uploadCurrent.getCategory());


                String UploadId = mDatabaseRef.child("Users").child(firebaseauth.getCurrentUser().getUid()).push().getKey();
                mDatabaseRef.child("Cart").child(firebaseauth.getCurrentUser().getUid()).child(UploadId).setValue(map);

            }
        });

    }

    @Override
    public int getItemCount() {
        return wUploads.size();
    }

    public class WishlistViewHolder extends RecyclerView.ViewHolder {

        public TextView mtextViewName;
        public TextView mtextViewCategory;
        public TextView mtextViewAmount;
        public ImageView mimageView;
        private ImageView wCart;


        public WishlistViewHolder(View itemView) {
            super(itemView);

            mimageView = itemView.findViewById(R.id.image_wupload);
            mtextViewCategory = itemView.findViewById(R.id.text_wcategory);
            mtextViewName = itemView.findViewById(R.id.text_wname);
            mtextViewAmount = itemView.findViewById(R.id.text_wamount);
            wCart = itemView.findViewById(R.id.image_wcart);
        }
    }
}
