package com.example.quizapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quizapp.Model.Question;

import java.util.List;

@Dao
public interface QuestionDao {
    @Insert
    void insertQuestion(Question question);

    @Update
    void updateQuestion(Question question);

    @Delete
    void deleteQuestion(Question question);

    @Query("DELETE FROM question_table")
    void deleteAllQuestions();

    @Query("SELECT * FROM question_table")
    LiveData<List<Question>> getAllQuestions();

    @Query("SELECT DISTINCT * FROM question_table ORDER BY RANDOM() LIMIT 10")
    List<Question> getListRandomQuestion();
}