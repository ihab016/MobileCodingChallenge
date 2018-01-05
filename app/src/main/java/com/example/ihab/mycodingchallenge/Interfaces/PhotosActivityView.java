package com.example.ihab.mycodingchallenge.Interfaces;

import com.example.ihab.mycodingchallenge.POJOs.album;
import com.example.ihab.mycodingchallenge.POJOs.photo;

import java.util.ArrayList;

/**
 * Created by ihab on 31/12/2017.
 */

public interface PhotosActivityView {

    public void initialization();

    public void goToFullSizePhotoActivity(int position);

    public boolean isOnline();

    public void setTheListOfPhotos(ArrayList<photo> photos);

    public void fetchData();
}
