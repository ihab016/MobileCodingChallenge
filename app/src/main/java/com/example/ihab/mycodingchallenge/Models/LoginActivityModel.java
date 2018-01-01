package com.example.ihab.mycodingchallenge.Models;

import android.view.View;
import android.widget.Toast;

import com.example.ihab.mycodingchallenge.Presenters.LoginActivityPresenter;
import com.example.ihab.mycodingchallenge.Views.LoginActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

/**
 * Created by ihab on 31/12/2017.
 */

public class LoginActivityModel {


    public void LoginWithFacebook(CallbackManager callbackManager, final LoginActivityPresenter presenter) {


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // if the user successfully loged in we will send him to the albums screen
              presenter.success();
            }

            @Override
            public void onCancel() {
                presenter.cancel();
            }

            @Override
            public void onError(FacebookException exception) {
                presenter.error();
            }
        });
    }
}
