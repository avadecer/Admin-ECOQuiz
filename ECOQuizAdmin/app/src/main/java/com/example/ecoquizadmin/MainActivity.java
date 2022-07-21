package com.example.ecoquizadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText email, pass;
    private Button login;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.loginBtn);

        firebaseAuth = FirebaseAuth.getInstance();

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

    private void firebaseLogin() {

        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Welcome back, Admin!", Toast.LENGTH_SHORT).show();
                            open2ndPage();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Incorrect Email/Password", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    private void open2ndPage(){
        Intent intent = new Intent(this, AgePage.class);
        startActivity(intent);
    }
    }