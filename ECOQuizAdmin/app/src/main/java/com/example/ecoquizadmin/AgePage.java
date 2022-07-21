package com.example.ecoquizadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AgePage extends AppCompatActivity {
    private Button below12, above12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_page);

        below12 = findViewById(R.id.b12_Btn);
        above12 = findViewById(R.id.b13_Btn);

        below12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgePage.this, QuestionActivity.class);
                startActivity(intent);
            }
        });

        above12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgePage.this, QuestionActivity2.class);
                startActivity(intent);
            }
        });
    }
}