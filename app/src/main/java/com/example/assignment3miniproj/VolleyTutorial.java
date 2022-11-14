package com.example.assignment3miniproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignment3miniproj.Models.Quiz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolleyTutorial extends AppCompatActivity {

    EditText t1, t2;
    TextView tv1;
    Button b1;
    RequestQueue mQueue;
    ArrayList<Quiz> quizList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_tutorial);

        t1 = findViewById(R.id.et1);
        t2 = findViewById(R.id.et2);
        tv1 = findViewById(R.id.tvResults);
        b1 = findViewById(R.id.btn1);
        mQueue = Volley.newRequestQueue(this);
        quizList = new ArrayList<>();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuiz();

            }
        });

    }

    private void getQuiz() {
        String url = "https://opentdb.com/api.php?amount=10";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i =0; i < jsonArray.length(); i++){
                        JSONObject quiz = jsonArray.getJSONObject(i);
                        Quiz singlequiz = new Quiz(
                                quiz.getString("category"),
                                quiz.getString("type"),
                                quiz.getString("difficulty"),
                                quiz.getString("question"),
                                quiz.getString("correct_answer")
                        );
                        quizList.add(singlequiz);
                    }

                    Log.e("api" , "onResponse: "+quizList.size());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("api" , "onResponse: "+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);

    }


}