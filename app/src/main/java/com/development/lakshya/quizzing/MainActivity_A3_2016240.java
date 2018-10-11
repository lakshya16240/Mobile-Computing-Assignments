package com.development.lakshya.quizzing;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_A3_2016240 extends AppCompatActivity {

    public static FrameLayout quizzingFrame;
    private static final int PERMISSION_REQUEST_CODE = 100 ;

    private String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quizzingFrame = findViewById(R.id.quizzingFrame);

        FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.quizzingFrame,new QuestionFragment());
        ft.commit();



        askForPermissions();


    }

    private void askForPermissions() {

        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),PERMISSION_REQUEST_CODE );
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0) {
                    StringBuilder permissionsDenied = new StringBuilder();
                    for (String per : permissionsList) {
                        if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                            permissionsDenied.append("\n").append(per);

                        }

                    }
                    // Show permissionsDenied
                    if(permissionsDenied.length()!=0) {
//                        Toast.makeText(this, "Please provide the required permissions", Toast.LENGTH_SHORT).show();
                        askForPermissions();
                    }
                }
            }
        }
    }

}
