package com.example.quizapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.quizapp.Model.Question;
import com.example.quizapp.R;
import com.example.quizapp.ViewModel.TakeQuizActivityViewModel;

public class TakeQuizActivity extends AppCompatActivity {
    private TextView txtScore, txtCurrentQues, txtTime, txtQuesTitle, txtAnswer1, txtAnswer2, txtAnswer3, txtAnswer4;
    private int currentQues, isCorrect = 0, totalQuest, totalTime, score = 0;
    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);
        InitUI();
        TakeQuizActivityViewModel viewModel = new ViewModelProvider(this).get(TakeQuizActivityViewModel.class);
        totalQuest = viewModel.getTotalQues();
        timer = getCountDownTimer(viewModel);
        viewModel.getQuestion().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                txtQuesTitle.setText(question.getTitle());
                txtAnswer1.setText(question.getAnswer1());
                txtAnswer2.setText(question.getAnswer2());
                txtAnswer3.setText(question.getAnswer3());
                txtAnswer4.setText(question.getAnswer4());

                currentQues = viewModel.getCurrentQuest() + 1;
                isCorrect = question.getCorrectAnswer();
                txtCurrentQues.setText("Question: " + currentQues + "/" + totalQuest);
                txtScore.setText("Your score:  " + score);
                timer.cancel();
                timer.start();

            }
        });

        txtAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCorrect == 1){
                    score++;
                }
                setOnChangeQuestion(viewModel);
                totalTime = 15;
            }
        });

        txtAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCorrect == 2){
                    score++;
                }
                setOnChangeQuestion(viewModel);
                totalTime = 15;
            }
        });

        txtAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCorrect == 3){
                    score++;
                }
                setOnChangeQuestion(viewModel);
                totalTime = 15;
            }
        });

        txtAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCorrect == 4){
                    score++;
                }
                setOnChangeQuestion(viewModel);
                totalTime = 15;
            }
        });

    }

    private void InitUI() {
        txtScore = findViewById(R.id.textViewScore);
        txtCurrentQues = findViewById(R.id.textViewCurrentQuestion);
        txtTime = findViewById(R.id.textViewTime);
        txtQuesTitle = findViewById(R.id.textViewTakeQuizQuestionTitle);
        txtAnswer1 = findViewById(R.id.textViewTakeQuizAnswer1);
        txtAnswer2 = findViewById(R.id.textViewTakeQuizAnswer2);
        txtAnswer3 = findViewById(R.id.textViewTakeQuizAnswer3);
        txtAnswer4 = findViewById(R.id.textViewTakeQuizAnswer4);
    }

    private CountDownTimer getCountDownTimer(TakeQuizActivityViewModel viewModel){
        totalTime = 15;
        CountDownTimer timer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long l) {
                txtTime.setText( "" + totalTime);
                totalTime -= 1;
            }

            @Override
            public void onFinish() {
                setOnChangeQuestion(viewModel);
                totalTime = 15;
            }
        };
        return timer;
    }

    private void setOnChangeQuestion(TakeQuizActivityViewModel viewModel){
        viewModel.onChangeQuestion();

        if(viewModel.getCurrentQuest() >= totalQuest) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(TakeQuizActivity.this);
            dialog.setTitle("Quiz App");
            dialog.setIcon(R.drawable.success);
            dialog.setMessage("Your score: " + score + "/" + totalQuest);
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialog.show();
        }
    }
}