package com.innocentmasuki.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Results extends AppCompatActivity {

    TextView score, position, percentageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        score = findViewById(R.id.resultsScore);
        position = findViewById(R.id.resultsPosition);
        percentageView = findViewById(R.id.percentage);

        Intent intent = getIntent();
        int marks = intent.getExtras().getInt("marks");
        int total = intent.getExtras().getInt("total");
        int percent = ((marks * 100 )/ total);

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
    }
}