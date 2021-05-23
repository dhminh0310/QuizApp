package com.example.quizapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.quizapp.Model.Question;
import com.example.quizapp.Repositories.QuestionRepo;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class TakeQuizActivityViewModel extends AndroidViewModel {
    private QuestionRepo questionRepo;
    private List<Question> listRandomQuestion;
    private MutableLiveData<Question> question;
    private Random rand = new Random();
    private int totalQues, currentQuest = 0;
    public TakeQuizActivityViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.questionRepo = QuestionRepo.getInstance(application);
        this.listRandomQuestion = questionRepo.getListRandomQuestion();
        totalQues = listRandomQuestion.size();
        question = new MutableLiveData<>();
        question.setValue(listRandomQuestion.get(0));
    }

    public MutableLiveData<Question> onChangeQuestion() {
        this.currentQuest++;
        if (currentQuest >= listRandomQuestion.size()) {
            return null;
        } else {
            question.setValue(listRandomQuestion.get(currentQuest));
            return question;
        }
    }
    public List<Question> getListQuest(){
        return listRandomQuestion;
    }

    public int getTotalQues(){
        return totalQues;
    }

    public MutableLiveData<Question> getQuestion(){
        return question;
    }

    public int getCurrentQuest(){
        return currentQuest;
    }
}
