package com.example.ihab.mycodingchallenge.Models;

import android.util.Log;

import com.example.ihab.mycodingchallenge.Adapters.PhotosAdapter;
import com.example.ihab.mycodingchallenge.POJOs.photo;
import com.example.ihab.mycodingchallenge.Presenters.PhotoActivityPresenter;
import com.example.ihab.mycodingchallenge.Views.PhotosActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ihab on 05/01/2018.
 */

public class PhotosActivityModel {

    ArrayList<photo> photos;

    public  void getThePhotos (String album_id, final PhotoActivityPresenter presenter){
        //sending a request to the graph api
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+album_id+"/photos?fields=picture{url}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d("response", "onCompleted: " + response);
                        try {
                            // getting data from the response as json and then parse it to an arraylist
                            JSONObject data = response.getJSONObject();
                            photos = new ArrayList<photo>();
                            JSONArray json_array = (JSONArray) data.getJSONArray("data");
                            for (int i = 0, size = json_array.length(); i < size; i++)
                            {
                                JSONObject Object = json_array.getJSONObject(i);
                                photos.add(new photo(
                                        Object.get("picture").toString(),
                                        Object.get("id").toString())
                                );
                            }
                            presenter.setTheListOfPhotos(photos);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }
}
