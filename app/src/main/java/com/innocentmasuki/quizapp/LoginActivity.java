package com.innocentmasuki.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    TextView gotToSignUp;
    Button logInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        gotToSignUp = findViewById(R.id.goToSignUp);
        logInBtn = findViewById(R.id.login);

        gotToSignUp.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
            finish();
        });

        logInBtn.setOnClickListener(v -> {
            loginUser();
        });
    }

    private void loginUser() {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        if(!userEmail.equals("") || !userPassword.equals("")){
            Intent i = new Intent(this, Questions.class);
            startActivity(i);
            finish();

        }else{
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}