package com.development.lakshya.musicplayer;

import android.net.Uri;

import java.io.Serializable;

public class Song implements Serializable{

    private String songName;
    private int songId;
    private String uri;

    public Song(String songName, int songId, String uri) {
        this.songName = songName;
        this.songId = songId;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }
}
