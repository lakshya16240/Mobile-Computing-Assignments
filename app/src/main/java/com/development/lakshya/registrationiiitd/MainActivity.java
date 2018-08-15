package com.development.lakshya.registrationiiitd;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static boolean onCreate=false, onResume=false, onStop=false, onStart=false, onRestart=false, onDestroy=false,
            onPause=false;

    private EditText et_name, et_roll,et_branch,et_course1,et_course2,et_course3,et_course4;

    public static final String TAG = "MobileComputing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onCreate = true;

        Log.d(TAG, "State of MainActivity is onCreate");
        Toast.makeText(this, "State of MainActivity is onCreate", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_main);

        et_name = findViewById(R.id.et_name);
        et_roll = findViewById(R.id.et_rollNumber);
        et_branch = findViewById(R.id.et_branch);
        et_course1 = findViewById(R.id.et_course1);
        et_course2 = findViewById(R.id.et_course2);
        et_course3 = findViewById(R.id.et_course3);
        et_course4 = findViewById(R.id.et_course4);

        ((Button)findViewById(R.id.bv_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String roll = et_roll.getText().toString();
                String branch = et_branch.getText().toString();
                String course1 = et_course1.getText().toString();
                String course2 = et_course2.getText().toString();
                String course3 = et_course3.getText().toString();
                String course4 = et_course4.getText().toString();

                boolean validation = validate(name, roll, branch, course1, course2, course3, course4);

                if(validation) {

                    Student student = new Student(name, roll, branch, course1, course2, course3, course4);

                    et_name.setText("");
                    et_roll.setText("");
                    et_branch.setText("");
                    et_course1.setText("");
                    et_course2.setText("");
                    et_course3.setText("");
                    et_course4.setText("");

                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    intent.putExtra("student", student);
                    startActivity(intent);
                }
            }
        });

        ((Button)findViewById(R.id.bv_clear)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_name.setText("");
                et_roll.setText("");
                et_branch.setText("");
                et_course1.setText("");
                et_course2.setText("");
                et_course3.setText("");
                et_course4.setText("");
            }
        });
    }

    private boolean validate(String name, String roll, String branch, String course1, String course2, String course3, String course4) {

        if(name.length()==0){
            et_name.requestFocus();
            et_name.setError("Please enter a name");
            return false;
        }
        if(roll.length()==0){
            et_roll.requestFocus();
            et_roll.setError("Please enter a valid roll number");
            return false;
        }
        if(branch.length()==0){
            et_branch.requestFocus();
            et_branch.setError("Please enter your branch");
            return false;
        }
        if(course1.length()==0){
            et_course1.requestFocus();
            et_course1.setError("Please enter all the 4 courses");
            return false;
        }
        if(course2.length()==0){
            et_course2.requestFocus();
            et_course2.setError("Please enter all the 4 courses");
            return false;
        }
        if(course3.length()==0){
            et_course3.requestFocus();
            et_course3.setError("Please enter all the 4 courses");
            return false;
        }
        if(course4.length()==0){
            et_course4.requestFocus();
            et_course4.setError("Please enter all the 4 courses");
            return false;
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(onCreate){

            Log.d(TAG, "State of MainActivity changed from Create to Start");
            Toast.makeText(this, "State of MainActivity changed from Create to Start", Toast.LENGTH_SHORT).show();
            onStart = true;
            onCreate = false;

        }
        else if(onRestart){

            Log.d(TAG, "State of MainActivity changed from Restart to Start");
            Toast.makeText(this, "State of MainActivity changed from Restart to Start", Toast.LENGTH_SHORT).show();
            onStart = true;
            onRestart = false;

        }
        else if(onResume){

            Log.d(TAG, "State of MainActivity changed from Resume to Start");
            Toast.makeText(this, "State of MainActivity changed from Resume to Start", Toast.LENGTH_SHORT).show();
            onStart = true;
            onResume = false;

        }
        else if(onStop){

            Log.d(TAG, "State of MainActivity changed from Stop to Start");
            Toast.makeText(this, "State of MainActivity changed from Stop to Start", Toast.LENGTH_SHORT).show();
            onStart = true;
            onStop = false;

        }
        else if(onDestroy){

            Log.d(TAG, "State of MainActivity changed from Destroy to Start");
            Toast.makeText(this, "State of MainActivity changed from Destroy to Start", Toast.LENGTH_SHORT).show();
            onStart = true;
            onDestroy = false;

        }
        else if(onPause){

            Log.d(TAG, "State of MainActivity changed from Pause to Start");
            Toast.makeText(this, "State of MainActivity changed from Pause to Start", Toast.LENGTH_SHORT).show();
            onStart = true;
            onPause = false;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(onCreate){

            Log.d(TAG, "State of MainActivity changed from Create to Stop");
            Toast.makeText(this, "State of MainActivity changed from Create to Stop", Toast.LENGTH_SHORT).show();
            onStop = true;
            onCreate = false;

        }
        else if(onRestart){

            Log.d(TAG, "State of MainActivity changed from Restart to Stop");
            Toast.makeText(this, "State of MainActivity changed from Restart to Stop", Toast.LENGTH_SHORT).show();
            onStop = true;
            onRestart = false;

        }
        else if(onResume){

            Log.d(TAG, "State of MainActivity changed from Resume to Stop");
            Toast.makeText(this, "State of MainActivity changed from Resume to Stop", Toast.LENGTH_SHORT).show();
            onStop = true;
            onResume = false;

        }
        else if(onStart){

            Log.d(TAG, "State of MainActivity changed from Start to Stop");
            Toast.makeText(this, "State of MainActivity changed from Start to Stop", Toast.LENGTH_SHORT).show();
            onStop = true;
            onStart = false;

        }
        else if(onPause){

            Log.d(TAG, "State of MainActivity changed from Pause to Stop");
            Toast.makeText(this, "State of MainActivity changed from Pause to Stop", Toast.LENGTH_SHORT).show();
            onStop = true;
            onPause = false;

        }
        else if(onDestroy){

            Log.d(TAG, "State of MainActivity changed from Destroy to Stop");
            Toast.makeText(this, "State of MainActivity changed from Destroy to Stop", Toast.LENGTH_SHORT).show();
            onStop = true;
            onDestroy = false;

        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(onCreate){

            Log.d(TAG, "State of MainActivity changed from Create to Destroy");
            Toast.makeText(this, "State of MainActivity changed from Create to Destroy", Toast.LENGTH_SHORT).show();
            onDestroy = true;
            onCreate = false;

        }
        else if(onRestart){

            Log.d(TAG, "State of MainActivity changed from Restart to Destroy");
            Toast.makeText(this, "State of MainActivity changed from Restart to Destroy", Toast.LENGTH_SHORT).show();
            onDestroy = true;
            onRestart = false;

        }
        else if(onResume){

            Log.d(TAG, "State of MainActivity changed from Resume to Destroy");
            Toast.makeText(this, "State of MainActivity changed from Resume to Destroy", Toast.LENGTH_SHORT).show();
            onDestroy = true;
            onResume = false;

        }
        else if(onStart){

            Log.d(TAG, "State of MainActivity changed from Start to Destroy");
            Toast.makeText(this, "State of MainActivity changed from Start to Destroy", Toast.LENGTH_SHORT).show();
            onDestroy = true;
            onStart = false;

        }
        else if(onStop){

            Log.d(TAG, "State of MainActivity changed from Stop to Destroy");
            Toast.makeText(this, "State of MainActivity changed from Stop to Destroy", Toast.LENGTH_SHORT).show();
            onDestroy = true;
            onStop = false;

        }
        else if(onPause){

            Log.d(TAG, "State of MainActivity changed from Pause to Destroy");
            Toast.makeText(this, "State of MainActivity changed from Pause to Destroy", Toast.LENGTH_SHORT).show();
            onDestroy = true;
            onPause = false;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(onCreate){

            Log.d(TAG, "State of MainActivity changed from Create to Resume");
            Toast.makeText(this, "State of MainActivity changed from Create to Resume", Toast.LENGTH_SHORT).show();
            onResume = true;
            onCreate = false;

        }
        else if(onRestart){

            Log.d(TAG, "State of MainActivity changed from Restart to Resume");
            Toast.makeText(this, "State of MainActivity changed from Restart to Resume", Toast.LENGTH_SHORT).show();
            onResume = true;
            onRestart = false;

        }
        else if(onStop){

            Log.d(TAG, "State of MainActivity changed from Stop to Resume");
            Toast.makeText(this, "State of MainActivity changed from Stop to Resume", Toast.LENGTH_SHORT).show();
            onResume = true;
            onStop = false;

        }
        else if(onStart){

            Log.d(TAG, "State of MainActivity changed from Start to Resume");
            Toast.makeText(this, "State of MainActivity changed from Start to Resume", Toast.LENGTH_SHORT).show();
            onResume = true;
            onStart = false;

        }
        else if(onDestroy){

            Log.d(TAG, "State of MainActivity changed from Destroy to Resume");
            Toast.makeText(this, "State of MainActivity changed from Destroy to Resume", Toast.LENGTH_SHORT).show();
            onResume = true;
            onDestroy = false;

        }
        else if(onPause){

            Log.d(TAG, "State of MainActivity changed from Pause to Resume");
            Toast.makeText(this, "State of MainActivity changed from Pause to Resume", Toast.LENGTH_SHORT).show();
            onResume = true;
            onPause = false;

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(onCreate){

            Log.d(TAG, "State of MainActivity changed from Create to Pause");
            Toast.makeText(this, "State of MainActivity changed from Create to Pause", Toast.LENGTH_SHORT).show();
            onPause = true;
            onCreate = false;

        }
        else if(onRestart){

            Log.d(TAG, "State of MainActivity changed from Restart to Pause");
            Toast.makeText(this, "State of MainActivity changed from Restart to Pause", Toast.LENGTH_SHORT).show();
            onPause = true;
            onRestart = false;

        }
        else if(onResume){

            Log.d(TAG, "State of MainActivity changed from Resume to Pause");
            Toast.makeText(this, "State of MainActivity changed from Resume to Pause", Toast.LENGTH_SHORT).show();
            onPause = true;
            onResume = false;

        }
        else if(onStart){

            Log.d(TAG, "State of MainActivity changed from Start to Pause");
            Toast.makeText(this, "State of MainActivity changed from Start to Pause", Toast.LENGTH_SHORT).show();
            onPause = true;
            onStart = false;

        }
        else if(onDestroy){

            Log.d(TAG, "State of MainActivity changed from Destroy to Pause");
            Toast.makeText(this, "State of MainActivity changed from Destroy to Pause", Toast.LENGTH_SHORT).show();
            onPause = true;
            onDestroy = false;

        }
        else if(onStop){

            Log.d(TAG, "State of MainActivity changed from Stop to Pause");
            Toast.makeText(this, "State of MainActivity changed from Stop to Pause", Toast.LENGTH_SHORT).show();
            onPause = true;
            onStop = false;

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(onCreate){

            Log.d(TAG, "State of MainActivity changed from Create to Restart");
            Toast.makeText(this, "State of MainActivity changed from Create to Restart", Toast.LENGTH_SHORT).show();
            onRestart = true;
            onCreate = false;

        }
        else if(onStop){

            Log.d(TAG, "State of MainActivity changed from Stop to Restart");
            Toast.makeText(this, "State of MainActivity changed from Stop to Restart", Toast.LENGTH_SHORT).show();
            onRestart = true;
            onStop = false;

        }
        else if(onResume){

            Log.d(TAG, "State of MainActivity changed from Resume to Restart");
            Toast.makeText(this, "State of MainActivity changed from Resume to Restart", Toast.LENGTH_SHORT).show();
            onRestart = true;
            onResume = false;

        }
        else if(onStart){

            Log.d(TAG, "State of MainActivity changed from Start to Restart");
            Toast.makeText(this, "State of MainActivity changed from Start to Restart", Toast.LENGTH_SHORT).show();
            onRestart = true;
            onStart = false;

        }
        else if(onDestroy){

            Log.d(TAG, "State of MainActivity changed from Destroy to Restart");
            Toast.makeText(this, "State of MainActivity changed from Destroy to Restart", Toast.LENGTH_SHORT).show();
            onRestart = true;
            onDestroy = false;

        }
        else if(onPause){

            Log.d(TAG, "State of MainActivity changed from Pause to Restart");
            Toast.makeText(this, "State of MainActivity changed from Pause to Restart", Toast.LENGTH_SHORT).show();
            onRestart = true;
            onPause = false;

        }
    }

}
