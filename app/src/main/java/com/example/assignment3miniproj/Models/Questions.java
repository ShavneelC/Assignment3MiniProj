package com.example.assignment3miniproj.Models;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.assignment3miniproj.R;

public class Questions extends AppCompatActivity {
    private String question, correct_answer;

    public Questions(String question, String correct_answer) {
        this.question = question;
        this.correct_answer = correct_answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_quiz);
    }
}