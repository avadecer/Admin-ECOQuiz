package com.example.ecoquizadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private RecyclerView quesView;
    private Button addQB;
    public static List<QuestionModel> quesList = new ArrayList<>();
    private QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        quesView = findViewById(R.id.quest_recycler);
        addQB = findViewById(R.id.addQB);

        addQB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        quesView.setLayoutManager(layoutManager);

        loadQuestions();
    }

    private void loadQuestions(){
        quesList.clear();

        quesList.add(new QuestionModel("1","Q1", "A", "B",2));
        quesList.add(new QuestionModel("1","Q2", "A", "B",2));
        quesList.add(new QuestionModel("1","Q3", "A", "B",2));
        quesList.add(new QuestionModel("1","Q4", "A", "B",2));
        quesList.add(new QuestionModel("1","Q5", "A", "B",2));
        quesList.add(new QuestionModel("1","Q6", "A", "B",2));
        quesList.add(new QuestionModel("1","Q7", "A", "B",2));
        quesList.add(new QuestionModel("1","Q8", "A", "B",2));
        quesList.add(new QuestionModel("1","Q9", "A", "B",2));
        quesList.add(new QuestionModel("1","Q10", "A", "B",2));

        adapter = new QuestionAdapter(quesList);
        quesView.setAdapter(adapter);
    }
}