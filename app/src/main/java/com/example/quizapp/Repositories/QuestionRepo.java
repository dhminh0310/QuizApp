package com.example.quizapp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.quizapp.Database.QuizAppDatabase;
import com.example.quizapp.Model.Question;
import com.example.quizapp.Database.QuestionDao;

import java.util.List;

public class QuestionRepo {

    private static QuestionDao questionDao;
    private static LiveData<List<Question>> listQuestions;
    public static QuestionRepo instance;

    public static QuestionRepo getInstance(Application application){
        if(instance == null){
            instance = new QuestionRepo();
        }
        questionDao = QuizAppDatabase.getInstance(application).questionDao();
        listQuestions = questionDao.getAllQuestions();
        return instance;
    }

    public void insertQuestion(Question question) {
        questionDao.insertQuestion(question);
    }

    public void updateQuestion(Question question) {
        questionDao.updateQuestion(question);
    }

    public void deleteQuestion(Question question) {
        questionDao.deleteQuestion(question);
    }

    public void deleteAllQuestions() {
        questionDao.deleteAllQuestions();
    }

    public LiveData<List<Question>> getAllQuestions() {
        return listQuestions;
    }

    public List<Question> getListRandomQuestion(){
        return questionDao.getListRandomQuestion();
    }
}
