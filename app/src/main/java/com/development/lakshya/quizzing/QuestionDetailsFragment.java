package com.development.lakshya.quizzing;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class QuestionDetailsFragment extends Fragment {

    private CheckBox cb_option1,cb_option2;
    private static Question question;
    private TextView tv_questionDetail;
    private Button bv_save, bv_submit;
    private static SQLiteDatabase db;
    public static final String TAG = "QuestionDetails";
    private BufferedWriter writer;
    private String fileName;


    public static Fragment newInstance(Question q, SQLiteDatabase questions){
        question = q;
        db = questions;
        return new QuestionDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.question_details_fragment,container,false);

        cb_option1 = view.findViewById(R.id.cb_option1);
        cb_option2 = view.findViewById(R.id.cb_option2);
        tv_questionDetail = view.findViewById(R.id.tv_questionDetail);
        bv_save = view.findViewById(R.id.bv_save);
        bv_submit = view.findViewById(R.id.bv_submit);

        tv_questionDetail.setText(question.getQuestion());

        Log.d(TAG, "onCreateView: " + question.getSelectedAnswer());

        if(!question.getSelectedAnswer().equals("")){
            if(question.getSelectedAnswer().equalsIgnoreCase("true"))
                cb_option1.setChecked(true);
            else
                cb_option2.setChecked(true);
        }

        cb_option1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    question.setSelectedAnswer(cb_option1.getText().toString());
                    Log.d(TAG, "onCheckedChanged: option1" + question.getSelectedAnswer());
                    cb_option2.setChecked(false);
                }
//                else
//                    question.setSelectedAnswer(null);
            }
        });

        cb_option2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    question.setSelectedAnswer(cb_option2.getText().toString());
                    Log.d(TAG, "onCheckedChanged: option2" + question.getSelectedAnswer());
                    cb_option1.setChecked(false);
                }
//                else
//                    question.setSelectedAnswer("");
            }
        });

        bv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("SELECTED_ANSWER",question.getSelectedAnswer());
                Log.d(TAG, "onClick: " + question.getSelectedAnswer());
                db.update("QUESTIONS",cv,"QUESTION=?",new String[]{question.getQuestion()});
            }
        });

        bv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String csv = android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath()+"/"+"Questions.csv";
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+File.separator+"QuestionsFolder", "Questions");

                if (!file.exists())
                {
                    file.mkdirs();
                }


                fileName = file.toString() + "/" + "Questions.csv";
                File questionsCsv = new File(fileName);
                try {
                    Log.d(TAG, "onClick: " + questionsCsv);
                    FileWriter fileWriter = new FileWriter(questionsCsv,true);

                    Log.d(TAG, "onClick: " + fileWriter);

                    writer = new BufferedWriter(fileWriter);
                    Log.d(TAG, "onClick: "+writer);
                    Log.d(TAG, "onClick: "  + writer);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    populateToCsv();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });


        return view;
    }

    private void populateToCsv() throws InterruptedException {

        final Cursor cursor = db.query("QUESTIONS",new String[]{"QUESTION","OPTION1","OPTION2","ANSWER","SELECTED_ANSWER"},null,null,null,null,null);
        if(cursor.getCount()>0){
//            int count = 1;

            Thread thread = new Thread(){
                @Override
                public void run() {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        Log.d(TAG, "populate: ");
                        for (int j = 0; j < cursor.getColumnCount(); j++) {
                            Log.d(TAG, "run: ");
                            if (j < cursor.getColumnCount() - 1) {
                                try {

                                    writer.write(cursor.getString(j) + ",");

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {

                                    writer.write(cursor.getString(j));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        try {
                            writer.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            };
            thread.start();
            thread.join();
            Toast.makeText(getActivity(), "Data Exported", Toast.LENGTH_SHORT).show();

            if(isNetworkAvailable()){

                String name= fileName.substring(fileName.lastIndexOf('/') + 1);
                ProgressDialog mDialog;
                mDialog = new ProgressDialog(getActivity());
                mDialog.setMax(100);
                mDialog.setMessage("Uploading " + name);
                mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mDialog.setProgress(0);
                mDialog.show();

                Object[] objects = new Object[3];
                objects[0] = fileName;
                objects[1] = getActivity();
                objects[2] = mDialog;
                UploadAsyncTask uploadAsyncTask = new UploadAsyncTask();
                uploadAsyncTask.execute(objects);

            }
            else
                Toast.makeText(getActivity(), "Create", Toast.LENGTH_SHORT).show();

        }
    }



    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }
}
