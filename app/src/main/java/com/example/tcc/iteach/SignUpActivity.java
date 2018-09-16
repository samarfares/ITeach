package com.example.tcc.iteach;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonSignupInstructor;
    private Button buttonSignupStudent;
    private TextView textViewSignin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonSignupInstructor = (Button) findViewById(R.id.buttonSignupInstructor);
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
