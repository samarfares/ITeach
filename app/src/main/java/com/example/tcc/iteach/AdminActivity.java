package com.example.tcc.iteach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    Button instructor;
    Button student;
    Button subject;
    Button blackboard;

    private Button buttonLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        firebaseAuth = FirebaseAuth.getInstance();
      /*  if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }*/
        instructor =(Button) findViewById(R.id.instructor);
        instructor.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), InstructorActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        student =(Button) findViewById(R.id.student);
        student.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), StudentActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        subject =(Button) findViewById(R.id.subject);
        subject.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), InstructorActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        blackboard =(Button) findViewById(R.id.blackboard);
        blackboard.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), InstructorActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        buttonLogout =(Button)findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
