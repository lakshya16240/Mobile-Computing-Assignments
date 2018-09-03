package com.development.lakshya.musicplayer;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FragmentSongView extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 100 ;
    private int[] mAudioPath;
    private ArrayList<Song> songsList;
    private MediaPlayer mMediaPlayer;
    private RecyclerView rv_playlist;
    private String[] mMusicList ;
    private SongsAdapter songsAdapter;
    private int songPosition = 0;
    public static final String NEXT_SONG = "Next Song";
    public static final String PREV_SONG = "Previous Song";
    private IntentFilter intentFilter;
    public static final String TAG = "MusicPlayer";

    private String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_song_view,container,false);

        rv_playlist = view.findViewById(R.id.rv_playlist);


        if(isAdded()){

            if(isPermissionsGranted()) {


                try {
                    songsList = getAudioList();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Log.d("MusicPlayer", "onCreate: " + songsList.size());
                for(int i = 0; i< songsList.size(); i++){
                    Log.d("MusicPlayer", "onCreate: " + songsList.get(i).getSongName() + " " + songsList.get(i).getSongId());
                }


                songsAdapter = new SongsAdapter(songsList, getActivity(), new SongPlayResponse() {
                    @Override
                    public void getSong(String songName, int songId, String uri, int position) {


                        Intent intent = new Intent(getActivity(), SongsPlaybackService.class);
                        getActivity().stopService(intent);
                        intent.putExtra("song", new Song(songName,songId,uri));
                        getActivity().startService(intent);
                        getActivity().findViewById(R.id.iv_playSong).setVisibility(View.INVISIBLE);
                        getActivity().findViewById(R.id.iv_pauseSong).setVisibility(View.VISIBLE);
                        SongsPlaybackService.IS_RUNNING = true;
                        songPosition = position;
                    }
                });

                rv_playlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv_playlist.setAdapter(songsAdapter);


                intentFilter = new IntentFilter();
                intentFilter.addAction(NEXT_SONG);
                intentFilter.addAction(PREV_SONG);
                intentFilter.addAction(DownloadSongService.DOWNLOADED_SONG);
                getActivity().registerReceiver(broadcastReceiver,intentFilter);
            }


        }
        
        return view;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(NEXT_SONG)){
                Intent songIntent = new Intent(getActivity(), SongsPlaybackService.class);
                getActivity().stopService(songIntent);
                int nextSong = songPosition + 1;
                if(songPosition == songsList.size()-1)
                    nextSong = 0;
                songPosition = nextSong;
                songIntent.putExtra("song", new Song(songsList.get(nextSong).getSongName(),songsList.get(nextSong).getSongId(),songsList.get(nextSong).getUri()));
                getActivity().startService(songIntent);
                getActivity().findViewById(R.id.iv_playSong).setVisibility(View.INVISIBLE);
                getActivity().findViewById(R.id.iv_pauseSong).setVisibility(View.VISIBLE);
                SongsPlaybackService.IS_RUNNING = true;
            }
            else if(intent.getAction().equals(PREV_SONG)){
                Intent songIntent = new Intent(getActivity(), SongsPlaybackService.class);
                getActivity().stopService(songIntent);
                int nextSong = songPosition - 1;
                if(songPosition == 0)
                    nextSong = songsList.size() -1;
                songPosition = nextSong;
                songIntent.putExtra("song", new Song(songsList.get(nextSong).getSongName(),songsList.get(nextSong).getSongId(),songsList.get(nextSong).getUri()));
                getActivity().startService(songIntent);
                getActivity().findViewById(R.id.iv_playSong).setVisibility(View.INVISIBLE);
                getActivity().findViewById(R.id.iv_pauseSong).setVisibility(View.VISIBLE);
                SongsPlaybackService.IS_RUNNING = true;
            }
            else if(intent.getAction().equals(DownloadSongService.DOWNLOADED_SONG)){
                Song song = (Song) intent.getSerializableExtra("Song");
                songsList.add(song);
                songsAdapter.notifyDataSetChanged();
                Log.d(TAG, "onReceive: ");
//                String[] songList = new String[mMusicList.length + 1];
//                for(int i=0;i<mMusicList.length;i++)
//                    songList[i] = mMusicList[i];
//                songList[mMusicList.length] = song.getSongName() ;
//
//                int[] songIds = new int[mAudioPath.length + 1];
//                for (int i=0;i<mAudioPath.length;i++)
//                    songIds[i] = mAudioPath[i];
//                songIds[mAudioPath.length] = song.getSongId();



            }
        }
    };

    private boolean isPermissionsGranted() {

        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(getActivity(),p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),PERMISSION_REQUEST_CODE );
            return false;
        }
        return true;
    }


    private ArrayList<Song> getAudioList() throws IllegalAccessException {

        Field[] fields=R.raw.class.getFields();
        String[] songs = new String[fields.length];
        mAudioPath = new int[fields.length];
        ArrayList<Song> songArrayList = new ArrayList<>();
        for(int i=0; i < fields.length; i++){
            Song song = new Song(fields[i].getName(),fields[i].getInt(fields[i]),null);
            songArrayList.add(song);
//            songs[i] = fields[i].getName();
//            mAudioPath[i] = fields[i].getInt(fields[i]);
//            Log.d(TAG, "getAudioList: " + mAudioPath[i]);
        }

        return songArrayList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                            permissionsDenied += "\n" + per;

                        }

                    }
                    // Show permissionsDenied
                    if(permissionsDenied.length()!=0)
                        Toast.makeText(getActivity(), "Please provide the required permissions", Toast.LENGTH_SHORT).show();

                    else{

                    }
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
