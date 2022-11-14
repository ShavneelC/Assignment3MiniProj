package com.example.assignment3miniproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
// token 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

    FirebaseAuth mAuth;
    Button loginbtn;
    FirebaseDatabase fd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        fd = FirebaseDatabase.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            checkUserAccessLevel(mAuth.getCurrentUser().getUid());
        } else{
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }


    }

    private void checkUserAccessLevel(String uid) {

        DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Users");
        dr.child(uid).child("usertype").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.w("TAG","onSuccess" + snapshot.getValue());
                String usertype = snapshot.getValue().toString();

                if("Admin".equals(usertype)){
                    startActivity(new Intent(MainActivity.this, HomeAdminActivity.class));
                    finish();
                } else if ("Player".equals(usertype)){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}