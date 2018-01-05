package com.example.ihab.mycodingchallenge.Presenters;

import com.example.ihab.mycodingchallenge.Interfaces.AlbumsActivityView;
import com.example.ihab.mycodingchallenge.Models.AlbumsActivityModel;
import com.example.ihab.mycodingchallenge.POJOs.album;
import com.example.ihab.mycodingchallenge.Views.AlbumsActivity;

import java.util.ArrayList;

/**
 * Created by ihab on 31/12/2017.
 */

public class AlbumsActivityPresenter {

    AlbumsActivityView view;
    AlbumsActivityModel model;

   public AlbumsActivityPresenter(AlbumsActivityView view){
        this.view=view;
        model=new AlbumsActivityModel();
    }

    public void getAlbums(String user_id){

       model.getAllAlbums(user_id,this);
    }

    public void setListOfAlbums(ArrayList<album> albums){
        view.setListOfAlbums(albums);
    }
}
