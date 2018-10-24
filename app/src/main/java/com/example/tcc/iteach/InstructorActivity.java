package com.example.tcc.iteach;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.MenuPopupWindow;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.tcc.iteach.SignUpInstructorActivity.encryptIt;

public class InstructorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ListView listView;
    final ArrayList<String> keyList = new ArrayList<>();
    private Button buttonContinueToLocation;

    List<Instructor> list;
    MyAdapterSearch myAdapter;
    ProgressDialog progressDialog;
    private DatePicker datePicker;
    private RadioGroup radioGroupGender;
    private RadioButton male, female;
    private Spinner specialtySpinner , paymentSpinner, placeSpinner , method;
    long longInstructorsPhoneNum;
    int intYearsOfExperience;
    double price;
    private LatLng location;
    private String encryptedLocation;
    private String latitude,longitude;
    private static String cryptoPass = "sup3rS3xy";
    List<String> chosen = new ArrayList<String>();
    MultiSelectionSpinner spinner;
    private TextView textViewSignin2 , textview , textViewDOB, textViewGender , textViewSpecialty, textViewPayment, textViewPlace , textViewMethod;


    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextPasswordInstructor , yearsExperience, editTextEmailInstructor,editTextFirstName,editTextLastName , editTextNumberInstructor, lessonsPrice;
    private Button addNew;
    private Button add;
    private Button upload;
    String  instructorEmail, instructorPassword , firstName, lastName  , gender, date , yearsOfExperience , instructorsPhoneNum , chosenString, priceString ,   chosenPaymentMethod,chosenPlace , chosenMethod , insId;

    private TextView textLocation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        firebaseAuth = FirebaseAuth.getInstance();
        listView = (ListView) findViewById(R.id.list1);
        buttonContinueToLocation=(Button)findViewById(R.id.buttonContinueToLocation);
        buttonContinueToLocation.setVisibility(View.GONE);
        editTextEmailInstructor = (EditText) findViewById(R.id.editTextEmailInstructor);
        editTextEmailInstructor.setVisibility( View.GONE );
        editTextPasswordInstructor= (EditText) findViewById(R.id.editTextPasswordInstructor);
        editTextPasswordInstructor.setVisibility( View.GONE );
        editTextFirstName=(EditText) findViewById(R.id.editTextFirstName);
        editTextFirstName.setVisibility( View.GONE );
        editTextLastName=(EditText) findViewById(R.id.editTextLastName);
        editTextLastName.setVisibility( View.GONE );
        editTextNumberInstructor= (EditText) findViewById(R.id.editTextNumberInstructor);
        editTextNumberInstructor.setVisibility( View.GONE );
        lessonsPrice= (EditText)findViewById(R.id.lessonsPrice);
        lessonsPrice.setVisibility( View.GONE );
        datePicker=(DatePicker)findViewById(R.id.datePicker);
        datePicker.setVisibility(View.GONE  );
        radioGroupGender=(RadioGroup)findViewById(R.id.radioGroupGender);
        radioGroupGender.setVisibility( View.GONE );
        yearsExperience= (EditText)findViewById(R.id.yearsExperience);
        yearsExperience.setVisibility( View.GONE );
        paymentSpinner=(Spinner)findViewById(R.id.payment);
        paymentSpinner.setVisibility( View.GONE );
        placeSpinner=(Spinner)findViewById(R.id.place);
        placeSpinner.setVisibility( View.GONE );
        textViewDOB=(TextView) findViewById(R.id.textViewDOB);
        textViewDOB.setVisibility( View.GONE );
        spinner=(MultiSelectionSpinner) findViewById(R.id.input1);
        spinner.setVisibility( View.GONE );

        textViewGender=(TextView) findViewById(R.id.textViewGender);
        textViewGender.setVisibility( View.GONE );
        textViewSpecialty=(TextView) findViewById(R.id.textViewSpecialty);
        textViewSpecialty.setVisibility( View.GONE );
        male=(RadioButton) findViewById(R.id.male);
        male.setVisibility( View.GONE );
        female=(RadioButton)findViewById(R.id.female);
        female.setVisibility( View.GONE );
        textViewPayment= (TextView) findViewById(R.id.textViewPayment);
        textViewPayment.setVisibility( View.GONE );
        textViewPlace=(TextView) findViewById(R.id.textViewPlace);
        textViewPlace.setVisibility( View.GONE );
        textViewMethod=(TextView) findViewById(R.id.textViewMethod);
        textViewMethod.setVisibility( View.GONE );
        method=(Spinner)findViewById(R.id.method);
        method.setVisibility( View.GONE );
        databaseReference = FirebaseDatabase.getInstance().getReference("Instructors");
        add=(Button) findViewById(R.id.add);
        addNew=(Button) findViewById(R.id.addNew);
        addNew.setVisibility( View.GONE );
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
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
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editTextEmailInstructor.setVisibility( View.VISIBLE );
                editTextPasswordInstructor.setVisibility( View.VISIBLE );
                editTextFirstName.setVisibility( View.VISIBLE );
                editTextLastName.setVisibility( View.VISIBLE );
                editTextNumberInstructor.setVisibility( View.VISIBLE );
                lessonsPrice.setVisibility( View.VISIBLE );
                datePicker.setVisibility(View.VISIBLE  );
                radioGroupGender.setVisibility( View.VISIBLE );
                yearsExperience.setVisibility( View.VISIBLE );
                paymentSpinner.setVisibility( View.VISIBLE );
                placeSpinner.setVisibility( View.VISIBLE );
                textViewDOB.setVisibility( View.VISIBLE );
                textViewGender.setVisibility( View.VISIBLE );
                textViewSpecialty.setVisibility( View.VISIBLE );
                male.setVisibility( View.VISIBLE );
                female.setVisibility( View.VISIBLE );
                textViewPayment.setVisibility( View.VISIBLE );
                textViewPlace.setVisibility( View.VISIBLE );
                textViewMethod.setVisibility( View.VISIBLE );
                method.setVisibility( View.VISIBLE );
                spinner.setVisibility( View.VISIBLE );
                addNew.setVisibility( View.VISIBLE );
                listView.setVisibility( View.GONE );
                add.setVisibility( View.GONE );

                buttonContinueToLocation.setVisibility(View.VISIBLE);


            }
        });
        buttonContinueToLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(InstructorActivity.this,LocationActivity.class);
                intent.putExtra("key", "AdminInstructor" );
                intent.putExtra("search", "false" );

                startActivity(intent);


            }
        });

        ArrayAdapter<CharSequence> paymentAdapter = ArrayAdapter.createFromResource(this ,R.array.paymentMethod,android.R.layout.simple_spinner_item);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentAdapter);
        //setContentView(R.layout.activity_sign_up_instructor);
        paymentSpinner.setOnItemSelectedListener( this );

        ArrayAdapter<CharSequence> placeAdapter = ArrayAdapter.createFromResource(this,R.array.lessonsPlace,android.R.layout.simple_spinner_item);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(placeAdapter);
        placeSpinner.setOnItemSelectedListener( this );


        ArrayAdapter<CharSequence> methodAdapter = ArrayAdapter.createFromResource(this,R.array.method,android.R.layout.simple_spinner_item);
        methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        method.setAdapter(methodAdapter);
        method.setOnItemSelectedListener( this );

        List<String> list1 = new ArrayList<String>();

        list1.add("العربية");
        list1.add("الانكليزية");
        list1.add("الرياضيات");
        list1.add("الكيمياء");
        list1.add("الفيزياء");
        list1.add("الموسيقى");
        list1.add("الرقص");
        list1.add("الرسم");
        list1.add("الطبخ");
        spinner.setItems(list1);

        // Location
        Bundle bundle = getIntent().getParcelableExtra("bundle");
        if (bundle!=null){
            location = bundle.getParcelable("location");
            encryptedLocation=encryptIt( location.toString() );}

        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("الرجاء الانتظار..");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("Instructors");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    keyList.add(snap.getKey());

                    Instructor instructor = snap.getValue(Instructor.class);
                    list.add(instructor);
                }
                myAdapter = new MyAdapterSearch(InstructorActivity.this,R.layout.items,list);
                listView.setAdapter(myAdapter);
                Utility.setListViewHeightBasedOnChildren(listView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(InstructorActivity.this)
                        .setTitle("حذف الأستاذ")
                        .setMessage("هل أنت متأكد من الحذف؟؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(position);
                                myAdapter.notifyDataSetChanged();

                                databaseReference.getRoot().child("Instructors").child(keyList.get(position)).removeValue();
                                keyList.remove(position);
                                Toast.makeText(getApplicationContext(),"تم الحذف بنجاح",Toast.LENGTH_LONG).show();

                            }
                        }).setNegativeButton("لا", null).show();


            }
        });

    }



    public void uploadData(View view){

        // insert data

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
        chosen=spinner.getSelectedStrings();
        // Toast.makeText(SignUpInstructorActivity.this,chosen.get( 0 ), Toast.LENGTH_SHORT).show();


        chosenString= spinner.getSelectedItemsAsString(); // this variable contains the specialties chosen by the instructor as a string EX:Arablic,English
        //instructor = new Instructor(firstName,lastName,date,gender,encryptedLocation,longInstructorsPhoneNum, intYearsOfExperience,price,0 , chosenPaymentMethod, chosenPlace , chosenMethod );
        chosenString= spinner.getSelectedItemsAsString();
        if(encryptedLocation== null ){
            // email is empty!
            Toast.makeText(this, "فضلاً اختر موقعه من الخارطة", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(instructorEmail)){
            // email is empty!
            Toast.makeText(this, "فضلأ أدخل ايميل", Toast.LENGTH_LONG).show();return;}


        if(TextUtils.isEmpty(instructorPassword)){
            // password is empty!
            Toast.makeText(this, "فضلأ أدخل كلمة مرور", Toast.LENGTH_LONG).show(); return;}

        if (instructorPassword.length()<6){
            Toast.makeText(this, "لايجب أن يكون طول كلمة المرور أقل من ستة أحرف", Toast.LENGTH_LONG).show();return;}


        if(TextUtils.isEmpty(instructorsPhoneNum)){
            Toast.makeText(this, "فضلاً أدخل رقم هاتف", Toast.LENGTH_LONG).show();return;}
        if(instructorsPhoneNum.length()!= 10&& !(instructorsPhoneNum.substring( 0,1 ).equals( "05" ))){
            Toast.makeText(this, "رقم الهاتف يجب أن يكون من 10 أرقام ويبدأ ب 05", Toast.LENGTH_LONG).show();return;}
        if(TextUtils.isEmpty(firstName)){
            Toast.makeText(this, "فضلاً أدخل اسمه الأول", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(lastName)){
            Toast.makeText(this, "فضلاُ أدخل اسم شهرته", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(date)){
            Toast.makeText(this, "فضلأ أدخل تاريخ ميلاده", Toast.LENGTH_LONG).show();return;}

        if(datePicker.getYear()>2003){
            Toast.makeText(this, "عذراً غير مسموح لك التسجيل بهذا العمر", Toast.LENGTH_LONG).show();return;}

        if(TextUtils.isEmpty(gender)){
            Toast.makeText(this, "فضلأ اختر جنسه", Toast.LENGTH_LONG).show();return;}
        if(chosen.size()==0){
            // email is empty!
            Toast.makeText(this, "فضلأ اختر المواد التي يدرسها", Toast.LENGTH_LONG).show();return;}



        if(TextUtils.isEmpty(yearsOfExperience)){
            Toast.makeText(this, "فضلأ أدخل عدد سنوات خبرته", Toast.LENGTH_LONG).show();return;}



        if(TextUtils.isEmpty(priceString)){
            Toast.makeText(this, "فضلاً أدخل سعر الدرس", Toast.LENGTH_LONG).show();return;}

// if validations are ok we register user
        intYearsOfExperience=Integer.parseInt(yearsOfExperience);
        longInstructorsPhoneNum=Long.parseLong(instructorsPhoneNum);
        price=Double.parseDouble(priceString);
        progressDialog.setMessage("تسجيل الحساب....الرجاء الانتظار");
        firebaseAuth.createUserWithEmailAndPassword(instructorEmail,instructorPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    // this variable contains the specialties chosen by the instructor as a string EX:Arablic,English
                    // Toast.makeText(SignUpInstructorActivity.this, "email "+ instructorEmail, Toast.LENGTH_LONG).show();
                    String userID = firebaseAuth.getCurrentUser().getUid();
                   Instructor instructor = new Instructor(firstName,lastName,date,gender,encryptedLocation,longInstructorsPhoneNum, intYearsOfExperience,price,0,0,0 ,chosenPaymentMethod, chosenPlace , chosenMethod , instructorEmail, chosen , userID );
                    //Toast.makeText(SignUpInstructorActivity.this, "chosen "+ instructor.subjects.get(0), Toast.LENGTH_LONG).show();

                            /* Intent intent = new Intent(SignUpInstructorActivity.this,ViewInstructorProfile.class);
                            intent.putExtra("instructorsFName",firstName);*/

                    // String id = databaseReference.push().getKey();
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(instructor);
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("subjects").setValue(chosen);
                    Toast.makeText(InstructorActivity.this, "تمت إضافة الأستاذ بنجاح", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference("messages").push().setValue(new Message( "أستاذ جديد", "أستاذ جديد انضم لنا..قد تكون مهتماً بالمواد التي يدرسها ويسكن بالقرب منك .." ,instructorEmail,encryptedLocation,firstName+" "+lastName,userID));
                    final FirebaseUser user = firebaseAuth.getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(InstructorActivity.this, "تم  ارسال ايميل تفعيل الى حسابه", Toast.LENGTH_SHORT).show();
                        }
                    });

                    String current_user_id = firebaseAuth.getCurrentUser().getUid();
                    HashMap postsMap = new HashMap();
                    postsMap.put("fullname",firstName+" "+lastName);
                    FirebaseDatabase.getInstance().getReference().child("users").child(current_user_id).updateChildren(postsMap);
                    startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                }

                else {progressDialog.dismiss();
                    Toast.makeText(InstructorActivity.this, "لقد فشلت إضافة الأستاذ"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }//else
            }//oncomplete
        });




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
    public void onNothingSelected(AdapterView<?> parent) {

    }
}






