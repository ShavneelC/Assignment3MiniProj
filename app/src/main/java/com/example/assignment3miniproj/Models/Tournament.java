package com.example.assignment3miniproj.Models;

import java.util.ArrayList;

public class Tournament {
    String name, Category, Difficulty, endDate, startDate;
    ArrayList<Quiz> quizList;

    Tournament(){}

    public Tournament(String name, String category, String difficulty, String endDate, String startDate, ArrayList<Quiz> quizList) {
        this.name = name;
        Category = category;
        Difficulty = difficulty;
        this.endDate = endDate;
        this.startDate = startDate;
        this.quizList = quizList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDifficulty() {
        return Difficulty;
    }

    public void setDifficulty(String difficulty) {
        Difficulty = difficulty;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public ArrayList<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(ArrayList<Quiz> quizList) {
        this.quizList = quizList;
    }
}
