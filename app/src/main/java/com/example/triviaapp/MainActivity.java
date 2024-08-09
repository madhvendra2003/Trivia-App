package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.triviaapp.Model.Question;
import com.example.triviaapp.controller.AppController;
import com.example.triviaapp.data.AnswerListAsyncResponse;
import com.example.triviaapp.data.Repository;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    TextView question  , textView_cnt,textView_score , textView_highScore;
    Button  button_true , button_false , button_next ;
    int score = 0;
    CardView cardView;
    int currIndex =0;
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_score = findViewById(R.id.textView_score);
        textView_highScore = findViewById(R.id.textView_highScr);
        textView_cnt = findViewById(R.id.textView_cnt);
        question = findViewById(R.id.Textview_Question);
        button_true = findViewById(R.id.button_true);
        button_false = findViewById(R.id.button_false);
        button_next = findViewById(R.id.button_next);

        cardView = findViewById(R.id.cardView);


        SharedPreferences sharedPreferences = getSharedPreferences("SCORE_ID",MODE_PRIVATE);
        int temp = sharedPreferences.getInt("Score",0);

       int hs  = sharedPreferences.getInt("highScore",0);


        if (temp>hs)
        {
            textView_highScore.setText(String.valueOf(temp));
        }
        else
        {
            textView_highScore.setText(String.valueOf(hs));
        }


        List<Question> questionList;
        questionList = new Repository().getQuestion(new AnswerListAsyncResponse() {
            @SuppressLint("SetTextI18n")
            @Override
            public void ProcessFinished(ArrayList<Question> questionArrayList) {

                question.setText(questionArrayList.get(currIndex%(questionArrayList.size())).getAns());
                textView_cnt.setText("Question : " + (currIndex+1) + "/" + questionArrayList.size());
                button_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currIndex++;
                        question.setText(questionArrayList.get(currIndex%(questionArrayList.size())).getAns());
                        textView_cnt.setText("Question : " + (currIndex+1) + "/" + questionArrayList.size());
                    }
                });


                button_false.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAns(false,questionArrayList.get(currIndex%(questionArrayList.size())).isAnsTrue(),cardView,textView_score);
                        question.setText(questionArrayList.get(currIndex%(questionArrayList.size())).getAns());
                        textView_cnt.setText("Question : " + (currIndex+1) + "/" + questionArrayList.size());

                        currIndex++;
                        question.setText(questionArrayList.get(currIndex%(questionArrayList.size())).getAns());
                        textView_cnt.setText("Question : " + (currIndex+1) + "/" + questionArrayList.size());

                    }

                });

                button_true.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAns(true,questionArrayList.get(currIndex%(questionArrayList.size())).isAnsTrue(),cardView,textView_score);
                        question.setText(questionArrayList.get(currIndex%(questionArrayList.size())).getAns());
                        textView_cnt.setText("Question : " + (currIndex+1) + "/" + questionArrayList.size());

                        currIndex++;
                        question.setText(questionArrayList.get(currIndex%(questionArrayList.size())).getAns());
                        textView_cnt.setText("Question : " + (currIndex+1) + "/" + questionArrayList.size());
                    }

                });

            }
        });


    }

    private void shakeAnimation(View v)
     {
         Animation shake = AnimationUtils.loadAnimation(this , R.anim.shake_animation);
         v.setAnimation(shake);
     }
    private void checkAns(boolean userChoice , boolean correct,View v, TextView txt)
    {
        if (userChoice == correct)
        {
            score++;
             txt.setText("score : "+ score);

            Snackbar.make(cardView,"this is correct ans",Snackbar.LENGTH_SHORT).show();


        }
        else
        {
            if (score != 0)
            {
                score--;

                txt.setText("score : "+ score);
            }
            Snackbar.make(cardView,"this is incorrect ans",Snackbar.LENGTH_SHORT).show();
            shakeAnimation(v);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // in on pause :
        // created 2 variables ie score and high Score
        // if (score>high) txt_high = score
        // else txt_high = highscore
        SharedPreferences sharedPreferences = getSharedPreferences("SCORE_ID",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Score",score);
        editor.apply();
        SharedPreferences.Editor edit1 = sharedPreferences.edit();
        int highscore = Integer.parseInt(textView_highScore.getText().toString());
        edit1.putInt("highScore",highscore);
        edit1.apply();



    }
}