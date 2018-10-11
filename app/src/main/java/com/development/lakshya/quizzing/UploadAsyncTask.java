package com.development.lakshya.quizzing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadAsyncTask extends AsyncTask<Object,Long,Integer> {

    private Context context;
    public static final String TAG="UploadAsyncTask";
    private ProgressDialog mDialog;
    private String filePath;
    private long fileSize;

    @Override
    protected void onPreExecute() {



        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Object... objects) {

        filePath = (String) objects[0];
        context = (Context) objects[1];
        mDialog = (ProgressDialog) objects[2];
        HttpURLConnection conn = null;
        DataOutputStream dataOutputStream = null;
        byte[] buffer;

        File file = new File(filePath);

        fileSize = file.getTotalSpace();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            URL url = new URL("http://192.168.60.204:8001/index.php");
            conn = (HttpURLConnection) url.openConnection();

            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + "***");
            conn.setRequestProperty("uploaded_file", filePath);

            dataOutputStream = new DataOutputStream(conn.getOutputStream());

            dataOutputStream.writeBytes( "--***" + "\r\n");
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                    + filePath + "\"" + "\r\n");

            dataOutputStream.writeBytes("\r\n");
            int availableBytes = fileInputStream.available();
            int bufferSize = Math.min(availableBytes,1024*1024);
            buffer = new byte[bufferSize];

            int bytesRead = fileInputStream.read(buffer,0,bufferSize);

            while (bytesRead > 0) {

                dataOutputStream.write(buffer, 0, bufferSize);
                availableBytes = fileInputStream.available();
                publishProgress(fileSize - bufferSize);
                Log.d(TAG, "doInBackground:1 " + bytesRead + " " + availableBytes);
                bufferSize = Math.min(availableBytes, 1024*1024);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);



                Log.d(TAG, "doInBackground:2 " + bytesRead + " " + availableBytes);

            }

            dataOutputStream.writeBytes("\r\n");
            dataOutputStream.writeBytes( "--***--" + "\r\n");

            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();


            fileInputStream.close();
            dataOutputStream.flush();
            dataOutputStream.close();
            Log.d(TAG, "doInBackground: " +responseCode + " " + responseMessage);

            if(responseCode==200){
                return responseCode;
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        Log.d(TAG, "onProgressUpdate: " +values[0] + " " + fileSize + " " + (100*(double)values[0]/fileSize));
        mDialog.setProgress((int)(100*(double)values[0]/fileSize));

    }

    @Override
    protected void onPostExecute(Integer responseCode) {
        super.onPostExecute(responseCode);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mDialog.dismiss();

        Log.d(TAG, "onPostExecute: ");
        if(responseCode==200){
            Toast.makeText(context, "File uploaded to Server", Toast.LENGTH_SHORT).show();
        }
    }
}
