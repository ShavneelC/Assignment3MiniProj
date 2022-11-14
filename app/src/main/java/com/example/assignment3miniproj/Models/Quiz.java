package com.example.assignment3miniproj.Models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Quiz {
    String category, type, difficulty, question, correct_answer;

    Quiz(){}

    public Quiz(String category, String type, String difficulty, String question, String correct_answer) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correct_answer = correct_answer;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }
}
