package com.development.lakshya.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {

//    private String[] musicList;
//    int[] resId;
    private ArrayList<Song> songsList;
    private Context context;
    private SongPlayResponse songPlayResponse;

//    public SongsAdapter(String[] musicList, int[] resId, Context context, SongPlayResponse songPlayResponse) {
//        this.musicList = musicList;
//        this.resId = resId;
//        this.context = context;
//        this.songPlayResponse = songPlayResponse;
//    }


    public SongsAdapter(ArrayList<Song> songsList, Context context, SongPlayResponse songPlayResponse) {
        this.songsList = songsList;
        this.context = context;
        this.songPlayResponse = songPlayResponse;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.item_song_recyclerview,parent,false);
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, final int position) {

        final String songName = songsList.get(position).getSongName();
        final int pos = position;
        holder.tv_index.setText(String.valueOf(position+1));
        holder.tv_songName.setText(songName);
        final int songId = songsList.get(position).getSongId();
        final String uri = songsList.get(position).getUri();
        holder.cv_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songPlayResponse.getSong(songName,songId,uri,pos);
            }
        });



    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder{

        TextView tv_index, tv_songName;
        CardView cv_song;

        SongViewHolder(View itemView) {
            super(itemView);
            tv_index = itemView.findViewById(R.id.tv_index);
            tv_songName = itemView.findViewById(R.id.tV_songName);
            cv_song = itemView.findViewById(R.id.cv_song);
        }
    }
}
