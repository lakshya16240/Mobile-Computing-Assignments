package com.development.lakshya.musicplayer;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SongDownloadAsyncTask extends AsyncTask<Object, String, String> {

    private String fileName;
    private Context context;
    private Uri uri;
    public static final String TAG = "SongDownload";

    private DownloadedSongResponse downloadedSongResponse;

    public SongDownloadAsyncTask(DownloadedSongResponse downloadedSongResponse) {
        this.downloadedSongResponse = downloadedSongResponse;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Object... objects) {

        int count = 0;
        try {

            context = (Context) objects[0];
            URL url = new URL((String) objects[1]);
            URLConnection connection = url.openConnection();
            connection.connect();


            fileName = ((String)objects[1]).substring(((String)objects[1]).lastIndexOf('/') + 1);

            InputStream input = new BufferedInputStream(url.openStream());
            FileOutputStream outputStream = context.openFileOutput(fileName,Context.MODE_PRIVATE);

            byte data[] = new byte[1024];

            while ((count = input.read(data)) != -1) {
                outputStream.write(data, 0, count);
            }

            //Log.d(TAG, "doInBackground: " + outputStream.toString());

            outputStream.flush();
            outputStream.close();
            input.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        File[] fileList  = context.getFilesDir().listFiles();
        for(int i=0;i<fileList.length;i++){
            Log.d(TAG, "doInBackground: " + fileList[i].getName());
            if(fileList[i].getName().equals(fileName))
                uri = Uri.fromFile(fileList[i]);
        }

        downloadedSongResponse.getDownloadedSong(new Song(fileName,0,uri.toString()));



    }
}
