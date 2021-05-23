package com.example.quizapp.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.quizapp.Model.Question;


@Database(entities = {Question.class}, version = 1)
public abstract class QuizAppDatabase extends RoomDatabase {

    private static QuizAppDatabase instance;
    public abstract QuestionDao questionDao();

    private static final String DATABASE_NAME = "QuizAppDatabase";

    public static QuizAppDatabase getInstance(Application application){
        if(instance == null){
            instance = Room.databaseBuilder(application, QuizAppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitDataForDatabaseOnCreate(instance).execute();
        }
    };

    private static class InitDataForDatabaseOnCreate extends AsyncTask<Void, Void, Void> {
        QuestionDao questionDao;
        public InitDataForDatabaseOnCreate(QuizAppDatabase database){
            this.questionDao = database.questionDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            questionDao.insertQuestion(new Question("Cây gì càng đốt càng dài ?", "Cây nhang", "Cây tre", "Cây đèn cầy", "Cây chuối", 2));
            questionDao.insertQuestion(new Question("Uống nước nhớ kẻ trồng cây, tại sao ?", "Uống nhiều nước thì tốt cho sức khỏe", "Uống nước đá", "Uống nước mía", "Vô lý quá", 3));
            questionDao.insertQuestion(new Question("Cái gì giống nữa cái bánh tráng nhất ?", "Cái bánh tráng", "Cái tô", "Nữa cái bánh tráng còn lại", "Đổi câu hỏi", 3));
            questionDao.insertQuestion(new Question("Vì sao cô gái gọi chàng trai là \"Cung thủ\" ?", "Vì lúc nào anh cũng vô tâm", "Vì chàng trai bắn cung giỏi", "Thích kêu thì kêu", "Vì chàng trai thích được gọi là cung thủ", 1));
            questionDao.insertQuestion(new Question("1 con vịt có 2 cái cánh, vậy ba con vịt có mấy cái cánh ?", "6 cái cánh", "2 cái cánh", "4 cái cánh", "8 cái cánh", 2));
            questionDao.insertQuestion(new Question("Ăn cái gì làm da đen ?", "Ăn năn hối cải", "Ăn chín uống sôi", "Ăn nhẹ nói khẽ", "Ăn nắng", 4));
            questionDao.insertQuestion(new Question("Ăn gì chụp hình mới đẹp ?", "Ăn uống \"heo thì\"", "Ăn ảnh", "Ăn nhẹ nói khẽ", "Ăn mặc", 2));
            questionDao.insertQuestion(new Question("Tỉnh nào vừa có ruộng vừa có rừng ?", "Lâm Đồng", "Tây Ninh", "Sóc Trăng", "Long An", 1));
            questionDao.insertQuestion(new Question("Cái gì không thể ăn vào bữa tối ?", "Bánh ngọt", "Cà phê", "Cái gì cũng ăn được", "Bữa trưa và bữa sáng", 4));
            questionDao.insertQuestion(new Question("Hoa nào biết nói ?", "Hoa hồng có gai", "Hoa lá", "Hoa hậu", "Hoa không biết nói", 3));
            questionDao.insertQuestion(new Question("Lúc nào là dễ ngủ nhất ?", "Buổi tối", "Lúc gần sáng", "Lúc ăn no", "Lúc buồn ngủ", 4));
            return null;
        }
    }
}
