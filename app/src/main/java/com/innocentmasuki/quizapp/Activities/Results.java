package com.innocentmasuki.quizapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.innocentmasuki.quizapp.Activities.Home;
import com.innocentmasuki.quizapp.R;

public class Results extends AppCompatActivity {

    TextView score, position, percentageView;
    Button doneQuiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        score = findViewById(R.id.resultsScore);
        position = findViewById(R.id.resultsPosition);
        percentageView = findViewById(R.id.percentage);
        doneQuiz = findViewById(R.id.doneQuiz);

        Intent intent = getIntent();
        int marks = intent.getExtras().getInt("marks");
        int total = intent.getExtras().getInt("total");
        int percent = (int)(((float)marks /(float)total ) * 100);

        score.setText(marks + "/" + total);

        if (percent < 30){
            position.setText("Congratulations 🎉" );

        }else if(percent <=70){
            position.setText("Congratulations 🎉 | Good" );
        }else{
            position.setText("Congratulations 🎉 | Excellent" );

        }


        ValueAnimator animator = ValueAnimator.ofInt(0, percent);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                percentageView.setText(animation.getAnimatedValue().toString() +"%");
            }
        });
        animator.start();

        doneQuiz.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
            finish();
        });
    }
}