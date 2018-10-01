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
import android.util.Base64;
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

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class SignUpInstructorActivity extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener {
Instructor instructor;
private TextView textViewSignin2 , textview , textViewDOB, textViewGender , textViewSpecialty, textViewPayment, textViewPlace , textViewMethod;
private EditText editTextPasswordInstructor , yearsExperience, editTextEmailInstructor,editTextFirstName,editTextLastName , editTextNumberInstructor, lessonsPrice;
private DatePicker datePicker;
private RadioGroup radioGroupGender;
private RadioButton male, female;
private Spinner specialtySpinner , paymentSpinner, placeSpinner , method;
private Button buttonContinueToLocation;
private Button register;
FirebaseAuth firebaseAuth;
DatabaseReference databaseReference;
FirebaseUser firebaseUser;
ProgressDialog progressDialog;
String  instructorEmail, instructorPassword , firstName, lastName  , gender, date , yearsOfExperience , instructorsPhoneNum , chosenString, priceString ,   chosenPaymentMethod,chosenPlace , chosenMethod;
long longInstructorsPhoneNum;
int intYearsOfExperience;
double price;
private String encryptedLatitude,encryptedLongitude;
private LatLng location,encryptedLocation;
private String latitude,longitude;
private static String cryptoPass = "sup3rS3xy";
    List<String> chosen = new LinkedList<String>();
    MultiSelectionSpinner spinner;



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
        textViewMethod=(TextView) findViewById(R.id.textViewMethod);
method=(Spinner)findViewById(R.id.method);
        progressDialog =new ProgressDialog(this);
// database stuff
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Instructors");
// onclick methods calling
        textViewSignin2.setOnClickListener(this);
        buttonContinueToLocation.setOnClickListener(this);
        register.setOnClickListener(this);

        // fill spinners
        ArrayAdapter<CharSequence> paymentAdapter = ArrayAdapter.createFromResource(this ,R.array.paymentMethod,android.R.layout.simple_spinner_item);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentAdapter);
        //setContentView(R.layout.activity_sign_up_instructor);
        paymentSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> placeAdapter = ArrayAdapter.createFromResource(this,R.array.lessonsPlace,android.R.layout.simple_spinner_item);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(placeAdapter);
        placeSpinner.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> methodAdapter = ArrayAdapter.createFromResource(this,R.array.method,android.R.layout.simple_spinner_item);
        methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        method.setAdapter(methodAdapter);
        method.setOnItemSelectedListener(this);

        spinner=(MultiSelectionSpinner) findViewById(R.id.input1);
        List<String> list = new ArrayList<String>();
        list.add("Arabic");
        list.add("English");
        list.add("Math");
        list.add("Chemistry");
        list.add("Physics");
        list.add("Music");
        list.add("Dancing");
        list.add("Painting");
        list.add("Cooking");
        spinner.setItems(list);

        // Location
        Bundle bundle = getIntent().getParcelableExtra("bundle");
        if (bundle!=null){
        location = bundle.getParcelable("location");
        latitude= String.valueOf( location.latitude );
        encryptedLatitude=encryptIt( latitude );
        longitude= String.valueOf( location.longitude );
        encryptedLongitude=encryptIt( longitude );
        encryptedLocation=new LatLng(  Double.parseDouble(encryptedLatitude),Double.parseDouble(encryptedLongitude));}
// end location
    }


    private void registerInstructor(){
        instructorEmail = editTextEmailInstructor.getText().toString();
        instructorPassword = editTextPasswordInstructor.getText().toString();

        firstName= editTextFirstName.getText().toString();
        lastName= editTextLastName.getText().toString();
        yearsOfExperience = yearsExperience.getText().toString();
        instructorsPhoneNum = editTextNumberInstructor.getText().toString();
        date = datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+datePicker.getYear();
        priceString = lessonsPrice.getText().toString();
        // To know if user selects male or female
        int radioId= radioGroupGender.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);
        if(radioButton!=null)
        gender = radioButton.getText().toString();


        if(TextUtils.isEmpty(instructorEmail)){
            // email is empty!
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(instructorPassword)){
            // password is empty!
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show(); return;}

        if (instructorPassword.length()<6){
            Toast.makeText(this, "Password must me at least 6 characters long", Toast.LENGTH_LONG).show();return;}
       //if (location==null){
            //Toast.makeText(SignUpInstructorActivity.this, "Please enter your location", Toast.LENGTH_SHORT).show(); return;}

        if(TextUtils.isEmpty(firstName)){
           Toast.makeText(this, "Please enter your first name", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(lastName)){
            Toast.makeText(this, "Please enter your last name", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(date)){
            Toast.makeText(this, "Please enter your date of birth", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(yearsOfExperience)){
            Toast.makeText(this, "Please enter your years of experience", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(instructorsPhoneNum)){
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(gender)){
            Toast.makeText(this, "Please specify your gender", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(priceString)){
            Toast.makeText(this, "Please enter your lesson price", Toast.LENGTH_LONG).show();return;}
// if validations are ok we register user
        intYearsOfExperience=Integer.parseInt(yearsOfExperience);
        longInstructorsPhoneNum=Long.parseLong(instructorsPhoneNum);
price=Double.parseDouble(priceString);
        progressDialog.setMessage("Registering..Please wait..");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(instructorEmail,instructorPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            progressDialog.dismiss();
                            chosen=spinner.getSelectedStrings();
chosenString= spinner.getSelectedItemsAsString(); // this variable contains the specialties chosen by the instructor as a string EX:Arablic,English
                            //Toast.makeText(SignUpInstructorActivity.this, "chosen "+ chosen.get(0), Toast.LENGTH_LONG).show();
                            instructor = new Instructor(firstName,lastName,date,gender,encryptedLocation,longInstructorsPhoneNum, intYearsOfExperience,price,0 , chosen , chosenPaymentMethod, chosenPlace , chosenMethod);
                           /* Intent intent = new Intent(SignUpInstructorActivity.this,MapsActivity.class);
                            intent.putExtra("instructorsFName",firstName);*/
                            firebaseUser=firebaseAuth.getCurrentUser();
                            String id = databaseReference.push().getKey();
                            databaseReference.child(id).setValue(instructor);
                           // databaseReference.child(id).setValue(chosenString);
                            Toast.makeText(SignUpInstructorActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),instructor_main.class));
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
            Intent intent = new Intent(SignUpInstructorActivity.this,LocationActivity.class);
            intent.putExtra("key",1);
           startActivity(intent);


            }
        if (view==register ){


            registerInstructor();
            //startActivity(new Intent(this,schedule.class));
        }


    }
    public static String encryptIt(String value) {
        try {
            DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] clearText = value.getBytes("UTF8");
            // Cipher is not thread safe
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            String encryptedValue = Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT);
            return encryptedValue;

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return value;
    };
    public static String decryptIt(String value) {
        try {
            DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] encrypedPwdBytes = Base64.decode(value, Base64.DEFAULT);
            // cipher is not thread safe
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypedValueBytes = (cipher.doFinal(encrypedPwdBytes));

            String decrypedValue = new String(decrypedValueBytes);
            return decrypedValue;

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView==paymentSpinner)
        chosenPaymentMethod = adapterView.getItemAtPosition(i).toString();


        if(adapterView==placeSpinner)
            chosenPlace= adapterView.getItemAtPosition(i).toString();

        if(adapterView==method)
            chosenMethod= adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
