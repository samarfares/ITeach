package com.example.tcc.iteach;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.firebase.database.DatabaseReference;

public class SignUpInstructorActivity extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener {
Instructor instructor;
private TextView textViewSignin2;
private EditText editTextPasswordInstructor , yearsExperience, editTextEmailInstructor,editTextFirstName,editTextLastName , editTextNumberInstructor, lessonsPrice;
private DatePicker datePicker;
private RadioGroup radioGroupGender;
private Spinner specialtySpinner , paymentSpinner, placeSpinner;
private Button buttonRegisterInstructor;
FirebaseAuth firebaseAuth;
ProgressDialog progressDialog;
String  instructorEmail, instructorPassword , firstName, lastName  , gender, date;
long instructorsPhoneNum;
int yearsOfExperience;
double price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_instructor);

        textViewSignin2 = (TextView) findViewById(R.id.textViewSignin2);
        editTextEmailInstructor = (EditText) findViewById(R.id.editTextEmailInstructor);
        editTextPasswordInstructor= (EditText) findViewById(R.id.editTextPasswordInstructor);
        editTextFirstName=(EditText) findViewById(R.id.editTextFirstName);
        editTextLastName=(EditText) findViewById(R.id.editTextLastName);
        editTextNumberInstructor= (EditText) findViewById(R.id.editTextNumberInstructor);
        lessonsPrice= (EditText)findViewById(R.id.lessonsPrice);
        datePicker=(DatePicker)findViewById(R.id.datePicker);
        radioGroupGender=(RadioGroup)findViewById(R.id.radioGroupGender);
        specialtySpinner=(Spinner)findViewById(R.id.specialty);
        yearsExperience= (EditText)findViewById(R.id.yearsExperience);
        paymentSpinner=(Spinner)findViewById(R.id.payment);
        placeSpinner=(Spinner)findViewById(R.id.place);
        buttonRegisterInstructor=(Button)findViewById(R.id.buttonRegisterInstructor);
        FirebaseUser firebaseUser;
        DatabaseReference databaseReference;

        textViewSignin2.setOnClickListener(this);
        buttonRegisterInstructor.setOnClickListener(this);
        firstName= editTextFirstName.getText().toString();
        lastName= editTextLastName.getText().toString();
        yearsOfExperience = Integer.parseInt(yearsExperience.getText().toString());
        instructorsPhoneNum = Long.parseLong(editTextNumberInstructor.getText().toString());
        date = datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+datePicker.getYear();
       // To know if user selects male or female
       int radioId= radioGroupGender.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);
        gender = radioButton.getText().toString();
        // fill the payment spinner with the payment methods in the strings.xml
        ArrayAdapter<CharSequence> paymentAdapter = ArrayAdapter.createFromResource(this,R.array.paymentMethod,android.R.layout.simple_spinner_item);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
paymentSpinner.setAdapter(paymentAdapter);
paymentSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        ArrayAdapter<CharSequence> specialtyAdapter = ArrayAdapter.createFromResource(this,R.array.specialty,android.R.layout.simple_spinner_item);
        specialtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialtySpinner.setAdapter(specialtyAdapter);
        specialtySpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        ArrayAdapter<CharSequence> placeAdapter = ArrayAdapter.createFromResource(this,R.array.lessonsPlace,android.R.layout.simple_spinner_item);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(placeAdapter);

    }


    private void registerInstructor(){
        instructorEmail = editTextEmailInstructor.getText().toString();
        instructorPassword = editTextPasswordInstructor.getText().toString();

        if(TextUtils.isEmpty(instructorEmail)){
            // email is empty!
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(instructorPassword)){
            // password is empty!
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show(); return;}

        if (instructorPassword.length()<6){
            Toast.makeText(this, "Password must me at least 6 characters long", Toast.LENGTH_LONG).show();return;}

// if validations are ok
        progressDialog.setMessage("Registering..Please wait..");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(instructorEmail,instructorPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            instructor = new Instructor(firstName,lastName,date,gender,"location",instructorsPhoneNum, yearsOfExperience,price);








                            //Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                            finish();
                            startActivity(new Intent(getApplicationContext(),InstructorActivity.class));}

                        else {Toast.makeText(SignUpInstructorActivity.this, "Registeration failed, "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.hide();

                        }//else
                    }//oncomplete
                });
    }

    @Override
    public void onClick(View view) {
        if(view== textViewSignin2){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        if (view==buttonRegisterInstructor ){
            registerInstructor();



        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String chosenPaymentMethod, chosenSpecialty,chosenPlace;
        if(view==paymentSpinner)
        chosenPaymentMethod = adapterView.getItemAtPosition(i).toString();

        else if (view==specialtySpinner)
            chosenSpecialty = adapterView.getItemAtPosition(i).toString();

        else if(view==placeSpinner)
            chosenPlace= adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
