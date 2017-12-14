package com.example.ihab.mycodingchallenge.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ihab.mycodingchallenge.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {
    LoginButton login_button;
    CallbackManager callbackManager;
    Button album_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // checking if the user is already connected
        if (AccessToken.getCurrentAccessToken() != null){
            // going directly to the albums screen
            goToAlbumsActivity();
        }
        // creating an access tokentracker to track whenever the user loged out the album_button will diseapper
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    //write your code here what to do when user clicks on facebook logout
                    album_button.setVisibility(View.GONE);
                }

            }
        };

        //initialize the ui
        intialization();
        // setting permission for accessing photos
        login_button.setReadPermissions("user_photos ");
         // login using facebook login
            LoginWithFacebook();


        // go to albums activtiy if albums button clicked
            album_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToAlbumsActivity();
                }
            });



    }

    public void intialization(){

        callbackManager=CallbackManager.Factory.create();
        login_button=findViewById(R.id.login_button);
        album_button=findViewById(R.id.albums_button);

    }

    private void goToAlbumsActivity() {
        Intent intent =new Intent(LoginActivity.this,AlbumsActivity.class);
        startActivity(intent);
    }




    private void LoginWithFacebook() {


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // if the user successfully loged in we will send him to the albums screen
                goToAlbumsActivity();
                //set the album button visible
                album_button.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancel() {
                // handling if the user cancelled the log in
                Toast.makeText(LoginActivity.this, "login cancelled ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // handling the error case
                Toast.makeText(LoginActivity.this, "An error has accured ", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
