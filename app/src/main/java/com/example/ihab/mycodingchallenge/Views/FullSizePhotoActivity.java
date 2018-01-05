package com.example.ihab.mycodingchallenge.Views;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ihab.mycodingchallenge.Interfaces.FullSizePhotoActivityView;
import com.example.ihab.mycodingchallenge.Presenters.FullSizePhotoActivityPresenter;
import com.example.ihab.mycodingchallenge.R;
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


public class FullSizePhotoActivity extends AppCompatActivity implements FullSizePhotoActivityView {

    ImageView imageview;
    String photo_id;
    FloatingActionButton floatingActionButton;
    private StorageReference mStorageRef;
    ProgressDialog progressDialog;
    FullSizePhotoActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_size_photo);
        presenter = new FullSizePhotoActivityPresenter(this);
        // initializing the storage reference
        mStorageRef = FirebaseStorage.getInstance().getReference();
        //initialize the ui
        initialization();
        //getting the full size photo and display it
        getThePhoto();

        // when upload button is clicked
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startUploading();
            }
        });
    }


    @Override
    public void initialization() {

        photo_id = getIntent().getStringExtra("photo_id");
        imageview = findViewById(R.id.full_size_image);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        // initialize the progress bar for uploading the photo
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("wait while uploading");
        progressDialog.setTitle("Uploading the photo to firebase");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);

    }


    // for checking the internet connection
    @Override
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void setThePhoto(String photo) {

        // using picasso to download the image
        Picasso.with(FullSizePhotoActivity.this).load(photo).into(imageview);
    }

    @Override
    public void getThePhoto() {

        if (isOnline()) {
            presenter.getThePhoto(photo_id);
        } else {
            Toast.makeText(FullSizePhotoActivity.this, "check out your internet connection ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void ProgressUpload(int currentprogress) {


        progressDialog.setProgress(currentprogress);

    }

    @Override
    public void PauseUpload() {

        Toast.makeText(FullSizePhotoActivity.this, "the upload is paused", Toast.LENGTH_LONG).show();
    }

    @Override
    public void SuccessUpload() {

        progressDialog.dismiss();
        Toast.makeText(FullSizePhotoActivity.this, "the photo was successfully uploaded", Toast.LENGTH_LONG).show();

    }

    @Override
    public void FailureUpload() {

        progressDialog.dismiss();
        Toast.makeText(FullSizePhotoActivity.this, "failing to upload the photo", Toast.LENGTH_LONG).show();

    }

    @Override
    public void startUploading() {

        if (isOnline()) {
            //getting the bitmap from the imageview
            BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageview.getDrawable());
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap == null) {
                Toast.makeText(FullSizePhotoActivity.this, "no image found ", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                presenter.uploadThePhotoToFirebase(bitmap, mStorageRef);
            }
        } else {
            Toast.makeText(FullSizePhotoActivity.this, "check out your internet connection ", Toast.LENGTH_SHORT).show();
        }
    }
}
