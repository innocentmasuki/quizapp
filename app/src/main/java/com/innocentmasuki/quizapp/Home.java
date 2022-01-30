package com.innocentmasuki.quizapp;

import static com.innocentmasuki.quizapp.SignUpActivity.Email;
import static com.innocentmasuki.quizapp.SignUpActivity.MyPREFERENCES;
import static com.innocentmasuki.quizapp.SignUpActivity.Password;
import static com.innocentmasuki.quizapp.SignUpActivity.Status;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    Button startQuiz;
    TextView logOut;
    EditText quizCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        startQuiz = findViewById(R.id.startQuiz);
        logOut = findViewById(R.id.logOut);
        quizCode = findViewById(R.id.quizCode);

        startQuiz.setOnClickListener(v -> {
            if(!quizCode.getText().toString().trim().isEmpty()){
                Intent i = new Intent(getApplicationContext(), Questions.class);
                i.putExtra("QUIZCODE", quizCode.getText().toString().trim());

                ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 7.0f, 1f, 7.0f, startQuiz.getWidth() / 2.0f, startQuiz.getHeight() / 2.0f);
                scaleAnimation.setDuration(700);
                startQuiz.setText("");
                startQuiz.startAnimation(scaleAnimation);

                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationEnd(Animation animation) {


                    }

                    public void onAnimationRepeat(Animation animation) {

                    }

                    public void onAnimationStart(Animation animation) {
                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            startActivity(i);
                            finish();
                        }, 600);
                    }
                });
            }else {
                Toast.makeText(getApplicationContext(), "Please provide quiz CODE", Toast.LENGTH_SHORT).show();
            }

            
        });

        logOut.setOnClickListener(v -> {
            SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString(Status, "");
            editor.putString(Email, "");
            editor.putString(Password, "");
            editor.apply();

            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        });

    }
}