package com.example.ihab.mycodingchallenge.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ihab.mycodingchallenge.POJOs.photo;
import com.example.ihab.mycodingchallenge.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ihab on 12/12/2017.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {

    RecyclerViewClickListener listener;
    ArrayList<photo> photos;
    Context context;

    public PhotosAdapter(Context context, ArrayList<photo> photos, RecyclerViewClickListener listener) {

        this.context = context;
        this.photos = photos;
        this.listener=listener;

    }

    @Override
    public PhotosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_photo_item, viewGroup, false);
        PhotosViewHolder viewHolder = new PhotosViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PhotosViewHolder viewHolder, int i) {

        //use picasso to download the image
        Picasso.with(context).load(photos.get(i).getPhoto_url()).into(viewHolder.photo);


    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    // create a view holder for recycling
    public class PhotosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView photo;


        public PhotosViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.single_photo);
            itemView.setOnClickListener(this);
        }

        // in order to add a clicklistner for the recyclerview
        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}
