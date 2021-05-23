package com.example.quizapp.UI.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.quizapp.UI.Adapter.RecyclerViewAllQuestionAdapter;
import com.example.quizapp.Model.Question;
import com.example.quizapp.R;
import com.example.quizapp.ViewModel.AddQuestionActivityViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShowAllQuestion extends AppCompatActivity {

    private RecyclerView rcvQuestions;
    private RecyclerViewAllQuestionAdapter adapter;
    private ImageButton btnAddQuestion;
    private AddQuestionActivityViewModel viewModel;
    public static final int ADD_QUESTION_REQUEST = 1;
    public static final int EDIT_QUESTION_REQUEST = 2;
    public static final String EXTRA_QUESTION = "QuestionObject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_question);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        btnAddQuestion = findViewById(R.id.buttonAddNewQues);
        rcvQuestions = findViewById(R.id.recyclerViewQuestions);

        adapter = new RecyclerViewAllQuestionAdapter();
        rcvQuestions.setLayoutManager(new LinearLayoutManager(this));
        rcvQuestions.setHasFixedSize(true);
        rcvQuestions.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(AddQuestionActivityViewModel.class);
        viewModel.getListQuestion().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                adapter.setQuestions(questions);
            }
        });

        adapter.setOnItemClickListener(new RecyclerViewAllQuestionAdapter.OnItemCLickListener() {
            @Override
            public void OnClick(Question question) {
                Intent intent = new Intent(ShowAllQuestion.this, AddEditQuestionActivity.class);
                intent.putExtra(EXTRA_QUESTION, question);
                startActivityForResult(intent, EDIT_QUESTION_REQUEST);
            }
        });

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAllQuestion.this, AddEditQuestionActivity.class);
                startActivityForResult(intent, ADD_QUESTION_REQUEST);
            }
        });

        //Swipe to delete Question
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ShowAllQuestion.this);
                dialog.setTitle("Delete question ?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteQuestion(adapter.getQuestionAt(viewHolder.getAdapterPosition()));
                        Toast.makeText(ShowAllQuestion.this, "Questions deleted !", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        return;
                    }
                });
                dialog.show();

            }
        }).attachToRecyclerView(rcvQuestions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.all_question_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuDeleteAllQuestion:
                deleteAllQuestion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAllQuestion() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete all question ?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                viewModel.deleteAllQuestions();
                Toast.makeText(ShowAllQuestion.this, "All questions deleted !", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_QUESTION_REQUEST && resultCode == RESULT_OK){
            Question question = new Question(data.getStringExtra(AddEditQuestionActivity.EXTRA_QUESTION), data.getStringExtra(AddEditQuestionActivity.EXTRA_ANSWER1),
                    data.getStringExtra(AddEditQuestionActivity.EXTRA_ANSWER2), data.getStringExtra(AddEditQuestionActivity.EXTRA_ANSWER3),
                    data.getStringExtra(AddEditQuestionActivity.EXTRA_ANSWER4), data.getIntExtra(AddEditQuestionActivity.EXTRA_CORRECTANSWER, 1));
            viewModel.insertQuestion(question);
            Toast.makeText(this, "Question saved", Toast.LENGTH_SHORT).show();
        }else if(requestCode == EDIT_QUESTION_REQUEST && resultCode == RESULT_OK){
            Question question = new Question(data.getStringExtra(AddEditQuestionActivity.EXTRA_QUESTION), data.getStringExtra(AddEditQuestionActivity.EXTRA_ANSWER1),
                    data.getStringExtra(AddEditQuestionActivity.EXTRA_ANSWER2), data.getStringExtra(AddEditQuestionActivity.EXTRA_ANSWER3),
                    data.getStringExtra(AddEditQuestionActivity.EXTRA_ANSWER4), data.getIntExtra(AddEditQuestionActivity.EXTRA_CORRECTANSWER, 1));

            int idEditQues = data.getIntExtra(AddEditQuestionActivity.EXTRA_ID, -1);

            if(idEditQues == -1){
                Toast.makeText(this, "Can not edit Question", Toast.LENGTH_SHORT).show();
                return;
            }
            question.setId(idEditQues);
            viewModel.updateQuestion(question);
            Toast.makeText(this, "Question edited", Toast.LENGTH_SHORT).show();
        }
    }
}