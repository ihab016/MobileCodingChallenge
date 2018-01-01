package com.example.ihab.mycodingchallenge.Presenters;

import com.example.ihab.mycodingchallenge.Interfaces.LoginActivityView;
import com.example.ihab.mycodingchallenge.Models.LoginActivityModel;
import com.facebook.CallbackManager;

/**
 * Created by ihab on 31/12/2017.
 */

public class LoginActivityPresenter {

     LoginActivityView view;
     LoginActivityModel model;

     public LoginActivityPresenter(LoginActivityView view ){
         this.view=view;
         model=new LoginActivityModel();

    }

    public void LoginWithFacebook(CallbackManager callbackManager){
         model.LoginWithFacebook(callbackManager,this);
    }

    public  void success(){

         view.success();
    }

    public void cancel(){
        view.cancel();
    }

    public void error(){
        view.error();
    }
}
