package com.hapy.login;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<FirebaseUpload> mUploads;


    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseauth;


    private AdapterView.OnItemClickListener mOnItemClickListener;




    public ImageAdapter(Context context, List<FirebaseUpload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_card_view, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {


       final FirebaseUpload uploadCurrent = mUploads.get(position);


        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewCategory.setText(uploadCurrent.getCategory());
        holder.textViewAmount.setText(uploadCurrent.getAmount());
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
        holder.mCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mDatabaseRef = FirebaseDatabase.getInstance().getReference();
                firebaseauth = FirebaseAuth.getInstance();
                Toast.makeText(mContext, "Added To Cart", Toast.LENGTH_SHORT).show();


                Map<String, String> map = new HashMap<>();
                map.put("ImageUrl",uploadCurrent.getImageUrl());
                map.put("Name",uploadCurrent.getName() );
                map.put("Amount",uploadCurrent.getAmount());
                map.put("Category",uploadCurrent.getCategory());


                String UploadId = mDatabaseRef.child("Users").child(firebaseauth.getCurrentUser().getUid()).push().getKey();
                mDatabaseRef.child("Cart").child(firebaseauth.getCurrentUser().getUid()).child(UploadId).setValue(map);

            }
        });
        holder.mWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabaseRef = FirebaseDatabase.getInstance().getReference();
                firebaseauth = FirebaseAuth.getInstance();
                Toast.makeText(mContext, "Added To Wishlist", Toast.LENGTH_SHORT).show();


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
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewCategory;
        public TextView textViewAmount;
        public ImageView imageView;
        private ImageView mCart;
        private ImageView mWishlist;





        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewCategory = itemView.findViewById(R.id.text_view_category);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
            imageView = itemView.findViewById(R.id.image_view_upload);
            mCart = itemView.findViewById(R.id.image_view_cart);
            mWishlist = itemView.findViewById(R.id.image_view_wishilist);




        }
    }

}
