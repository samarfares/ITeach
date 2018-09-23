package com.example.tcc.iteach;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpStudentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewSignin2;
    private EditText editTextPasswordStudent , editTextEmailStudent,editTextFirstNameStudent,editTextLastNameStudent ;
    private DatePicker datePickerStudent;
    private RadioGroup radioGroupGender2;
    private Spinner subjects;
    private Button buttonContinueToLocation2;
    private TextView textView , textViewDOBStudent , textViewGenderStudent , textViewSubjects;
private RadioButton male2, female2;
String studentEmail, studentPassword, studentFName , studentLName, studentGender, studentDOB;
ProgressDialog progressDialog;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student);

        textViewSignin2 = (TextView) findViewById(R.id.textViewSignin2);
        editTextEmailStudent = (EditText) findViewById(R.id.editTextEmailStudent);
        editTextPasswordStudent= (EditText) findViewById(R.id.editTextPasswordStudent);
        editTextFirstNameStudent=(EditText) findViewById(R.id.editTextFirstNameStudent);
        editTextLastNameStudent=(EditText) findViewById(R.id.editTextLastNameStudent);
        datePickerStudent=(DatePicker)findViewById(R.id.datePickerStudent);
        radioGroupGender2=(RadioGroup)findViewById(R.id.radioGroupGender2);
        subjects=(Spinner)findViewById(R.id.subjects);
        buttonContinueToLocation2=(Button)findViewById(R.id.buttonContinueToLocation2);
        textView= (TextView)findViewById(R.id.textView);
        textViewDOBStudent=(TextView)findViewById(R.id.textViewDOBStudent);
        textViewGenderStudent= (TextView)findViewById(R.id.textViewGenderStudent);
        male2= (RadioButton)findViewById(R.id.male2);
        female2= (RadioButton)findViewById(R.id.female2);
        textViewSubjects=(TextView)findViewById(R.id.textViewSubjects);
        progressDialog =new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        textViewSignin2.setOnClickListener(this);
        buttonContinueToLocation2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view== textViewSignin2){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        if (view==buttonContinueToLocation2 ){
            registerStudent(); }
            }

    private void registerStudent() {
        studentEmail = editTextEmailStudent.getText().toString();
        studentPassword = editTextPasswordStudent.getText().toString();

        studentFName= editTextFirstNameStudent.getText().toString();
        studentLName= editTextLastNameStudent.getText().toString();
        studentDOB = datePickerStudent.getDayOfMonth()+"/"+datePickerStudent.getMonth()+"/"+datePickerStudent.getYear();
        // To know if user selects male or female
        int radioId= radioGroupGender2.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);
        studentGender = radioButton.getText().toString();

 // *************************SUBJECTS SPINNER MISSING

        if(TextUtils.isEmpty(studentEmail)){
            // email is empty!
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(studentPassword)){
            // password is empty!
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show(); return;}

        if (studentPassword.length()<6){
            Toast.makeText(this, "Password must me at least 6 characters long", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(studentFName)){
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_LONG).show(); return;}

        if(TextUtils.isEmpty(studentLName)){
            Toast.makeText(this, "Please enter your last name", Toast.LENGTH_LONG).show(); return;}

        if(TextUtils.isEmpty(studentGender)){
            Toast.makeText(this, "Please specify your gender", Toast.LENGTH_LONG).show(); return;}
// if validations are ok
        progressDialog.setMessage("Registering..Please wait..");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(studentEmail,studentPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpStudentActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                    //finish();
                    Intent intent = new Intent(SignUpStudentActivity.this,MapsActivity.class);
                    intent.putExtra("studentFName",studentFName);
                    intent.putExtra("studentLName",studentLName);
                    intent.putExtra("studentDOB",studentDOB);
                    intent.putExtra("SGender",studentGender);
                    intent.putExtra("location","location");
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }

                else {                    progressDialog.dismiss();

                    Toast.makeText(SignUpStudentActivity.this, "Registeration failed, "+task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }//else
            }//oncomplete
        });
    }
}
