package com.example.ihab.mycodingchallenge.Views;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ihab.mycodingchallenge.Adapters.AlbumsAdapter;
import com.example.ihab.mycodingchallenge.Adapters.RecyclerViewClickListener;
import com.example.ihab.mycodingchallenge.Interfaces.AlbumsActivityView;
import com.example.ihab.mycodingchallenge.POJOs.album;
import com.example.ihab.mycodingchallenge.Presenters.AlbumsActivityPresenter;
import com.example.ihab.mycodingchallenge.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlbumsActivity extends AppCompatActivity implements AlbumsActivityView{

    RecyclerView AlbumsRecyclerView;
    RecyclerViewClickListener listener;
    AlbumsAdapter albumsAdapter;
    Button Log_out_button;
    String User_id;
    ArrayList<album> albums;
    AlbumsActivityPresenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        presenter=new AlbumsActivityPresenter(this);

        //verify if the user is not connected
        checkingLogin();
        // getting the userid from accesstoken
        User_id = AccessToken.getCurrentAccessToken().getUserId();
        // initilaize th ui
        initialization();
        fetchData();


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
                goToPhotoActivity(position);
            }
        };

    }

    @Override
    public void initialization() {

        Log_out_button=findViewById(R.id.log_out);
        AlbumsRecyclerView=(RecyclerView)findViewById(R.id.albums_recycler_view);
        //setting the layoutmanger for the recyclerview
        AlbumsRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
    }

    @Override
    public void backToTheLoginActivity() {

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    // go to the photos screen
    @Override
    public void goToPhotoActivity( int position){

        Intent intent = new Intent(AlbumsActivity.this , PhotosActivity.class);
        intent.putExtra("id",albums.get(position).getAlbum_id());
        startActivity(intent);
    }




    // set the adapter for our recyclerview
    @Override
    public void setListOfAlbums(ArrayList<album> albums) {
        this.albums=albums;
        albumsAdapter = new AlbumsAdapter(AlbumsActivity.this,albums,listener);
        AlbumsRecyclerView.setAdapter(albumsAdapter);
    }

    @Override
    public void checkingLogin() {

        if (AccessToken.getCurrentAccessToken() == null){
            backToTheLoginActivity();
        }
    }

    // and checking if the user is connected to the internet
    // getting the list of the albums
    @Override
    public void fetchData(){

        if(isOnline())
        {presenter.getAlbums(User_id);}
        else
        {
            Toast.makeText(AlbumsActivity.this, "check out your internet connection ", Toast.LENGTH_LONG).show();
        }
    }


     // for checking the internet connection
    @Override
    public boolean isOnline() {

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
