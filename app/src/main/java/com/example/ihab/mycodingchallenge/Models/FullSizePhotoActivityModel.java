package com.example.ihab.mycodingchallenge.Models;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.ihab.mycodingchallenge.Presenters.FullSizePhotoActivityPresenter;
import com.example.ihab.mycodingchallenge.Views.FullSizePhotoActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * Created by ihab on 05/01/2018.
 */

public class FullSizePhotoActivityModel {





    public void getThePhoto(String photo_id, final FullSizePhotoActivityPresenter presenter) {
        // send a request to the graph api
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + photo_id + "/?fields=images",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            // getting data from the response as json and parse it
                            JSONObject data = response.getJSONObject();
                            JSONObject j = (JSONObject) data.getJSONArray("images").get(0);
                            String photoUrl = j.get("source").toString();
                            presenter.setThePhoto(photoUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    public void uploadThePhotoToFirebase(Bitmap bitmap, StorageReference mStorageRef, final FullSizePhotoActivityPresenter presenter) {



        // Genrating a random name
        StorageReference reference = mStorageRef.child(UUID.randomUUID().toString()+"image.jpg");




        // turn the Bitmap Image into a byte array to be uploaded
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();

        // uploading the image
        UploadTask uploadTask = reference.putBytes(bytes);

        // we will add a progressListener to update the progress dialog
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                int currentprogress = (int) progress;
               presenter.ProgressUpload(currentprogress);
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
               presenter.PauseUpload();
            }
        });
        // handling tha case of success
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                presenter.SuccessUpload();
            }
            // handling the case of failure
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("", "onFailure: "+e);
                presenter.FailureUpload();
            }
        });


    }
}
