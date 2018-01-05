package com.example.ihab.mycodingchallenge.Interfaces;

import com.example.ihab.mycodingchallenge.POJOs.album;

import java.util.ArrayList;

/**
 * Created by ihab on 31/12/2017.
 */

public interface AlbumsActivityView {

    public void initialization();

    public void backToTheLoginActivity();

    public boolean isOnline();

    public void goToPhotoActivity(int position);

    public void checkingLogin();

    public void setListOfAlbums(ArrayList<album> albums);

    public void fetchData();

}
