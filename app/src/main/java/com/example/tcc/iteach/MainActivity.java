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
import com.google.android.gms.tasks.OnFailureListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    private DatabaseReference databaseReference ,databaseReference2;
boolean check = true;
    FirebaseAuth firebaseAuth;
Intent intent;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth =FirebaseAuth.getInstance();


        databaseReference= FirebaseDatabase.getInstance().getReference();
databaseReference2=FirebaseDatabase.getInstance().getReference("Instructors");
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        textViewSignup = (TextView) findViewById(R.id.textViewSignUp);

        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
intent = new Intent(MainActivity.this, instructor_main.class );
    }


    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "ادخل الايميل", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "password should be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;

        }

       /* if(email.equals("haya@hotmail.com")&&password.equals("123456")){
            finish();
            startActivity(new Intent(getApplicationContext(),AdminHome.class));


        }*/
       // else if (!email.equals("haya@hotmail.com") && password.equals("123456")) {
           // SharedPreferences sharedPreferences = getSharedPreferences("MayData", MODE_PRIVATE);
         //   SharedPreferences.Editor editor = sharedPreferences.edit();
           // editor.putString("User", "NotAdmin");
           // editor.commit();}


        progressDialog.setMessage("Logging in please wait...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            finish();

                            databaseReference2.addValueEventListener( new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                        if (snap.child( "userID" ).getValue().toString().equals( firebaseAuth.getCurrentUser().getUid() )) {
                                            check=false;
                                            Toast.makeText( MainActivity.this, snap.child( "userID" ).getValue().toString() + "\n" + firebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_LONG ).show();
                                            //intent.putExtra("username", snap.child("firstName").toString()+" "+snap.child("lastName"));
                                            startActivity( intent);

                                        }
                                    }
                                    if (check){
                                    startActivity(new Intent(getApplicationContext(),student_main.class));}
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            } );




                            //startActivity(new Intent(getApplicationContext(),instructor_main.class));
                        }

                         else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "login failed, " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }//else
                    }








                });
    }


    @Override
    public void onClick(View view) {
        if(view== buttonSignIn){
            userLogin();
            //startActivity(new Intent(this, SearchForInstructorActivity.class));

        }
        if(view== textViewSignup){
            finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }
    }
}