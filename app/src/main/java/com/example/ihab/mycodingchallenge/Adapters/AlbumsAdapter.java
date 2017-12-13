package com.example.ihab.mycodingchallenge.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ihab.mycodingchallenge.POJOs.album;
import com.example.ihab.mycodingchallenge.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ihab on 12/12/2017.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder> {

    RecyclerViewClickListener listener;
    ArrayList<album> albums;
    Context context;

    public AlbumsAdapter(Context context, ArrayList<album> albums,RecyclerViewClickListener listener) {

        this.context = context;
        this.albums = albums;
        this.listener=listener;

    }

    @Override
    public AlbumsAdapter.AlbumViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_album_item, viewGroup, false);
        AlbumViewHolder viewHolder = new AlbumViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumsAdapter.AlbumViewHolder viewHolder, int i) {

        //use picasso to download the image
        Picasso.with(context).load(albums.get(i).getImage()).into(viewHolder.album_photo);
        //set the album name
        viewHolder.album_name.setText(albums.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
  // create a view holder for recycling
    public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView album_photo;
        TextView album_name;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            album_photo = itemView.findViewById(R.id.album_photo);
            album_name = itemView.findViewById(R.id.album_name);
            itemView.setOnClickListener(this);
        }
         // in order to add a clicklistener for the recyclerview
        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}
