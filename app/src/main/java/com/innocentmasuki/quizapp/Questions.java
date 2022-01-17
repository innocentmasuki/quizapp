package com.innocentmasuki.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Questions extends AppCompatActivity {

    Boolean answered = false;

    TextView tv;
    Button nextQuestion, quitbutton, checkAnswer;
    RadioGroup radio_g;
    RadioButton rb1,rb2,rb3,rb4;

    String[] questions = {
            "Which method can be defined only once in a program?",
            "Which of these is not a bitwise operator?",
            "Which keyword is used by method to refer to the object that invoked it?",
            "Which of these keywords is used to define interfaces in Java?",
            "Which of these access specifiers can be used for an interface?",
            "Which of the following is correct way of importing an entire package ‘pkg’?",
            "What is the return type of Constructors?",
            "Which of the following package stores all the standard java classes?",
            "Which of these method of class String is used to compare two String objects for their equality?",
            "An expression involving byte, int, & literal numbers is promoted to which of these?"
    };
    String[] answers = {"main method","<=","this","interface","public","import pkg.*","None of the mentioned","java","equals()","int"};
    String[] opt = {
            "finalize method","main method","static method","private method",
            "&","&=","|=","<=",
            "import","this","catch","abstract",
            "Interface","interface","intf","Intf",
            "public","protected","private","All of the mentioned",
            "Import pkg.","import pkg.*","Import pkg.*","import pkg.",
            "int","float","void","None of the mentioned",
            "lang","java","util","java.packages",
            "equals()","Equals()","isequal()","Isequal()",
            "int","long","byte","float"
    };

    int flag=0;
    public static int marks=0,correct=0,wrong=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        TextView textView=(TextView)findViewById(R.id.DispName);

        nextQuestion=(Button)findViewById(R.id.nextQuestion);
        checkAnswer=(Button)findViewById(R.id.check_answer);
        quitbutton=(Button)findViewById(R.id.buttonquit);
        tv=(TextView) findViewById(R.id.tvque);

        radio_g=(RadioGroup)findViewById(R.id.answersgrp);
        rb1=(RadioButton)findViewById(R.id.radioButton);
        rb2=(RadioButton)findViewById(R.id.radioButton2);
        rb3=(RadioButton)findViewById(R.id.radioButton3);
        rb4=(RadioButton)findViewById(R.id.radioButton4);
        tv.setText(questions[flag]);
        rb1.setText(opt[0]);
        rb2.setText(opt[1]);
        rb3.setText(opt[2]);
        rb4.setText(opt[3]);


//         circularViewWithTimer = findViewById(R.id.circular_view_with_timer);
//        CircularView.OptionsBuilder builderWithTimer =
//                new CircularView.OptionsBuilder()
//                        .shouldDisplayText(true)
//                        .setCounterInSeconds(50)
//                        .setCircularViewCallback(new CircularViewCallback() {
//                            @Override
//                            public void onTimerFinish() {
//
//                                // Will be called if times up of countdown timer
//                                Toast.makeText(MainActivity.this, "CircularCallback: Timer Finished ", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onTimerCancelled() {
//
//                                // Will be called if stopTimer is called
//                                Toast.makeText(MainActivity.this, "CircularCallback: Timer Cancelled ", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//        circularViewWithTimer.setOptions(builderWithTimer);

        checkAnswer.setOnClickListener(v -> {



            if(radio_g.getCheckedRadioButtonId()==-1)
            {
                Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                return;
            }else{
                answered = true;
                checkAnswer.setEnabled(false);
                RadioButton userAnswer = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = userAnswer.getText().toString();
                if(ansText.equals(answers[flag])) {
                    correct++;
                    userAnswer.setBackgroundResource(R.drawable.correct_background);
                    userAnswer.setTextColor(Color.parseColor("#FFFFFFFF"));
                }

                else {

                    userAnswer.setBackgroundResource(R.drawable.wrong_background);
                    radio_g.getChildAt(flag+1).setBackgroundResource(R.drawable.correct_background);
                    userAnswer.setTextColor(Color.parseColor("#FFFFFFFF"));
                    ((RadioButton) radio_g.getChildAt(flag+1)).setTextColor(Color.parseColor("#FFFFFFFF"));
                    wrong++;
                }
                rb1.setEnabled(false);
                rb2.setEnabled(false);
                rb3.setEnabled(false);
                rb4.setEnabled(false);
            }


        });

        nextQuestion.setOnClickListener(v -> {
            if(answered){
                answered = false;
                checkAnswer.setEnabled(true);

                rb1.setTextColor(Color.parseColor("#000000"));
                rb2.setTextColor(Color.parseColor("#000000"));
                rb3.setTextColor(Color.parseColor("#000000"));
                rb4.setTextColor(Color.parseColor("#000000"));

                rb1.setBackgroundResource(0);
                rb2.setBackgroundResource(0);
                rb3.setBackgroundResource(0);
                rb4.setBackgroundResource(0);

                rb1.setEnabled(true);
                rb2.setEnabled(true);
                rb3.setEnabled(true);
                rb4.setEnabled(true);


                flag++;
                if(flag<questions.length)
                {
                    tv.setText(questions[flag]);
                    rb1.setText(opt[flag*4]);
                    rb2.setText(opt[flag*4 +1]);
                    rb3.setText(opt[flag*4 +2]);
                    rb4.setText(opt[flag*4 +3]);
                }
                else
                {
                    marks=correct;
//                Intent in = new Intent(getApplicationContext(),Results.class);
//                startActivity(in);
                    Toast.makeText(getApplicationContext(), "All done go to results", Toast.LENGTH_SHORT).show();
                }
                radio_g.clearCheck();
            }

        });


    }
}