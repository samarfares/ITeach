package com.example.tcc.iteach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonSignupInstructor;
    private Button buttonSignupStudent;
    private TextView textViewSignin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonSignupInstructor = (Button) findViewById(R.id.buttonSignUpInstructor );
        textViewSignin = (TextView) findViewById(R.id.textViewSignIn);
        buttonSignupStudent=(Button) findViewById(R.id.buttonSignupStudent);

        buttonSignupInstructor.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
                buttonSignupStudent.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view== buttonSignupInstructor){
            finish();
            startActivity(new Intent(this, SignUpInstructorActivity.class));        }

        if(view== buttonSignupStudent){
            finish();
            startActivity(new Intent(this, SignUpStudentActivity.class));        }


        if(view== textViewSignin){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

}
