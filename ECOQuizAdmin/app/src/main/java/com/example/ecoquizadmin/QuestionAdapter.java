package com.example.ecoquizadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private List<QuestionModel> ques_list;

    public QuestionAdapter(List<QuestionModel> ques_list) {
        this.ques_list = ques_list;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cat_item_layout, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder viewHolder, int pos) {
        viewHolder.setData(pos);
    }

    @Override
    public int getItemCount() {
        return ques_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView deleteB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.catName);
            deleteB = itemView.findViewById(R.id.catDelB);

        }

        private void setData(int pos){
            title.setText("              QUESTION " + pos);
        }
    }
}
