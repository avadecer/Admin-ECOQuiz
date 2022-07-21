package com.example.ecoquizadmin;

import static com.example.ecoquizadmin.QuestionActivity.quesList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class QuestionDetailsActivity extends AppCompatActivity {

    private EditText ques, optionA, optionB, answer;
    private Button addQB;
    private String qStr, aStr, bStr, ansStr;
    private FirebaseFirestore firestore;
    private String action;
    private int qID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);

        ques = findViewById(R.id.question);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        answer = findViewById(R.id.answer);
        addQB = findViewById(R.id.addQB);

        firestore = FirebaseFirestore.getInstance();

        action = getIntent().getStringExtra("ACTION");

        if(action.compareTo("EDIT") == 0)
        {
            qID = getIntent().getIntExtra("Q_ID", 0);
            loadData(qID);
            getSupportActionBar().setTitle("Question " + String.valueOf(qID));
            addQB.setText("UPDATE");
        }
        else
        {
            getSupportActionBar().setTitle("Question " + String.valueOf(quesList.size() + 1));
            addQB.setText("ADD");
        }

        addQB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qStr = ques.getText().toString();
                aStr = optionA.getText().toString();
                bStr = optionB.getText().toString();
                ansStr = answer.getText().toString();

                if(qStr.isEmpty()) {
                    ques.setError("Enter Question");
                    return;
                }

                if(aStr.isEmpty()) {
                    optionA.setError("Enter option A");
                    return;
                }

                if(bStr.isEmpty()) {
                    optionB.setError("Enter option B ");
                    return;
                }

                if(ansStr.isEmpty()) {
                    answer.setError("Enter correct answer");
                    return;
                }
                    if(action.compareTo("EDIT") == 0)
                    {
                        editQuestion();
                    }
                    else
                    {
                        addNewQuestion();
                    }

            }
        });
    }

    private void addNewQuestion (){

        Map<String,Object> quesData = new ArrayMap<>();

        quesData.put("QUESTION",qStr);
        quesData.put("A",aStr);
        quesData.put("B",bStr);
        quesData.put("ANSWER",ansStr);

        String doc_id = firestore.collection("1").document().getId();

        firestore.collection("1").document(doc_id).set(quesData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Map<String, Object> quesDoc = new ArrayMap<>();
                        quesDoc.put("Q" + String.valueOf(quesList.size() + 1) + "_ID", doc_id);
                        quesDoc.put("COUNT", String.valueOf(quesList.size() + 1));

                        firestore.collection("1").document("QUESTIONS_LIST")
                                .update(quesDoc)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(QuestionDetailsActivity.this, " Question Added Successfully", Toast.LENGTH_SHORT).show();

                                        quesList.add(new QuestionModel(
                                                doc_id,
                                                qStr, aStr, bStr, Integer.valueOf(ansStr)
                                        ));

                                        QuestionDetailsActivity.this.finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(QuestionDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }) .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(QuestionDetailsActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });
    }

    private void loadData(int id)
    {
        ques.setText(quesList.get(id).getQuestion());
        optionA.setText(quesList.get(id).getOptionA());
        optionB.setText(quesList.get(id).getOptionB());
        answer.setText(String.valueOf(quesList.get(id).getCorrectAns()));
    }

    private void editQuestion()
    {
        Map<String,Object> quesData = new ArrayMap<>();
        quesData.put("QUESTION", qStr);
        quesData.put("A",aStr);
        quesData.put("B",bStr);
        quesData.put("ANSWER",ansStr);


        firestore.collection("1").document(quesList.get(qID).getQuesID())
                .set(quesData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(QuestionDetailsActivity.this,"Question updated successfully",Toast.LENGTH_SHORT).show();

                        quesList.get(qID).setQuestion(qStr);
                        quesList.get(qID).setOptionA(aStr);
                        quesList.get(qID).setOptionB(bStr);
                        quesList.get(qID).setCorrectAns(Integer.valueOf(ansStr));

                        QuestionDetailsActivity.this.finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QuestionDetailsActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}