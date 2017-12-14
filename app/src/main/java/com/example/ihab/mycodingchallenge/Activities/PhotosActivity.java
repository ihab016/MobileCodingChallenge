package com.example.ihab.mycodingchallenge.Activities;

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
import com.example.ihab.mycodingchallenge.POJOs.photo;
import com.example.ihab.mycodingchallenge.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity {

    RecyclerView PhotosRecyclerView;
    RecyclerViewClickListener listener;
    PhotosAdapter photosAdapter;
    ArrayList<photo> photos;
    String album_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        //intialize the ui
        intialization();
        // getting the list of the photos in an album
        if(isOnline())
        {getThePhotos(album_id);}
        else{
            Toast.makeText(PhotosActivity.this, "check out your internet connection ", Toast.LENGTH_LONG).show();
        }
        //implementing a clicklistener for the recyclerview
        listener=new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //going to the full_size_photo_activity and sending to it the photo id
                Intent intent=new Intent(PhotosActivity.this,FullSizePhotoActivity.class);
                intent.putExtra("photo_id",photos.get(position).getId());
                startActivity(intent);
            }
        };
    }

    private void intialization() {

        album_id=getIntent().getStringExtra("id");
        PhotosRecyclerView=findViewById(R.id.photos_recycler_view);
        //setting the layoutmanger for the recyclerview
        PhotosRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
    }

    public  void getThePhotos (String album_id){
        //sending a request to the graph api
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+album_id+"/photos?fields=picture{url}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d("response", "onCompleted: " + response);
                        try {
                            // getting data from the response as json and then parse it to an arraylist
                            JSONObject data = response.getJSONObject();
                             photos = new ArrayList<photo>();
                            JSONArray json_array = (JSONArray) data.getJSONArray("data");
                            for (int i = 0, size = json_array.length(); i < size; i++)
                            {
                                JSONObject Object = json_array.getJSONObject(i);
                                photos.add(new photo(
                                        Object.get("picture").toString(),
                                        Object.get("id").toString())
                                );
                            }
                            //after we got the list of the photos we will set the adapter for our recyclerview
                            photosAdapter = new PhotosAdapter (PhotosActivity.this,photos,listener);
                            PhotosRecyclerView.setAdapter(photosAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }
    //for checking the internet connection
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
