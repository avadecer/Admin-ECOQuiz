package com.example.ecoquizadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText email, pass;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.loginBtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().isEmpty()){
                    email.setError("Enter valid email ID");
                    return;
                }
                else {
                    email.setError(null);
                }

                if(pass.getText().toString().isEmpty()){
                    pass.setError("Enter Password");
                    return;
                }
                else {
                    pass.setError(null);
                }

                firebaseLogin();
            }
        });
    }

    private void firebaseLogin(){
        
    }
}