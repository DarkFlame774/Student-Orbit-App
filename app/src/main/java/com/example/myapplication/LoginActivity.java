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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    private EditText userEmail, userPassword;
    private TextView tvShow;
    private RelativeLayout loginBtn;

    private String email, password;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("islogin", "false").equals("true")) {
            openDash();
        }

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
        email = userEmail.getText().toString().trim();
        password = userPassword.getText().toString().trim();

        if (email.isEmpty()) {
            userEmail.setError("Required");
            userEmail.requestFocus();
        } else if (password.isEmpty()) {
            userPassword.setError("Required");
            userPassword.requestFocus();
        } else if (email.equals("admin@gmail.com") && password.equals("12345")) {
            editor.putString("islogin", "yes");
            editor.commit();
            openDash();
        } else {
            Toast.makeText(LoginActivity.this, "Please check email and password again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openDash() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}