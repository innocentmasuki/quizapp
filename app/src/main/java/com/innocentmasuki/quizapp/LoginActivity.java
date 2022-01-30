package com.innocentmasuki.quizapp;

import static com.innocentmasuki.quizapp.SignUpActivity.BASE_URL;
import static com.innocentmasuki.quizapp.SignUpActivity.Email;
import static com.innocentmasuki.quizapp.SignUpActivity.MyPREFERENCES;
import static com.innocentmasuki.quizapp.SignUpActivity.Password;
import static com.innocentmasuki.quizapp.SignUpActivity.Status;
import static com.innocentmasuki.quizapp.SignUpActivity.retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.innocentmasuki.quizapp.RetrofitApi.DataAPI;
import com.innocentmasuki.quizapp.RetrofitApi.Auth.Auth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    TextView gotToSignUp;
    Button logInBtn;


    SharedPreferences sharedpreferences;

    DataAPI userServices = retrofit.create(DataAPI.class);

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
         sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(sharedpreferences.getString("status", "").equals("active")){
            String userEmail = sharedpreferences.getString(Status, "");
            String userPassword = sharedpreferences.getString(Password, "");
            Intent main = new Intent(getApplicationContext(), Home.class);
            main.putExtra("EMAIL", userEmail);
            startActivity(main);
            finish();
        }

        logInBtn.setOnClickListener(v -> {
            userAuthenticate();
        });
    }

    private void userAuthenticate() {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        if(!userEmail.equals("") || !userPassword.equals("")){
            logInBtn.setEnabled(false);
            Call<Auth> call = userServices.authenticate(userEmail,userPassword);
            call.enqueue(new Callback<Auth>() {
                @Override
                public void onResponse(@NonNull Call<Auth> call, @NonNull Response<Auth> response) {
                    if(response.isSuccessful()){
                        assert response.body() != null;
                        if(response.body().getMessage().equals("success")){
                            String email = response.body().getEmail();
                            String role = response.body().getRole();

                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString(Status, "active");
                            editor.putString(Email, userEmail);
                            editor.putString(Password, userPassword);
                            editor.apply();

                            Intent main = new Intent(getApplicationContext(), Home.class);
                            main.putExtra("EMAIL", email);
                            main.putExtra("ROLE", role);
                            startActivity(main);
                            finish();

                        }else if(response.body().getMessage().equals("Incorrect")){
                            logInBtn.setEnabled(true);
                            Toast.makeText(LoginActivity.this, "Wrong email or password.", Toast.LENGTH_SHORT).show();
                            Log.d("Response",response.body().getMessage());
                        }else{
                            logInBtn.setEnabled(true);
                            Toast.makeText(LoginActivity.this, "Can't Authenticate.", Toast.LENGTH_SHORT).show();
                            Log.d("Response",response.body().getMessage());
                        }

                    }else {
                        logInBtn.setEnabled(true);
                        assert response.errorBody() != null;
                        Toast.makeText(getApplicationContext(),"No response from quiz-servers.",Toast.LENGTH_SHORT).show(); // this will tell you why your api doesnt work most of time
                    }

                }

                @Override
                public void onFailure(@NonNull Call<Auth> call, @NonNull Throwable t) {
                    logInBtn.setEnabled(true);
                    Log.v("Login", t.toString());
                    Toast.makeText(getApplicationContext(),"Can't connect.",Toast.LENGTH_SHORT).show(); // ALL NETWORK ERROR HERE
                }


            });


        }else{
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }

    }


}