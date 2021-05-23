package com.example.quizapp.UI.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Model.Question;
import com.example.quizapp.R;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAllQuestionAdapter extends RecyclerView.Adapter<RecyclerViewAllQuestionAdapter.ViewHolder>{

    List<Question> listQues = new ArrayList<>();
    private OnItemCLickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_line, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtQuestionTitle.setText(listQues.get(position).getTitle());
        holder.txtAnswer1.setText(listQues.get(position).getAnswer1());
        holder.txtAnswer2.setText(listQues.get(position).getAnswer2());
        holder.txtAnswer3.setText(listQues.get(position).getAnswer3());
        holder.txtAnswer4.setText(listQues.get(position).getAnswer4());
        switch (listQues.get(position).getCorrectAnswer()){
            case 1:
                holder.txtAnswer1.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                return;
            case 2:
                holder.txtAnswer2.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                return;
            case 3:
                holder.txtAnswer3.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                return;
            case 4:
                holder.txtAnswer4.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                return;
        }
    }

    @Override
    public int getItemCount() {
        return listQues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtQuestionTitle, txtAnswer1, txtAnswer2, txtAnswer3, txtAnswer4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuestionTitle = itemView.findViewById(R.id.textViewQuestionTitle);
            txtAnswer1 = itemView.findViewById(R.id.textViewAnswer1);
            txtAnswer2 = itemView.findViewById(R.id.textViewAnswer2);
            txtAnswer3 = itemView.findViewById(R.id.textViewAnswer3);
            txtAnswer4 = itemView.findViewById(R.id.textViewAnswer4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.OnClick(listQues.get(position));
                    }
                }
            });
        }
    }

    public void setQuestions(List<Question> listQues){
        this.listQues = listQues;
        notifyDataSetChanged();
    }

    public interface OnItemCLickListener{
        void OnClick(Question question);
    }

    public void setOnItemClickListener(OnItemCLickListener listener){
        this.listener = listener;
    }

    public Question getQuestionAt (int id){
        return listQues.get(id);
    }
}

