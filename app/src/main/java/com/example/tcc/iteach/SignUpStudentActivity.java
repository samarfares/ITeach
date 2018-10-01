package com.example.tcc.iteach;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
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

public class SignUpStudentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewSignin2;
    private EditText editTextPasswordStudent , editTextEmailStudent,editTextFirstNameStudent,editTextLastNameStudent ;
    private DatePicker datePickerStudent;
    private RadioGroup radioGroupGender2;
    private Button buttonContinueToLocation2 , buttonRegister2;
    private TextView textView , textViewDOBStudent , textViewGenderStudent , textViewSubjects;
private RadioButton male2, female2;
    MultiSelectionSpinner spinner2;
String studentEmail, studentPassword, studentFName , studentLName, studentGender, studentDOB;
ProgressDialog progressDialog;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
DatabaseReference databaseReference2;
Person person;
private LatLng location;
    private String encryptedLocation;
    private String latitude,longitude ,encryptedLatitude,encryptedLongitude;
    private static String cryptoPass = "sup3rS3xy";

    List<String> subjects = new ArrayList<String>();

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
        buttonContinueToLocation2=(Button)findViewById(R.id.buttonContinueToLocation2);
        buttonRegister2=(Button)findViewById(R.id.buttonRegister2);
        textView= (TextView)findViewById(R.id.textView);
        textViewDOBStudent=(TextView)findViewById(R.id.textViewDOBStudent);
        textViewGenderStudent= (TextView)findViewById(R.id.textViewGenderStudent);
        male2= (RadioButton)findViewById(R.id.male2);
        female2= (RadioButton)findViewById(R.id.female2);
        textViewSubjects=(TextView)findViewById(R.id.textViewSubjects);
        progressDialog =new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference2= FirebaseDatabase.getInstance().getReference("Students");


        spinner2=(MultiSelectionSpinner) findViewById(R.id.input2);
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
        spinner2.setItems(list);


        // Location
        Bundle bundle = getIntent().getParcelableExtra("bundle");
        if (bundle!=null){
            location = bundle.getParcelable("location");
            encryptedLocation=encryptIt( location.toString() );}
// end location

        textViewSignin2.setOnClickListener(this);
        buttonContinueToLocation2.setOnClickListener(this);
buttonRegister2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view== textViewSignin2){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        if (view==buttonContinueToLocation2 ){
            Intent intent = new Intent(SignUpStudentActivity.this,LocationActivity.class);
            intent.putExtra("key", "student" );
            startActivity(intent);        }

         if (view==buttonRegister2)
             registerStudent();

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
        if(radioButton!=null)
        studentGender = radioButton.getText().toString();


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

        if(TextUtils.isEmpty(studentDOB)){
            Toast.makeText(this, "Please specify your date of birth", Toast.LENGTH_LONG).show(); return;}

       // if (location==null){
          //  Toast.makeText(SignUpStudentActivity.this, "Please enter your location", Toast.LENGTH_SHORT).show(); return;}

// if validations are ok
        progressDialog.setMessage("Registering..Please wait..");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(studentEmail,studentPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    subjects=spinner2.getSelectedStrings();
                    person = new Person(studentFName,studentLName,studentDOB,studentGender,encryptedLocation);
                    firebaseUser=firebaseAuth.getCurrentUser();
                    String id = databaseReference2.push().getKey();
                    databaseReference2.child(id).setValue(person);
                    databaseReference2.child(id).child("subjects").setValue(subjects);


                    Toast.makeText(SignUpStudentActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                    //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }

                else {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpStudentActivity.this, "Registeration failed, "+task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }//else
            }//oncomplete
        });
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
    }

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







}
