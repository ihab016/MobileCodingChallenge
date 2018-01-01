package com.example.ihab.mycodingchallenge.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ihab.mycodingchallenge.Interfaces.LoginActivityView;
import com.example.ihab.mycodingchallenge.Presenters.LoginActivityPresenter;
import com.example.ihab.mycodingchallenge.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity implements LoginActivityView {
    LoginButton login_button;
    CallbackManager callbackManager;
    Button album_button;
    LoginActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter=new LoginActivityPresenter(this);
        // checking if the user is already connected
        checkingLogin();
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
            presenter.LoginWithFacebook(callbackManager);


        // go to albums activtiy if albums button clicked
            album_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToAlbumsActivity();
                }
            });



    }







    @Override
    public void checkingLogin() {

        if (AccessToken.getCurrentAccessToken() != null){
            // going directly to the albums screen
            goToAlbumsActivity();
        }
    }

    @Override
    public void intialization(){

        callbackManager=CallbackManager.Factory.create();
        login_button=findViewById(R.id.login_button);
        album_button=findViewById(R.id.albums_button);

    }
    @Override
    public void goToAlbumsActivity() {
        Intent intent =new Intent(LoginActivity.this,AlbumsActivity.class);
        startActivity(intent);
    }


    @Override
    public void success() {
        goToAlbumsActivity();
        //set the album button visible
        album_button.setVisibility(View.VISIBLE);
    }

    @Override
    public void cancel() {
        // handling if the user cancelled the log in
        Toast.makeText(LoginActivity.this, "login cancelled ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void error() {
// handling the error case
        Toast.makeText(LoginActivity.this, "An error has accured ", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
