package com.example.quizapptest;

import android.animation.Animator;
import android.animation.*;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class QuizAct extends AppCompatActivity{


    TextView tvQuestion, tvQuestionNo, tvScore;
    Button next;
    private int count = 0, posi = 0, score = 0;
    private LinearLayout choicesLayout;
    private List<Q_A> questionlist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuestion = findViewById(R.id.questions);
        tvQuestionNo = findViewById(R.id.queNo);
        tvScore = findViewById(R.id.scoreTxt);
        choicesLayout = findViewById(R.id.choices_layout);
        next = findViewById(R.id.nextBtn);

        questionlist = new ArrayList<>();
        questionlist.add(new Q_A("The runtime system starts your program by calling which function first?", "print", "iterative", "hello", "main", "main"));
        questionlist.add(new Q_A("What method can be used to create a new instance of an object?", "another instance", "field", "constructor", "private method", "constructor"));
        questionlist.add(new Q_A("Which type of variable keeps a constant value once it is assigned?", "non static", "static", "final", "private", "final"));
        questionlist.add(new Q_A("Which keyword lets you call the constructor of a parent class?", "parent", "super", "this", "new", "super"));
        questionlist.add(new Q_A("Subclasses of an abstract class are created using the keyword?", "extends", "abstracts", "interfaces", "implements", "extends"));
        questionlist.add(new Q_A("What is true about a final class?", "class declared final is a final class", "Final classes are created so the methods implemented by that class cannot be overriddden", "It can't be inherited", "All of the above", "All of the above"));
        questionlist.add(new Q_A("What is the size of float and double in java?", "32 and 64", "32 and 32", "64 and 64", "64 and 32", "32 and 64"));
        questionlist.add(new Q_A("Which access modifier makes variables and methods visible only in the class where they are declared?", "public", "protected", "non-modifier", "private", "private"));
        questionlist.add(new Q_A("Which keyword lets you use an interface?", "extends", "implements", "inherits", "import", "implements"));
        questionlist.add(new Q_A("How can you achieve runtime polymorphism in Java?", "method overloading", "method overrunning", "method overriding", "method calling", "method overriding"));

        for(int i = 0 ; i < 4 ; i++){
            choicesLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAns((Button) v);
                }
            });
        }

        playAni(tvQuestion, 0, questionlist.get(posi).getQuestion());
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setEnabled(false);
                next.setAlpha(0.7f);
                enableChoice(true);
                posi++;
                if(posi == questionlist.size()){
                    finishQuiz();
                    return;
                }
                count = 0;
                playAni(tvQuestion, 0, questionlist.get(posi).getQuestion());
            }
        });
    }


    private void playAni(final View view,final int value, String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                        if(value == 0 && count < 4){
                            String option = "";
                            if(count == 0){
                                option = questionlist.get(posi).getAnsA();
                            }
                            else if(count == 1){
                                option = questionlist.get(posi).getAnsB();
                            }
                            else if(count == 2) {
                                option = questionlist.get(posi).getAnsC();
                            }
                            else if(count == 3) {
                                option = questionlist.get(posi).getAnsD();
                            }
                            playAni(choicesLayout.getChildAt(count),0, option);
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        if(value == 0){
                            try{
                                ((TextView)view).setText(data);
                                tvQuestionNo.setText(posi+1+" / "+questionlist.size());
                            }
                            catch(ClassCastException ex){
                                ((Button)view).setText(data);
                            }
                            view.setTag(data);
                            playAni(view, 1, data);
                        }
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {

                    }
                });
    }

    private void checkAns(Button selectedAns){
        enableChoice(false);
        next.setEnabled(true);
        next.setAlpha(1);
        if(selectedAns.getText().toString().equals(questionlist.get(posi).getCorrectAns())){
            score++;
            selectedAns.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#68E8E3")));
            tvScore.setText("Score: "+score);
        }
        else {
            selectedAns.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctOption = (Button) choicesLayout.findViewWithTag(questionlist.get(posi).getCorrectAns());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#68E8E3")));
        }
    }

    private void enableChoice(boolean enable){
        for(int i = 0 ; i < 4 ; i++){
            choicesLayout.getChildAt(i).setEnabled(enable);
            if(enable){
                choicesLayout.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#30ffffff")));
            }
        }
    }

    void finishQuiz(){
        String passStat = "";
        if(score >= questionlist.size()*0.6){
            passStat = "Congrats you passed!";
        }
        else {
            passStat = "Better luck next time";
        }
        new AlertDialog.Builder(this).setTitle(passStat).setMessage("Score Is "+score+" Out Of "+ questionlist.size()).setPositiveButton("Ok", (dialogInterface, i) -> ok()).setCancelable(false).show();
    }

    void ok(){
        Intent intent = new Intent(getApplicationContext(), StartQuiz.class);
        startActivity(intent);
    }

}