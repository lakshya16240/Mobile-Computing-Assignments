package com.development.lakshya.musicplayer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class SongsPlaybackService extends Service {

    public static boolean IS_RUNNING = false;
    private BroadcastReceiver receiver;
    private MediaPlayer mp;
    public static final String TAG = "SongsPlaybackService";
    public static final String PLAY_SONG = "Play Song";
    public static final String PAUSE_SONG = "Pause Song";

    public SongsPlaybackService() {
    }

    public class SongActionReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(PLAY_SONG)){
                mp.start();
            }
            else if(intent.getAction().equals(PAUSE_SONG))
                mp.pause();
        }

        public SongActionReceiver() {
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PLAY_SONG);
        intentFilter.addAction(PAUSE_SONG);
        receiver = new SongActionReceiver();
        registerReceiver(receiver,intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        Song song = (Song) intent.getSerializableExtra("song");
        String songName = song.getSongName();
        int songId = song.getSongId();
        Uri uri = null;
        if(song.getUri()!=null)
            uri = Uri.parse(song.getUri());

        mp = new MediaPlayer();
        if(uri == null)
            mp = MediaPlayer.create(getApplicationContext(),songId);
        else
            mp = MediaPlayer.create(getApplicationContext(),uri);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSelf();
            }
        });
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
        unregisterReceiver(receiver);
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
