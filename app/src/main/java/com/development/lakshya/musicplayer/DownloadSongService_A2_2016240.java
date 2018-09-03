package com.development.lakshya.musicplayer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DownloadSongService_A2_2016240 extends Service {

    public static final String TAG = "DownloadService";
    public static final String DOWNLOADED_SONG = "SongDownloaded";
    private Song downloadedSong;
    public DownloadSongService_A2_2016240() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if(isNetworkAvailable()){
            Log.d(TAG, "onStartCommand: ");
            Object[] objects = new Object[2];
            objects[0] = getApplicationContext();
            objects[1] = "http://faculty.iiitd.ac.in/~mukulika/s1.mp3";
            SongDownloadAsyncTask_A2_2016240 downloadAsyncTask = new SongDownloadAsyncTask_A2_2016240(new DownloadedSongResponse_A2_2016240() {
                @Override
                public void getDownloadedSong(Song song) {
                    downloadedSong = song;
                    Intent intent1 = new Intent();
                    intent1.setAction(DOWNLOADED_SONG);
                    intent1.putExtra("Song",downloadedSong);
                    sendBroadcast(intent1);
                    Log.d(TAG, "getDownloadedSong: ");
                }
            });
            downloadAsyncTask.execute(objects);


        }
        else {
            Toast.makeText(getApplicationContext(), "Internet Connectivity Required", Toast.LENGTH_SHORT).show();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }
}
