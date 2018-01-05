package com.example.ihab.mycodingchallenge.Interfaces;

import com.google.firebase.storage.UploadTask;

/**
 * Created by ihab on 31/12/2017.
 */

public interface FullSizePhotoActivityView {

    public void initialization();

    public void setThePhoto(String photo);

    public boolean isOnline();

    public void ProgressUpload(int currentProgress);

    public void PauseUpload();

    public void SuccessUpload();

    public void FailureUpload();

    public void startUploading();

    public void getThePhoto();
}
