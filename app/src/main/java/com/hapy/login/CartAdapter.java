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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {


    private Context cContext;
    private List<FirebaseUpload> cUploads;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseauth;


    public CartAdapter(Context context, List<FirebaseUpload> uploads) {
        cContext = context;
        cUploads = uploads;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(cContext).inflate(R.layout.activity_cart_card_view, parent, false);
        return new CartAdapter.CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
       final FirebaseUpload uploadCurrent = cUploads.get(position);


        holder.mtextViewName.setText(uploadCurrent.getName());
        holder.mtextViewCategory.setText(uploadCurrent.getCategory());
        holder.mtextViewAmount.setText(uploadCurrent.getAmount());
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.mimageView);

        holder.cWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabaseRef = FirebaseDatabase.getInstance().getReference();
                firebaseauth = FirebaseAuth.getInstance();
                Toast.makeText(cContext, "Added To Wishlist", Toast.LENGTH_SHORT).show();


                Map<String, String> map = new HashMap<>();
                map.put("ImageUrl",uploadCurrent.getImageUrl());
                map.put("Name",uploadCurrent.getName() );
                map.put("Amount",uploadCurrent.getAmount());
                map.put("Category",uploadCurrent.getCategory());


                String UploadId = mDatabaseRef.child("Users").child(firebaseauth.getCurrentUser().getUid()).push().getKey();
                mDatabaseRef.child("Wishlist").child(firebaseauth.getCurrentUser().getUid()).child(UploadId).setValue(map);

            }
        });

    }

    @Override
    public int getItemCount() {
        return cUploads.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        public TextView mtextViewName;
        public TextView mtextViewCategory;
        public TextView mtextViewAmount;
        public ImageView mimageView;
        private ImageView cWishlist;

        public CartViewHolder(View itemView) {
            super(itemView);

            mimageView = itemView.findViewById(R.id.image_cupload);
            mtextViewCategory = itemView.findViewById(R.id.text_ccategory);
            mtextViewName = itemView.findViewById(R.id.text_cname);
            mtextViewAmount = itemView.findViewById(R.id.text_camount);
            cWishlist = itemView.findViewById(R.id.image_cwishilist);
        }
    }
}

