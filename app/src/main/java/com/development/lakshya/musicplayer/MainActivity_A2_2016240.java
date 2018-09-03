package com.development.lakshya.musicplayer;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity_A2_2016240 extends AppCompatActivity {

    private ImageView iv_playSong, iv_pauseSong, iv_previousSong, iv_nextSong, iv_downloadSong;
    private ComponentName component;
    private SystemReceivers systemReceivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_playSong = findViewById(R.id.iv_playSong);
        iv_pauseSong = findViewById(R.id.iv_pauseSong);
        iv_nextSong = findViewById(R.id.iv_nextSong);
        iv_previousSong = findViewById(R.id.iv_previousSong);
        iv_downloadSong = findViewById(R.id.iv_downloadSong);
        systemReceivers  = new SystemReceivers();



        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        registerReceiver(systemReceivers,intentFilter);

        component = new ComponentName(this, SystemReceivers.class);

        iv_playSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SongsPlaybackService.IS_RUNNING) {
                    MainActivity_A2_2016240.this.findViewById(R.id.iv_playSong).setVisibility(View.INVISIBLE);
                    MainActivity_A2_2016240.this.findViewById(R.id.iv_pauseSong).setVisibility(View.VISIBLE);
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(SongsPlaybackService.PLAY_SONG);
                    MainActivity_A2_2016240.this.sendBroadcast(broadcastIntent);
                }
            }
        });
        
        iv_pauseSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SongsPlaybackService.IS_RUNNING) {
                    MainActivity_A2_2016240.this.findViewById(R.id.iv_playSong).setVisibility(View.VISIBLE);
                    MainActivity_A2_2016240.this.findViewById(R.id.iv_pauseSong).setVisibility(View.INVISIBLE);
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(SongsPlaybackService.PAUSE_SONG);
                    MainActivity_A2_2016240.this.sendBroadcast(broadcastIntent);
                }
            }
        });


        iv_nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SongsPlaybackService.IS_RUNNING){
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(FragmentSongView_A2_2016240.NEXT_SONG);
                    MainActivity_A2_2016240.this.sendBroadcast(broadcastIntent);
                }
            }
        });

        iv_previousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SongsPlaybackService.IS_RUNNING){
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(FragmentSongView_A2_2016240.PREV_SONG);
                    MainActivity_A2_2016240.this.sendBroadcast(broadcastIntent);
                }
            }
        });

        iv_downloadSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_A2_2016240.this, DownloadSongService_A2_2016240.class);
                startService(intent);
            }
        });

    }

    public static class SystemReceivers extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {


            if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
                Toast.makeText(context, "Power Connected", Toast.LENGTH_SHORT).show();
            else if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED))
                Toast.makeText(context, "Power Disconnected", Toast.LENGTH_SHORT).show();
            else if(intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED))
                Toast.makeText(context, "Airplane mode on", Toast.LENGTH_SHORT).show();
            else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
                Toast.makeText(context, "Boot Completed", Toast.LENGTH_SHORT).show();

        }

        public SystemReceivers() {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(systemReceivers);
        MainActivity_A2_2016240.this.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED , PackageManager.DONT_KILL_APP);
    }
}
