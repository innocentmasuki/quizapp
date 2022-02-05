package com.innocentmasuki.quizapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.innocentmasuki.quizapp.R;

import java.util.Locale;

public class Questions extends AppCompatActivity {

    Boolean answered = false, timeUp = false;
    Animation shake;
    View contents;

    private boolean mTimerRunning;

    TextView tv, userScores, user_marks;
    Button nextQuestion, quitButton, submitAnswerBtn, startQuiz,buttonQuit;
    RadioGroup radio_g;
    RadioButton rb1,rb2,rb3,rb4;

    ProgressBar mProgressBar,questionProgressBar;
    CountDownTimer mCountDownTimer;

    int seconds;
    public static int totalSeconds = 0;


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

    int[] duration = {60,120,70,12,30,60,120,70,12,30};

    int flag=0;
    public int marks=0,correct=0,wrong=0;
    int counter = 0;

    private long mTimeLeftInMillis = duration[flag] * 1000;
    Vibrator v ;
    MediaPlayer correctSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        correctSound = MediaPlayer.create(getApplicationContext(), R.raw.correct);

        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
        questionProgressBar=(ProgressBar)findViewById(R.id.questionsProgress);
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        contents = findViewById(R.id.contents);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        nextQuestion=(Button)findViewById(R.id.nextQuestion);
        submitAnswerBtn =(Button)findViewById(R.id.check_answer);
        quitButton =(Button)findViewById(R.id.buttonquit);
        startQuiz =(Button)findViewById(R.id.startQuiz);
        buttonQuit =(Button)findViewById(R.id.buttonQuit);
        tv=(TextView) findViewById(R.id.tvque);
        userScores=(TextView) findViewById(R.id.scores);
        user_marks=(TextView) findViewById(R.id.user_marks);

        radio_g=(RadioGroup)findViewById(R.id.answersgrp);
        rb1=(RadioButton)findViewById(R.id.radioButton);
        rb2=(RadioButton)findViewById(R.id.radioButton2);
        rb3=(RadioButton)findViewById(R.id.radioButton3);
        rb4=(RadioButton)findViewById(R.id.radioButton4);






        startQuiz();


        submitAnswerBtn.setOnClickListener(v -> submitAnswer());

        nextQuestion.setOnClickListener(v -> goToNextQuestion());

        buttonQuit.setOnClickListener(v -> {
            Intent in = new Intent(getApplicationContext(), Results.class);
            in.putExtra("marks", marks);
            in.putExtra("total", totalSeconds);
            startActivity(in);
            finish();
        });


    }

    private void startQuiz() {
        marks=0;
        totalSeconds = 0;
        for (int j : duration) {
            totalSeconds = totalSeconds + j;
        }



        tv.setText(questions[flag]);
        rb1.setText(opt[0]);
        rb2.setText(opt[1]);
        rb3.setText(opt[2]);
        rb4.setText(opt[3]);





        questionProgressBar.setMax(questions.length);
        questionProgressBar.setProgress(flag + 1);


        startTimer();
        updateCountDownText();
    }


    private void submitAnswer() {

        if(radio_g.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
            return;

        }else{

            answered = true;
            submitAnswerBtn.setEnabled(false);
            RadioButton userAnswer = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
            String ansText = userAnswer.getText().toString();
            if(ansText.equals(answers[flag])) {
                correct++;
                 correctSound.start();
                pauseTimer();
                marks = marks + seconds;
                user_marks.setText(Integer.toString(marks));
                userAnswer.setBackgroundResource(R.drawable.correct_background);
                userAnswer.setTextColor(Color.parseColor("#FFFFFFFF"));
            }

            else {
                pauseTimer();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(100);
                }
                userAnswer.setBackgroundResource(R.drawable.wrong_background);
                for (int i=0;i<4;i++) {
                    RadioButton o = (RadioButton) radio_g.getChildAt(i);
                    if (o.getText().toString().equals(answers[flag])) {
                        o.setBackgroundResource(R.drawable.correct_background);
                        o.setTextColor(Color.parseColor("#FFFFFFFF"));
                        o.startAnimation(shake);
                    }
                }
//                    radio_g.getChildAt(flag+1).setBackgroundResource(R.drawable.correct_background);
                userAnswer.setTextColor(Color.parseColor("#FFFFFFFF"));
                wrong++;
            }
            rb1.setEnabled(false);
            rb2.setEnabled(false);
            rb3.setEnabled(false);
            rb4.setEnabled(false);
        }

    }

    private void goToNextQuestion() {
        if(answered || timeUp){
            answered = false;
            timeUp = false;
            submitAnswerBtn.setEnabled(true);


            rb1.setTextColor(Color.parseColor("#787878"));
            rb2.setTextColor(Color.parseColor("#787878"));
            rb3.setTextColor(Color.parseColor("#787878"));
            rb4.setTextColor(Color.parseColor("#787878"));

            rb1.setBackgroundResource(0);
            rb2.setBackgroundResource(0);
            rb3.setBackgroundResource(0);
            rb4.setBackgroundResource(0);

            rb1.setEnabled(true);
            rb2.setEnabled(true);
            rb3.setEnabled(true);
            rb4.setEnabled(true);


            flag++;
            if(flag<questions.length){

                if(flag == (questions.length - 1)){
                    nextQuestion.setText("FINISH");
                }
                tv.setText(questions[flag]);
                rb1.setText(opt[flag*4]);
                rb2.setText(opt[flag*4 +1]);
                rb3.setText(opt[flag*4 +2]);
                rb4.setText(opt[flag*4 +3]);


                questionProgressBar.setProgress(flag + 1);
                mProgressBar.setMax(duration[flag]);
                resetTimer();
                startTimer();

            }
            else
            {
                    Intent in = new Intent(getApplicationContext(),Results.class);
                    in.putExtra("marks", marks);
                    in.putExtra("total", totalSeconds);
                    startActivity(in);
                    finish();
            }
            radio_g.clearCheck();
        }

    }


    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;


                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                submitAnswerBtn.setEnabled(false);
                timeUp = true;

            }
        }.start();

        mTimerRunning = true;

    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;

    }

    private void resetTimer() {
        mTimeLeftInMillis = duration[flag] * 1000;
        updateCountDownText();
    }


    private void updateCountDownText() {
        mProgressBar.setMax(duration[flag]);

//        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
         seconds = (int) (mTimeLeftInMillis / 1000);
        mProgressBar.setProgress(seconds);
        Log.v("Log_tag", "Tick of Seconds"+ seconds);



        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d", seconds);

        userScores.setText(Integer.toString(seconds));
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft",mTimeLeftInMillis);
        outState.putBoolean("timerRunning",mTimerRunning);
    }

    @Override
    protected  void onRestoreInstanceState(Bundle savedInstances){
        super.onRestoreInstanceState(savedInstances);
        mTimeLeftInMillis = savedInstances.getLong("millisLeft");
        mTimerRunning = savedInstances.getBoolean("timerRunning");
        updateCountDownText();

        if(mTimerRunning){
            startTimer();
        }
    }


}