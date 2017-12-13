package com.example.ihab.mycodingchallenge.Activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.ihab.mycodingchallenge.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;



public class FullSizePhotoActivity extends AppCompatActivity {

    ImageView imageview;
    String photo_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_size_photo);
        //initialize the ui
        initialization();
        //getting the full size photo and display it
        getThePhoto(photo_id);
    }

    private void initialization() {

        photo_id = getIntent().getStringExtra("photo_id");
        imageview = findViewById(R.id.full_size_image);
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
}
