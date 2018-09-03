package com.development.lakshya.musicplayer;

import android.net.Uri;

public interface SongPlayResponse {

    void getSong(String songName, int songId, String uri, int position);
}
