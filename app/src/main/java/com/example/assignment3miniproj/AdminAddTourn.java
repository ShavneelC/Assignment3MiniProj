package com.example.assignment3miniproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignment3miniproj.Models.Quiz;
import com.example.assignment3miniproj.REST.Controller;
import com.example.assignment3miniproj.REST.Methods;
import com.example.assignment3miniproj.RVAdapters.TournAddAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminAddTourn extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText tournname, startDate, endDate, questions;
    Button Add, AddBack, save;
    DatePickerDialog.OnDateSetListener setListener;
    DatePickerDialog.OnDateSetListener setListener2;
    Spinner spinnerCategories, spinnerDifficulty;
    RequestQueue mQueue;
    ArrayList<Quiz> quizList, fbList;
    RecyclerView rv;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_tourn);
        tournname = findViewById(R.id.etTournNameAdd);
        startDate = findViewById(R.id.etTournStartDateAdd);
        endDate = findViewById(R.id.etTournEndDateAdd);
        questions = findViewById(R.id.etTournQuestionsAdd);
        Add = findViewById(R.id.btnTournAdd);
        AddBack = findViewById(R.id.btnTournBackAdd);
        save = findViewById(R.id.btnTournSave);
        spinnerCategories = findViewById(R.id.spinnerCategoriesAdd);
        spinnerDifficulty = findViewById(R.id.spinnerDifficultyAdd);
        db = FirebaseFirestore.getInstance();


        //Volley
        mQueue = Volley.newRequestQueue(this);
        quizList = new ArrayList<>();
        fbList = new ArrayList<>();

        //recyclerview
        rv = findViewById(R.id.rvTournAdd);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //Array adapter for spinners
        ArrayAdapter<CharSequence> adapterCat = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCategories.setAdapter(adapterCat);
        spinnerCategories.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterDiff = ArrayAdapter.createFromResource(this,R.array.difficulty, android.R.layout.simple_spinner_item);
        adapterDiff.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerDifficulty.setAdapter(adapterDiff);
        spinnerDifficulty.setOnItemSelectedListener(this);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar calendar2 = Calendar.getInstance();
        final int year2 = calendar2.get(Calendar.YEAR);
        final int month2 = calendar2.get(Calendar.MONTH);
        final int day2 = calendar2.get(Calendar.DAY_OF_MONTH);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminAddTourn.this, android.R.style.Theme_Holo_Light_DarkActionBar,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(AdminAddTourn.this, android.R.style.Theme_Holo_Light_DarkActionBar,setListener2,year2,month2,day2);
                datePickerDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog2.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                month = month+1;
                String date = dayOfMonth+"/"+month+"/"+year;
                startDate.setText(date);
            }
        };

        setListener2 = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year2, int month2, int dayOfMonth2){
                month2 = month2+1;
                String date2 = dayOfMonth2+"/"+month2+"/"+year2;
                endDate.setText(date2);
            }
        };

        AddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeAdminActivity.class));
            }
        });


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuiz();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                quizList.clear();
            }
        });

    }

    private void insertData() {
        String category = spinnerCategories.getSelectedItem().toString();
        String categoryNumber = category.replaceAll("[^0-9]","");
        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        Map<String,Object> map=new HashMap<>();
        map.put("name", tournname.getText().toString());
        map.put("startDate", startDate.getText().toString());
        map.put("endDate", endDate.getText().toString());
        map.put("Category", category);
        map.put("Difficulty", difficulty);
        map.put("Quiz", quizList);


        db.collection("Tournament")
                        .add(map)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        tournname.setText("");
                                        startDate.setText("");
                                        endDate.setText("");
                                        Toast.makeText(getApplicationContext(),"Tournament has been added",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                                        }
                                });


//        FirebaseDatabase.getInstance().getReference().child("Tournament").push()
//                .setValue(map)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        tournname.setText("");
//                        startDate.setText("");
//                        endDate.setText("");
//                        Toast.makeText(getApplicationContext(),"Tournament has been added",Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//                )
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
//                    }
//                });

    }

    private void getQuiz() {
        String category = spinnerCategories.getSelectedItem().toString();
        String categoryNumber = category.replaceAll("[^0-9]","");
        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        String url = "https://opentdb.com/api.php?amount=10&category=" + categoryNumber + "&difficulty=" + difficulty + "&type=boolean";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
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
                    rv.setAdapter(new TournAddAdapter(AdminAddTourn.this, quizList));

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}