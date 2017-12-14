package com.example.ihab.mycodingchallenge.Activities;


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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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


public class FullSizePhotoActivity extends AppCompatActivity {

    ImageView imageview;
    String photo_id;
    FloatingActionButton floatingActionButton;
    private StorageReference mStorageRef;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_size_photo);
        // initializing the storage reference
        mStorageRef = FirebaseStorage.getInstance().getReference();
        //initialize the ui
        initialization();
        //getting the full size photo and display it
        if(isOnline())
        {getThePhoto(photo_id);}
        else
        {
            Toast.makeText(FullSizePhotoActivity.this, "check out your internet connection ", Toast.LENGTH_SHORT).show();
        }

        // when upload button is clicked
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    uploadThePhotoToFirebase();
                }
                else
                {
                    Toast.makeText(FullSizePhotoActivity.this, "check out your internet connection ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void initialization() {

        photo_id = getIntent().getStringExtra("photo_id");
        imageview = findViewById(R.id.full_size_image);
        floatingActionButton=findViewById(R.id.floatingActionButton);
        // initialize the progress bar for uploading the photo
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("wait while uploading");
        progressDialog.setTitle("Uploading the photo to firebase");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);

    }

    public void getThePhoto(String photo_id) {
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
                            // using picasso to download the image
                            Picasso.with(FullSizePhotoActivity.this).load(photoUrl).into(imageview);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }
    private void uploadThePhotoToFirebase() {
        //getting the bitmap from the imageview
        BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageview.getDrawable());
        Bitmap bitmap = bitmapDrawable .getBitmap();
        if (bitmap==null){
            Toast.makeText(this, "no image found ", Toast.LENGTH_SHORT).show();

        }else {
            // Genrating a random name
            StorageReference reference = mStorageRef.child(UUID.randomUUID().toString()+"image.jpg");

            progressDialog.show();


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
                    progressDialog.setProgress(currentprogress);
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Upload is paused");
                }
            });
            // handling tha case of success
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(FullSizePhotoActivity.this, "the photo was successfully uploaded", Toast.LENGTH_LONG).show();
                }
                // handling the case of failure
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(FullSizePhotoActivity.this, "failing to upload the photo" , Toast.LENGTH_LONG).show();

                }
            });
        }

    }
    // for checking the internet connection
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
