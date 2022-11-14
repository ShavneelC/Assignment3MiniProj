package com.example.assignment3miniproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    EditText usernameet, passwordet;
    Button registerbtn;

    TextView loginTv;
    RadioGroup radiogroup;
    RadioButton rPlayerBtn, reAdminBtn;
    DatabaseReference dr;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameet = findViewById(R.id.etUsernameReg);
        passwordet = findViewById(R.id.etPasswordReg);
        registerbtn = findViewById(R.id.btnRegister);
        loginTv = findViewById(R.id.tvLoginHereReg);
        radiogroup = findViewById(R.id.radio_group);
        rPlayerBtn = findViewById(R.id.rBtnPlayer);
        reAdminBtn = findViewById(R.id.rBtnAdmin);

        mauth = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference("Users");


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameet.getText().toString();
                String password = passwordet.getText().toString();
                String userType = "";


                //dr = FirebaseDatabase.getInstance().getReference("User");

                if (rPlayerBtn.isChecked()){
                    userType = "Player";
                }
                if(reAdminBtn.isChecked()){
                    userType = "Admin";
                }


                if (TextUtils.isEmpty(username)){
                    usernameet.setError("Please enter an email");
                    usernameet.requestFocus();
                }
                else if (TextUtils.isEmpty(password)){
                    passwordet.setError("Please enter a valid password");
                    passwordet.requestFocus();
                }

                String finalUserType = userType;
                mauth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(finalUserType);

                            FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(RegistrationActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    });
                            }
                        else {
                            Toast.makeText(RegistrationActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);

               startActivity(intent);
            }
        });
    }

    public void createUser(){
        String username = usernameet.getText().toString();
        String password = passwordet.getText().toString();
        String utype = "";
        dr = FirebaseDatabase.getInstance().getReference("User");

        if (rPlayerBtn.isChecked()){
            utype = "Player";
        }
        if(reAdminBtn.isChecked()){
            utype = "Admin";
        }

        if (TextUtils.isEmpty(username)){
            usernameet.setError("Please enter an email");
            usernameet.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            passwordet.setError("Please enter a valid password");
            passwordet.requestFocus();
            }
        else{
            mauth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegistrationActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    }
                    else {
                        Toast.makeText(RegistrationActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
