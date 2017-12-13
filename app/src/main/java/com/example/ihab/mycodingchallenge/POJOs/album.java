package com.example.ihab.mycodingchallenge.POJOs;

/**
 * Created by ihab on 12/12/2017.
 */

public class album {

    private String image;
    private String name;
    private String album_id;

    public album(String image, String name, String album_id) {
        this.image = image;
        this.name = name;
        this.album_id = album_id;
    }

    public String getImage() {
        return image;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getName() {

        return name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum_id() {
        return album_id;
    }
}
