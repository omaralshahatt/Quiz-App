package com.example.quizapptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartQuiz extends AppCompatActivity {

    Button startBtn;
    FirebaseAuth auth;
    FirebaseUser user;
    Button logoutBtn;
    TextView txtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        auth = FirebaseAuth.getInstance();
        logoutBtn = findViewById(R.id.logout);
        txtView = findViewById(R.id.sigedIn);
        user = auth.getCurrentUser();
        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginAct.class);
            startActivity(intent);
            finish();
        }
        else{
            txtView.setText("Signed In As: "+user.getEmail());
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginAct.class);
                startActivity(intent);
                finish();
            }
        });






        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartQuiz.this, QuizAct.class);
                startActivity(intent);
            }
        });

    }
}