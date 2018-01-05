package com.example.ihab.mycodingchallenge.Presenters;

import android.graphics.Bitmap;

import com.example.ihab.mycodingchallenge.Interfaces.FullSizePhotoActivityView;
import com.example.ihab.mycodingchallenge.Interfaces.PhotosActivityView;
import com.example.ihab.mycodingchallenge.Models.FullSizePhotoActivityModel;
import com.example.ihab.mycodingchallenge.Models.PhotosActivityModel;
import com.example.ihab.mycodingchallenge.POJOs.photo;
import com.example.ihab.mycodingchallenge.Views.FullSizePhotoActivity;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

/**
 * Created by ihab on 31/12/2017.
 */

public class FullSizePhotoActivityPresenter {


    FullSizePhotoActivityView view;
    FullSizePhotoActivityModel model;

    public FullSizePhotoActivityPresenter(FullSizePhotoActivityView view){
        this.view=view;
        model=new FullSizePhotoActivityModel();
    }

    public void getThePhoto(String photo_id){

        model.getThePhoto(photo_id,this);
    }

    public void setThePhoto(String photo){
        view.setThePhoto(photo);

    }
    public void uploadThePhotoToFirebase(Bitmap bitmap,StorageReference mStorageRef){

        model.uploadThePhotoToFirebase(bitmap,mStorageRef,this);

    }

    public void ProgressUpload(int currentProgress)
    {
        view.ProgressUpload(currentProgress);
    }

    public void PauseUpload(){

        view.PauseUpload();
    }

    public void SuccessUpload(){

        view.SuccessUpload();

    }

    public void FailureUpload(){

        view.FailureUpload();
    }

}
