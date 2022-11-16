package com.example.assignment3miniproj.RVAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.assignment3miniproj.AdminAddTourn;
import com.example.assignment3miniproj.Models.Quiz;
import com.example.assignment3miniproj.R;

import java.util.ArrayList;

public class TournAddAdapter extends RecyclerView.Adapter<TournAddAdapter.holder> {
    AdminAddTourn adminAddTourn;
    ArrayList<Quiz> quizList;



    public TournAddAdapter(AdminAddTourn adminAddTourn, ArrayList<Quiz> quizList) {
        this.adminAddTourn = adminAddTourn;
        this.quizList = quizList;

    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(adminAddTourn).inflate(R.layout.activity_admin_add_tourn_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        holder.category.setText(quizList.get(position).getCategory());
        holder.difficulty.setText(quizList.get(position).getDifficulty());
        holder.question.setText(quizList.get(position).getQuestion());
        holder.answer.setText(quizList.get(position).getCorrect_answer());



    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    class holder extends RecyclerView.ViewHolder{

        TextView category, difficulty, question, answer ;

        public holder(@NonNull View itemView) {
            super(itemView);
            category = (TextView)itemView.findViewById(R.id.tvCategoryCardview);
            difficulty = (TextView)itemView.findViewById(R.id.tvDifficultyCardview);
            question = (TextView)itemView.findViewById(R.id.tvQuestionCardview);
            answer = (TextView)itemView.findViewById(R.id.tvCorrectAnswerCardview);
        }
    }

}
