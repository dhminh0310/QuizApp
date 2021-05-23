package com.example.quizapp.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Model.Question;
import com.example.quizapp.R;

public class AddEditQuestionActivity extends AppCompatActivity {
    private EditText edtQuestion, edtAnswer1, edtAnswer2, edtAnswer3, edtAnswer4;
    private NumberPicker numpickerCorrectAnswer;
    private TextView txtAddEditQuestionTitle;
    private int idEditQuestion = -1;
    public static final String EXTRA_QUESTION = "Question";
    public static final String EXTRA_ANSWER1 = "Answer1";
    public static final String EXTRA_ANSWER2 = "Answer2";
    public static final String EXTRA_ANSWER3 = "Answer3";
    public static final String EXTRA_ANSWER4 = "Answer4";
    public static final String EXTRA_CORRECTANSWER = "CorrectAnswer";
    public static final String EXTRA_ID = "QuestionId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        numpickerCorrectAnswer = findViewById(R.id.numberPickerCorrectAnswer);
        numpickerCorrectAnswer.setMinValue(1);
        numpickerCorrectAnswer.setMaxValue(4);
        numpickerCorrectAnswer.setOrientation(LinearLayout.HORIZONTAL);
        edtQuestion = findViewById(R.id.editTextQuestion);
        edtAnswer2 = findViewById(R.id.editTextanswer2);
        edtAnswer3 = findViewById(R.id.editTextanswer3);
        edtAnswer4 = findViewById(R.id.editTextanswer4);
        edtAnswer1 = findViewById(R.id.editTextanswer1);
        txtAddEditQuestionTitle = findViewById(R.id.textViewAddEditQues);

        Intent intent = getIntent();
        if(intent.hasExtra(ShowAllQuestion.EXTRA_QUESTION)){
            txtAddEditQuestionTitle.setText("Edit Question");
            Question question = (Question) intent.getSerializableExtra(ShowAllQuestion.EXTRA_QUESTION);
            EditQuestion(question);
        }
    }

    private void EditQuestion(Question question) {
        edtQuestion.setText(question.getTitle());
        edtAnswer1.setText(question.getAnswer1());
        edtAnswer2.setText(question.getAnswer2());
        edtAnswer3.setText(question.getAnswer3());
        edtAnswer4.setText(question.getAnswer4());
        numpickerCorrectAnswer.setValue(question.getCorrectAnswer());
        idEditQuestion = question.getId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_question_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSave:
                SaveQuestion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void SaveQuestion() {
        String questionTitle = edtQuestion.getText().toString().trim();
        String answer1 = edtAnswer1.getText().toString().trim();
        String answer2 = edtAnswer2.getText().toString().trim();
        String answer3 = edtAnswer3.getText().toString().trim();
        String answer4 = edtAnswer4.getText().toString().trim();
        int correctAnswer = numpickerCorrectAnswer.getValue();
        if(questionTitle.isEmpty() || answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty()
        || answer4.isEmpty()){
            Toast.makeText(this, "Please insert Question and answer", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_QUESTION, questionTitle);
        intent.putExtra(EXTRA_ANSWER1, answer1);
        intent.putExtra(EXTRA_ANSWER2, answer2);
        intent.putExtra(EXTRA_ANSWER3, answer3);
        intent.putExtra(EXTRA_ANSWER4, answer4);
        intent.putExtra(EXTRA_CORRECTANSWER, correctAnswer);

        intent.putExtra(EXTRA_ID, idEditQuestion);

        setResult(RESULT_OK, intent);
        finish();
    }
}