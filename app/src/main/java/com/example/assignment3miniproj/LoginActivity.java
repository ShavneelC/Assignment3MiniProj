package com.example.assignment3miniproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button loginbtn;
    EditText loginEt, passwordEt;
    TextView regtv;
    FirebaseDatabase fd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEt = findViewById(R.id.etlogin);
        passwordEt = findViewById(R.id.etpassword);
        loginbtn = findViewById(R.id.btnLogin);
        regtv = findViewById(R.id.tvRegisterHerelog);


        mAuth= FirebaseAuth.getInstance();
        fd = FirebaseDatabase.getInstance();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();

            }
        });

        regtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(LoginActivity.this, RegistrationActivity.class));
                startActivity(intent);
            }
        });
    }

    private void loginUser(){
        String username = loginEt.getText().toString();
        String password = passwordEt.getText().toString();

        if (TextUtils.isEmpty(username)){
            loginEt.setError("Please enter an email");
            loginEt.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            passwordEt.setError("Please enter a valid password");
            passwordEt.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel(mAuth.getCurrentUser().getUid());
                        
//                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                        startActivity(intent);

                    }
                    else{
                            Toast.makeText(LoginActivity.this, "Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                }

            });

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
                    startActivity(new Intent(LoginActivity.this, HomeAdminActivity.class));
                    finish();
                } else{
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}