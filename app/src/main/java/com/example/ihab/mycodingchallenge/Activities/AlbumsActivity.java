package com.example.ihab.mycodingchallenge.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.ihab.mycodingchallenge.Adapters.AlbumsAdapter;
import com.example.ihab.mycodingchallenge.Adapters.RecyclerViewClickListener;
import com.example.ihab.mycodingchallenge.POJOs.album;
import com.example.ihab.mycodingchallenge.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlbumsActivity extends AppCompatActivity {

    RecyclerView AlbumsRecyclerView;
    RecyclerViewClickListener listener;
    AlbumsAdapter albumsAdapter;
    Button Log_out_button;
    String User_id;
    ArrayList<album> albums;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        //verify if the user is not connected
        if (AccessToken.getCurrentAccessToken() == null){
            backToTheLoginActivity();
        }
        // getting the userid from accesstoken
        User_id = AccessToken.getCurrentAccessToken().getUserId();
        // initilaize th ui
        initialization();
        // getting the list of the albums
        getAllAlbums(User_id);

        Log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loging out and going back to login screen
                LoginManager.getInstance().logOut();
                backToTheLoginActivity();
            }
        });

        //implementing a clicklistener for the recyclerview
        listener=new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                 // go to the photos screen
                Intent intent = new Intent(AlbumsActivity.this , PhotosActivity.class);
                intent.putExtra("id",albums.get(position).getAlbum_id());
                startActivity(intent);
            }
        };

    }

    private void initialization() {

        Log_out_button=findViewById(R.id.log_out);
        AlbumsRecyclerView=(RecyclerView)findViewById(R.id.albums_recycler_view);
        //setting the layoutmanger for the recyclerview
        AlbumsRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
    }

    private void backToTheLoginActivity() {

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public  void getAllAlbums (String User_iD){

        //we will send a request to the graph api using graph request
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+User_iD+"/albums?fields=name,picture{url}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {

                            // we got the json back from the server so we will parse the response in put it in an arraylist
                            JSONObject data = response.getJSONObject();
                            albums = new ArrayList<album>();
                            JSONArray json_Array = (JSONArray) data.getJSONArray("data");
                            for (int i = 0, size = json_Array.length(); i < size; i++)
                            {
                                JSONObject Object = json_Array.getJSONObject(i);
                                albums.add(new album(
                                         Object.getJSONObject("picture").getJSONObject("data").get("url").toString()
                                        ,Object.get("name").toString()
                                        ,Object.get("id").toString())
                                );
                            }

                            //after parsing the json will set the adapter for our recyclerview
                            albumsAdapter = new AlbumsAdapter(AlbumsActivity.this,albums,listener);
                            AlbumsRecyclerView.setAdapter(albumsAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    @Override
    public void onBackPressed() {
        LoginManager.getInstance().logOut();
        backToTheLoginActivity();
    }
}
