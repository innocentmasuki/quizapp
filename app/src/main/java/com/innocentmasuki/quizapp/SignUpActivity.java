package com.innocentmasuki.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, password;
    TextView gotoLogin;
    Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        gotoLogin =  findViewById(R.id.goToLogIn);
        signUp =  findViewById(R.id.signup);

        gotoLogin.setOnClickListener(v -> {
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();
        });
        signUp.setOnClickListener(v -> {
            signUpUser();
        });
    }

    private void signUpUser() {
        String username = name.getText().toString().trim();
        String email = name.getText().toString().trim();
        String password = name.getText().toString().trim();

        if(!username.equals("") || !email.equals("") || !password.equals("")){
            Intent i = new Intent(this, Questions.class);
            startActivity(i);
            finish();
//            Toast.makeText(this, "Send data to database", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}