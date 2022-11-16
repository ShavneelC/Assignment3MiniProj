package com.example.assignment3miniproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.assignment3miniproj.Models.Quiz;
import com.example.assignment3miniproj.Models.Tournament;
import com.example.assignment3miniproj.RVAdapters.TourAddAdapterPL;
import com.example.assignment3miniproj.RVAdapters.TourViewAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    RecyclerView rv;
    TourAddAdapterPL adapter;
    Map<String,Object> quizList;
    FirebaseFirestore db;
    ArrayList<Quiz> list;
    Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        quizList = new HashMap<>();
        list = new ArrayList<>();

        rv=(RecyclerView)findViewById(R.id.RvAllQuizzesPL);
        rv.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();



//        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Tournament");
//        dr.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot shot : snapshot.getChildren()){
//                    String categoryquiz = Objects.requireNonNull(shot.child("0").getValue()).toString();
//                    quiz = new Quiz();
//                    quiz.setCategory(categoryquiz);
//                    list.add(quiz);
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



        FirebaseRecyclerOptions<Tournament> options =
                new FirebaseRecyclerOptions.Builder<Tournament>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tournament"), Tournament.class)
                        .build();

        adapter=new TourAddAdapterPL(options);
        rv.setAdapter(adapter);

//        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Tournament");
//        dr.child("Difficulty").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot snapshot1: snapshot.getChildren()){
//                    String usertype = snapshot1.getValue().toString();
//                    quizList.put("Question", usertype);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemhomePl:
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            case R.id.itemlogoutPl:
                FirebaseAuth.getInstance()
                        .signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}