package com.development.lakshya.quizzing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder> {

    private Context context;
    private ArrayList<Question> questions;
    private OnQuestionClickedListener onQuestionClickedListener;

    public QuestionsAdapter(Context context, ArrayList<Question> questions, OnQuestionClickedListener onQuestionClickedListener) {
        this.context = context;
        this.questions = questions;
        this.onQuestionClickedListener = onQuestionClickedListener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.list_item_question,viewGroup,false);
        return new QuestionViewHolder(itemView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionViewHolder questionViewHolder, int i) {

        final Question question = questions.get(i);
        questionViewHolder.tv_question.setText(question.getQuestion());
        questionViewHolder.tv_questionNumber.setText((i+1) + ". ");
        questionViewHolder.ll_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("QuestionAdapter", "onClick: ");
                onQuestionClickedListener.onQuestionClicked(question);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_question,tv_questionNumber;
        LinearLayout ll_question;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_question = itemView.findViewById(R.id.tv_question);
            tv_questionNumber = itemView.findViewById(R.id.tv_questionNumber);
            ll_question = itemView.findViewById(R.id.ll_question);
        }
    }
}
