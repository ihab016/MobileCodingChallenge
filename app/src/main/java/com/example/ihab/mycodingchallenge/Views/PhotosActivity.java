package com.example.ihab.mycodingchallenge.Views;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ihab.mycodingchallenge.Adapters.PhotosAdapter;
import com.example.ihab.mycodingchallenge.Adapters.RecyclerViewClickListener;
import com.example.ihab.mycodingchallenge.Interfaces.PhotosActivityView;
import com.example.ihab.mycodingchallenge.POJOs.photo;
import com.example.ihab.mycodingchallenge.Presenters.PhotoActivityPresenter;
import com.example.ihab.mycodingchallenge.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity implements PhotosActivityView {

    RecyclerView PhotosRecyclerView;
    RecyclerViewClickListener listener;
    PhotosAdapter photosAdapter;
    ArrayList<photo> photos;
    String album_id;
    PhotoActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        presenter=new PhotoActivityPresenter(this);
        //intialize the ui
        initialization();
        // getting the list of the photos in an album
        fetchData();
        //implementing a clicklistener for the recyclerview
        listener=new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //going to the full_size_photo_activity and sending to it the photo id
                goToFullSizePhotoActivity(position);
            }
        };
    }

    public void initialization() {

        album_id=getIntent().getStringExtra("id");
        PhotosRecyclerView=findViewById(R.id.photos_recycler_view);
        //setting the layoutmanger for the recyclerview
        PhotosRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
    }


    //for checking the internet connection
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    // getting the list of the photos in an album
    public void fetchData(){
        if(isOnline())
        {presenter.getThePhotos(album_id);}
        else{
            Toast.makeText(PhotosActivity.this, "check out your internet connection ", Toast.LENGTH_LONG).show();
        }
    }

    public void goToFullSizePhotoActivity(int position){

        Intent intent=new Intent(PhotosActivity.this,FullSizePhotoActivity.class);
        intent.putExtra("photo_id",photos.get(position).getId());
        startActivity(intent);

    }
    public void setTheListOfPhotos(ArrayList<photo> photos) {
        //after we got the list of the photos we will set the adapter for our recyclerview
        this.photos=photos;
        photosAdapter = new PhotosAdapter(PhotosActivity.this, photos, listener);
        PhotosRecyclerView.setAdapter(photosAdapter);
    }
}
