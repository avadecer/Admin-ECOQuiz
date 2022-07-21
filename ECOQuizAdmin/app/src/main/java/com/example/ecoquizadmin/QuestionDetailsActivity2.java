package com.example.ecoquizadmin;

import static com.example.ecoquizadmin.QuestionActivity.quesList;
import static com.example.ecoquizadmin.QuestionActivity2.quesList2;

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

public class QuestionDetailsActivity2 extends AppCompatActivity {

    private EditText ques, optionA, optionB, answer;
    private Button addQB;
    private String qStr, aStr, bStr, ansStr;
    private FirebaseFirestore firestore;
    private String action;
    private int qID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details2);

        ques = findViewById(R.id.question2);
        optionA = findViewById(R.id.optionA2);
        optionB = findViewById(R.id.optionB2);
        answer = findViewById(R.id.answer2);
        addQB = findViewById(R.id.addQB2);

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
            getSupportActionBar().setTitle("Question " + String.valueOf(quesList2.size() + 1));
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
                    editQuestion2();
                }
                else
                {
                    addNewQuestion2();
                }

            }
        });
    }

    private void addNewQuestion2 (){

        Map<String,Object> quesData = new ArrayMap<>();

        quesData.put("QUESTION",qStr);
        quesData.put("A",aStr);
        quesData.put("B",bStr);
        quesData.put("ANSWER",ansStr);

        String doc_id = firestore.collection("2").document().getId();

        firestore.collection("2").document(doc_id).set(quesData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Map<String, Object> quesDoc = new ArrayMap<>();
                        quesDoc.put("Q" + String.valueOf(quesList2.size() + 1) + "_ID", doc_id);
                        quesDoc.put("COUNT", String.valueOf(quesList2.size() + 1));

                        firestore.collection("2").document("QUESTIONS_LIST")
                                .update(quesDoc)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(QuestionDetailsActivity2.this, " Question Added Successfully", Toast.LENGTH_SHORT).show();

                                        quesList2.add(new QuestionModel(
                                                doc_id,
                                                qStr, aStr, bStr, Integer.valueOf(ansStr)
                                        ));

                                        QuestionDetailsActivity2.this.finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(QuestionDetailsActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }) .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QuestionDetailsActivity2.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadData(int id)
    {
        ques.setText(quesList2.get(id).getQuestion());
        optionA.setText(quesList2.get(id).getOptionA());
        optionB.setText(quesList2.get(id).getOptionB());
        answer.setText(String.valueOf(quesList2.get(id).getCorrectAns()));
    }

    private void editQuestion2()
    {
        Map<String,Object> quesData = new ArrayMap<>();
        quesData.put("QUESTION", qStr);
        quesData.put("A",aStr);
        quesData.put("B",bStr);
        quesData.put("ANSWER",ansStr);


        firestore.collection("2").document(quesList2.get(qID).getQuesID())
                .set(quesData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(QuestionDetailsActivity2.this,"Question updated successfully",Toast.LENGTH_SHORT).show();

                        quesList2.get(qID).setQuestion(qStr);
                        quesList2.get(qID).setOptionA(aStr);
                        quesList2.get(qID).setOptionB(bStr);
                        quesList2.get(qID).setCorrectAns(Integer.valueOf(ansStr));

                        QuestionDetailsActivity2.this.finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QuestionDetailsActivity2.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}