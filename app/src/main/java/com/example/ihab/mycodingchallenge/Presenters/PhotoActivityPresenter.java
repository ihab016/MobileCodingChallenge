package com.example.ihab.mycodingchallenge.Presenters;

import com.example.ihab.mycodingchallenge.Interfaces.AlbumsActivityView;
import com.example.ihab.mycodingchallenge.Interfaces.PhotosActivityView;
import com.example.ihab.mycodingchallenge.Models.AlbumsActivityModel;
import com.example.ihab.mycodingchallenge.Models.PhotosActivityModel;
import com.example.ihab.mycodingchallenge.POJOs.photo;

import java.util.ArrayList;

/**
 * Created by ihab on 31/12/2017.
 */

public class PhotoActivityPresenter {

    PhotosActivityView view;
    PhotosActivityModel model;

    public PhotoActivityPresenter(PhotosActivityView view){
        this.view=view;
        model=new PhotosActivityModel();
    }

    public void getThePhotos(String album_id){

        model.getThePhotos(album_id,this);
    }

    public void setTheListOfPhotos(ArrayList<photo> photos){
        view.setTheListOfPhotos(photos);

    }
}
