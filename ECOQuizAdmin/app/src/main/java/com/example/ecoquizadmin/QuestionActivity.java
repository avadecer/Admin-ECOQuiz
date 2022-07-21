package com.example.ecoquizadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity {

    private RecyclerView quesView;
    private Button addQB;
    public static List<QuestionModel> quesList = new ArrayList<>();
    private QuestionAdapter adapter;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        quesView = findViewById(R.id.quest_recycler);
        addQB = findViewById(R.id.addQB);

        addQB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionActivity.this, QuestionDetailsActivity.class);
                intent.putExtra("ACTION", "ADD");
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        quesView.setLayoutManager(layoutManager);

        firestore = FirebaseFirestore.getInstance();

        loadQuestions();
    }

    private void loadQuestions() {
        quesList.clear();

        //fetching questions in the firebase

        firestore.collection("1").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();

                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            docList.put(doc.getId(), doc);
                        }

                        QueryDocumentSnapshot quesListDoc = docList.get("QUESTIONS_LIST");

                        String count = quesListDoc.getString("COUNT");

                        for (int i = 0; i < Integer.valueOf(count); i++) {
                            String quesID = quesListDoc.getString("Q" + String.valueOf(i + 1) + "_ID");

                            QueryDocumentSnapshot quesDoc = docList.get(quesID);

                            quesList.add(new QuestionModel(
                                    quesID,
                                    quesDoc.getString("QUESTION"),
                                    quesDoc.getString("A"),
                                    quesDoc.getString("B"),
                                    Integer.valueOf(quesDoc.getString("ANSWER"))
                            ));
                        }

                        adapter = new QuestionAdapter(quesList);
                        quesView.setAdapter(adapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QuestionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
