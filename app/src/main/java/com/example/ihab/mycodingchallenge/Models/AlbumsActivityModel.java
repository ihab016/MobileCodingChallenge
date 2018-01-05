package com.example.ihab.mycodingchallenge.Models;

import com.example.ihab.mycodingchallenge.Adapters.AlbumsAdapter;
import com.example.ihab.mycodingchallenge.POJOs.album;
import com.example.ihab.mycodingchallenge.Presenters.AlbumsActivityPresenter;
import com.example.ihab.mycodingchallenge.Views.AlbumsActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ihab on 02/01/2018.
 */

public class AlbumsActivityModel {
    ArrayList<album> albums;

    public  void getAllAlbums (String User_iD , final AlbumsActivityPresenter presenter){

        //we will send a request to the graph api using graph request
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+User_iD+"/albums?fields=name,picture{url}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {

                            // we got the json back from the server so we will parse the response in put it in an arraylist
                            JSONObject data = response.getJSONObject();
                            albums = new ArrayList<album>();
                            JSONArray json_Array = (JSONArray) data.getJSONArray("data");
                            for (int i = 0, size = json_Array.length(); i < size; i++)
                            {
                                JSONObject Object = json_Array.getJSONObject(i);
                                albums.add(new album(
                                        Object.getJSONObject("picture").getJSONObject("data").get("url").toString()
                                        ,Object.get("name").toString()
                                        ,Object.get("id").toString())
                                );
                            }

                           presenter.setListOfAlbums(albums);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

}
