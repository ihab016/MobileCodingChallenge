package com.example.ihab.mycodingchallenge.POJOs;

/**
 * Created by ihab on 12/12/2017.
 */

public class photo {
    private String photo_url;
    private  String id;

    public photo(String photo_url, String id) {
        this.photo_url = photo_url;
        this.id = id;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public String getId() {
        return id;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public void setId(String id) {
        this.id = id;
    }
}
