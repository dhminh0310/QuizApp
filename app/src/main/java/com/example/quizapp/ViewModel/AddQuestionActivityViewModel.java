package com.example.quizapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quizapp.Model.Question;
import com.example.quizapp.Repositories.QuestionRepo;

import java.util.List;

public class AddQuestionActivityViewModel extends AndroidViewModel {
    private QuestionRepo questionRepo;
    private LiveData<List<Question>> listQuestionLiveData;

    public AddQuestionActivityViewModel(@NonNull Application application) {
        super(application);
        questionRepo = QuestionRepo.getInstance(application);
        this.listQuestionLiveData = questionRepo.getAllQuestions();
    }

    public LiveData<List<Question>> getListQuestion(){
        return listQuestionLiveData;
    }

    public void insertQuestion(Question question){
        questionRepo.insertQuestion(question);
    }

    public void updateQuestion(Question question){
        questionRepo.updateQuestion(question);
    }

    public void deleteAllQuestions(){
        questionRepo.deleteAllQuestions();
    }

    public void deleteQuestion(Question question){
        questionRepo.deleteQuestion(question);
    }
}
