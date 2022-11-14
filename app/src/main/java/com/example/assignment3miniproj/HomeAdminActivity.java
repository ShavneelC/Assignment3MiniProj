package com.example.assignment3miniproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

//import com.example.assignment3miniproj.Models.Quiz;
import com.example.assignment3miniproj.Models.Tournament;
import com.example.assignment3miniproj.RVAdapters.TourViewAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class HomeAdminActivity extends AppCompatActivity {
    FloatingActionButton fb;
    RecyclerView rv;
    TourViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        rv=(RecyclerView)findViewById(R.id.RvAllQuizzes);
        rv.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Tournament> options =
                new FirebaseRecyclerOptions.Builder<Tournament>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tournament"), Tournament.class)
                        .build();

        adapter=new TourViewAdapter(options);
        rv.setAdapter(adapter);

        fb = (FloatingActionButton)findViewById(R.id.fTournadd);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdminActivity.this, AdminAddTourn.class);
                startActivity(intent);
            }
        });

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemhome:
                startActivity(new Intent(HomeAdminActivity.this, LoginActivity.class));
            case R.id.itemlogout:
                FirebaseAuth.getInstance()
                        .signOut();
                startActivity(new Intent(HomeAdminActivity.this, LoginActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}