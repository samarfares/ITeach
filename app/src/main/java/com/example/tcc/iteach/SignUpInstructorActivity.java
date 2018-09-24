package com.example.tcc.iteach;

import android.accounts.Account;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;

public class SignUpInstructorActivity extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener {
Instructor instructor;
private TextView textViewSignin2 , textview , textViewDOB, textViewGender , textViewSpecialty, textViewPayment, textViewPlace;
private EditText editTextPasswordInstructor , yearsExperience, editTextEmailInstructor,editTextFirstName,editTextLastName , editTextNumberInstructor, lessonsPrice;
private DatePicker datePicker;
private RadioGroup radioGroupGender;
private RadioButton male, female;
private Spinner specialtySpinner , paymentSpinner, placeSpinner;
private Button buttonContinueToLocation;
private Button register;
FirebaseAuth firebaseAuth;
ProgressDialog progressDialog;
String  instructorEmail, instructorPassword , firstName, lastName  , gender, date;
long instructorsPhoneNum;
int yearsOfExperience;
double price;
private String location;

String [] listItems;
boolean [] checkedItems;
ArrayList<Integer> mUserItems = new ArrayList<>();




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
        buttonContinueToLocation=(Button)findViewById(R.id.buttonContinueToLocation);
        register=(Button)findViewById(R.id.buttonRegister);

        textViewDOB=(TextView) findViewById(R.id.textViewDOB);
        textview=(TextView) findViewById(R.id.textview);
        textViewGender=(TextView) findViewById(R.id.textViewGender);
        textViewSpecialty=(TextView) findViewById(R.id.textViewSpecialty);
        male=(RadioButton) findViewById(R.id.male);
        female=(RadioButton)findViewById(R.id.female);
        textViewPayment= (TextView) findViewById(R.id.textViewPayment);
        textViewPlace=(TextView) findViewById(R.id.textViewPlace);
        progressDialog =new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseUser firebaseUser;
        DatabaseReference databaseReference;
        textViewSignin2.setOnClickListener(this);
        buttonContinueToLocation.setOnClickListener(this);
///////////////////////////////////////////////////////////////////////////////////////
       /* listItems=getResources().getStringArray(R.array.specialty);
        checkedItems=new boolean[listItems.length];
        specialtySpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SignUpInstructorActivity.this);
                mBuilder.setTitle("Specialties");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                      if (isChecked){
                          if(! mUserItems.contains(position)){
                              mUserItems.add(position);
                          }
                          else{
                              mUserItems.remove(position);
                          }


                      }

                    }
                });
mBuilder.setCancelable(false);
mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        String item="";
        for(int i=0 ; i<mUserItems.size();i++){
            item=item+listItems[mUserItems.get(i)];
            if(i !=mUserItems.size()-1)
                item=item+",";

        }

    }
});
            }
        }); */

    location=getIntent().getStringExtra( "location" );



    }


    private void registerInstructor(){
        instructorEmail = editTextEmailInstructor.getText().toString();
        instructorPassword = editTextPasswordInstructor.getText().toString();

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
        ArrayAdapter<CharSequence> paymentAdapter = ArrayAdapter.createFromResource(this ,R.array.paymentMethod,android.R.layout.simple_spinner_item);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentAdapter);
        setContentView(R.layout.activity_sign_up_instructor);



        paymentSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);




       ArrayAdapter<CharSequence> specialtyAdapter = ArrayAdapter.createFromResource(this,R.array.specialty,android.R.layout.simple_spinner_item);
       specialtyAdapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
       specialtySpinner.setAdapter(specialtyAdapter);
       specialtySpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        ArrayAdapter<CharSequence> placeAdapter = ArrayAdapter.createFromResource(this,R.array.lessonsPlace,android.R.layout.simple_spinner_item);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(placeAdapter);

        if(TextUtils.isEmpty(instructorEmail)){
            // email is empty!
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(instructorPassword)){
            // password is empty!
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show(); return;}

        if (instructorPassword.length()<6){
            Toast.makeText(this, "Password must me at least 6 characters long", Toast.LENGTH_LONG).show();return;}
        if (location==null)
            Toast.makeText(SignUpInstructorActivity.this, "Please enter your location", Toast.LENGTH_SHORT).show();

// if validations are ok
        progressDialog.setMessage("Registering..Please wait..");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(instructorEmail,instructorPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            progressDialog.dismiss();


                            instructor = new Instructor(firstName,lastName,date,gender,location,instructorsPhoneNum, yearsOfExperience,price);
                           /* Intent intent = new Intent(SignUpInstructorActivity.this,MapsActivity.class);
                            intent.putExtra("instructorsFName",firstName);
                            intent.putExtra("instructorsLName",lastName);
                            intent.putExtra("DOB",date);
                            intent.putExtra("gender",gender);
                            intent.putExtra("location","location");
                            intent.putExtra("phoneNum",instructorsPhoneNum);
                            intent.putExtra("yearsOfExperience",yearsOfExperience);
                            intent.putExtra("price",price);*/

                            Toast.makeText(SignUpInstructorActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }

                        else {progressDialog.dismiss();
                            Toast.makeText(SignUpInstructorActivity.this, "Registration failed, "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                             }//else
                    }//oncomplete
                }); }

    @Override
    public void onClick(View view) {
        if(view== textViewSignin2){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        if (view==buttonContinueToLocation ){

            startActivity(new Intent(this, LocationActivity.class));


            }
        if (view==register ){


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
