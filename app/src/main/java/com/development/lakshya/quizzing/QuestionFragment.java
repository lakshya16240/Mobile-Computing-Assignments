package com.development.lakshya.quizzing;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class QuestionFragment extends Fragment {

    private ArrayList<Question_A3_2016240> questions;
    private SQLiteOpenHelper helper;
    private SQLiteDatabase questionsDatabase;
    public static final String DB_NAME = "QuestionsTreasure";
    private RecyclerView rv_questions;
    private QuestionsAdapter_A3_2016240 questionsAdapter;
    public static final String TAG = "QuestionFragment";
    private Button bv_submitQuestion;
    private String fileName;
    private BufferedWriter writer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.question_fragment,container,false);

        rv_questions = view.findViewById(R.id.rv_questions);
        bv_submitQuestion = view.findViewById(R.id.bv_submitQuestions);

        rv_questions.setLayoutManager(new LinearLayoutManager(getActivity()));

        questions = new ArrayList<>();

        helper = new QuestionsDatabaseHelper_A3_2016240(getActivity(),DB_NAME,null,3);
        try {
            Log.d(TAG, "onCreateView: ");
            questionsDatabase = helper.getWritableDatabase();
        }catch (SQLiteException e){

        }

        Cursor cursor = questionsDatabase.query("QUESTIONS",new String[]{"QUESTION","OPTION1","OPTION2","ANSWER","SELECTED_ANSWER"},null,null,null,null,null);
        int count = 1;
        if(cursor.getCount()!=0) {

            if(cursor.moveToFirst()) {

                do{

                    String question = cursor.getString(cursor.getColumnIndex("QUESTION"));
                    String answer = cursor.getString(cursor.getColumnIndex("ANSWER"));
                    String selected_answer = cursor.getString(cursor.getColumnIndex("SELECTED_ANSWER"));
                    questions.add(new Question_A3_2016240(question,answer,selected_answer));
                }while(cursor.moveToNext());
            }
        }

        questionsAdapter = new QuestionsAdapter_A3_2016240(getActivity(), questions, new OnQuestionClickedListener() {
            @Override
            public void onQuestionClicked(Question_A3_2016240 question) {
                Log.d(TAG, "onQuestionClicked: ");
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = QuestionDetailsFragment_A3_2016240.newInstance(question,questionsDatabase);
                ft.replace(MainActivity_A3_2016240.quizzingFrame.getId(), fragment);
                ft.commit();
                ft.addToBackStack(null);
            }
        });
        rv_questions.setAdapter(questionsAdapter);

        bv_submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        final Cursor cursor = questionsDatabase.query("QUESTIONS",new String[]{"QUESTION","OPTION1","OPTION2","ANSWER","SELECTED_ANSWER"},null,null,null,null,null);
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
