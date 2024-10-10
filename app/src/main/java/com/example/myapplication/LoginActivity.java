package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText userEmail, userPassword;
    private TextView tvShow;
    private RelativeLayout loginBtn;
    private FirebaseAuth auth;

    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        tvShow = findViewById(R.id.txt_show);
        loginBtn = findViewById(R.id.login_btn);

        tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userPassword.getInputType() == 144) {
                    userPassword.setInputType(129);
                    tvShow.setText("Hide");
                } else {
                    userPassword.setInputType(144);
                    tvShow.setText("Show");
                }
                userPassword.setSelection(userPassword.getText().length());
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData() {
        email = userEmail.getText().toString();
        password = userPassword.getText().toString();

        if (email.isEmpty()) {
            userEmail.setError("Required");
            userEmail.requestFocus();
        } else if (password.isEmpty()) {
            userPassword.setError("Required");
            userPassword.requestFocus();
        } else {
           loginUser();
        }
    }

    private void loginUser() {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            openDash();
                        }else{
                            Toast.makeText(LoginActivity.this,"Error : "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this,"Error : "+ e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openDash() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}