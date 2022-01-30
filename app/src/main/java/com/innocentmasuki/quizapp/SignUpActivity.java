package com.innocentmasuki.quizapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.innocentmasuki.quizapp.RetrofitApi.Auth.Register;
import com.innocentmasuki.quizapp.RetrofitApi.DataAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SignUpActivity extends AppCompatActivity {

 public static final String BASE_URL =  "http://192.168.43.73/quizapp/";
    static final Gson GSON = new GsonBuilder()
            .setLenient()
            .create();

    public static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .build();

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    DataAPI service = retrofit.create(DataAPI.class);

    public static final String MyPREFERENCES = "USER" ;
    public static final String Status = "status";
    public static final String Email = "email";
    public static final String Password = "password";

    SharedPreferences sharedpreferences;


    EditText email, password;
    TextView gotoLogin;
    Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        gotoLogin =  findViewById(R.id.goToLogIn);
        signUp =  findViewById(R.id.signup);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(sharedpreferences.getString("status", "").equals("active")){
            String userEmail = sharedpreferences.getString(Status, "");
            String userPassword = sharedpreferences.getString(Password, "");
            Intent main = new Intent(getApplicationContext(), Home.class);
            main.putExtra("EMAIL", userEmail);
            startActivity(main);
            finish();
        }

        gotoLogin.setOnClickListener(v -> {
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();
        });
        signUp.setOnClickListener(v -> signUpUser());
    }

    private void signUpUser() {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
            if(!userEmail.equals("") || !userPassword.equals("")){
                if (userEmail.matches(emailPattern)){
                    if (!(userPassword.length() < 8)){
                        Call<Register> responses = service.register(userEmail,userPassword);
                        responses.enqueue(new Callback<Register>() {
                            @Override
                            public void onResponse(@NonNull Call<Register> call, @NonNull Response<Register> response) {

                                assert response.body() != null;
                                if (response.body().getMessage().equals("success")){
                                    SharedPreferences.Editor editor = sharedpreferences.edit();

                                    editor.putString(Status, "active");
                                    editor.putString(Email, userEmail);
                                    editor.putString(Password, userPassword);
                                    editor.apply();

                                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                }else if(response.body().getMessage().equals("duplicate")){
                                        Toast.makeText(SignUpActivity.this, "User already exist.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(SignUpActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Register> call, @NonNull Throwable t) {
                                Toast.makeText(SignUpActivity.this, "Can't Sign Up.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(),"You must have 8 characters in your password", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }




    }
}