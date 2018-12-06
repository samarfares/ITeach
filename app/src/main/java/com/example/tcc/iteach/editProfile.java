package com.example.tcc.iteach;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class editProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
TextView editTextEmailInstructor2,editTextPasswordInstructor2,editTextNumberInstructor2,editTextFirstName2,editTextLastName2,
        textViewSpecialty2,lessonsPrice2,yearsExperience2,textViewBrowse;
Spinner payment2,place2,method2;
Button buttonBrowse22,buttonRegister3;
MultiSelectionSpinner input12;
RadioGroup radioGroupGender22;
RadioButton male3,female3;
DatePicker datePicker2;
    private DatabaseReference databaseReference3,databaseReference33;
    String currentUserId3;
    private FirebaseAuth mAuth3;
    String firstName3,lastName3,birth3,email3,gender3,subject3,lessonPl3,lessonPri3,teachingMeth3,paymentMeth3,phoneNumber3,yoe3;
    Button save2;
    String  instructorEmail, instructorPassword , firstName, lastName  , gender, date , yearsOfExperience , instructorsPhoneNum , chosenString, priceString ,   chosenPaymentMethod,chosenPlace , chosenMethod , insId;
    long longInstructorsPhoneNum;
    int intYearsOfExperience;
    double price;
    List<String> chosen = new ArrayList<String>();
    List<String> subjects4 = new ArrayList<String>();
    SpinnerAdapter adapter;
    String[] title;
    Uri pdfUri;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth3 = FirebaseAuth.getInstance();
        currentUserId3 = mAuth3.getCurrentUser().getUid();
        databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Instructors").child(currentUserId3);
        databaseReference33 = FirebaseDatabase.getInstance().getReference().child("Instructors").child(currentUserId3);
        storage= FirebaseStorage.getInstance();
        datePicker2 = (DatePicker) findViewById(R.id.datePicker2);
        editTextFirstName2 = (TextView) findViewById(R.id.editTextFirstName2);
        editTextLastName2 = (TextView) findViewById(R.id.editTextLastName2);
        textViewBrowse = (TextView) findViewById(R.id.textViewBrowse);
        adapter= new SpinnerAdapter(this);
buttonBrowse22 =  (Button) findViewById(R.id.buttonBrowse22);

        editTextEmailInstructor2 = (TextView) findViewById(R.id.editTextEmailInstructor2);
        radioGroupGender22 = (RadioGroup) findViewById(R.id.radioGroupGender22);
        male3 = (RadioButton) findViewById(R.id.male3);
        female3 = (RadioButton) findViewById(R.id.female3);
        input12 = (MultiSelectionSpinner) findViewById(R.id.input12);
       // input12.setAdapter(adapter);
        place2 = (Spinner) findViewById(R.id.place2);
        lessonsPrice2 = (TextView) findViewById(R.id.lessonsPrice2);
        method2 = (Spinner) findViewById(R.id.method2);
        payment2 = (Spinner) findViewById(R.id.payment2);
        editTextNumberInstructor2 = (TextView) findViewById(R.id.editTextNumberInstructor2);
        yearsExperience2 = (TextView) findViewById(R.id.yearsExperience2);
        save2 = (Button) findViewById(R.id.buttonRegister3);

        ArrayAdapter<CharSequence> paymentAdapter = ArrayAdapter.createFromResource(this ,R.array.paymentMethod,android.R.layout.simple_spinner_item);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payment2.setAdapter(paymentAdapter);
        //setContentView(R.layout.activity_sign_up_instructor);
        payment2.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> placeAdapter = ArrayAdapter.createFromResource(this,R.array.lessonsPlace,android.R.layout.simple_spinner_item);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place2.setAdapter(placeAdapter);
        place2.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> methodAdapter = ArrayAdapter.createFromResource(this,R.array.method,android.R.layout.simple_spinner_item);
        methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        method2.setAdapter(methodAdapter);
        method2.setOnItemSelectedListener(this);
        final List<String> list = new ArrayList<String>();
      //  final String[]s0 = {"","الانكليزية","لعربية"};
       /* final String[]s1 = {"لعربية"};
        final String[]s2 = {"الانكليزية"};
        final String[]s3 = {"الرياضيات"};
        final String[]s4 = {"الكيمياء"};
        final String[]s5 = {"الفيزياء"};
        final String[]s6 = {"الموسيقى"};
        final String[]s7 = {"الرقص"};
        final String[]s8 = {"الرسم"};
        final String[]s9 = {"الطبخ"}; */
      list.add("");
        list.add("العربية");
        list.add("الانكليزية");
        list.add("الرياضيات");
        list.add("الكيمياء");
        list.add("الفيزياء");
        list.add("الموسيقى");
        list.add("الرقص");
        list.add("الرسم");
        list.add("الطبخ");
        input12.setItems(list);
     //   input12.setItems(s0);
     /*   input12.setItems(s1);
        input12.setItems(s2);
        input12.setItems(s3);
        input12.setItems(s4);
        input12.setItems(s5);
        input12.setItems(s6);
        input12.setItems(s7);
        input12.setItems(s8);
        input12.setItems(s9); */
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Instructor instructor = dataSnapshot.getValue(Instructor.class);
                    firstName3 = dataSnapshot.child("firstName").getValue().toString();
                    lastName3 = dataSnapshot.child("lastName").getValue().toString();
                    birth3 = dataSnapshot.child("dob").getValue().toString();
                   // Log.d("Value of date: " , birth3);
                    String day = birth3.substring(0,2);
                    String month = birth3.substring(3,5);
                    String year = birth3.substring(6,10);
                    int mday=Integer.valueOf(day);
                    int mmonth=Integer.valueOf(month);
                    int myear=Integer.valueOf(year);
                    datePicker2.updateDate(myear,mmonth,mday);
                    email3 = dataSnapshot.child("email").getValue().toString();
                    gender3 = dataSnapshot.child("gender").getValue().toString();
                    if(gender3.equals("أنثى")){
                        female3.setChecked(true);
                    }
                    else {
                        male3.setChecked(true);
                    }

                    subject3 = dataSnapshot.child("subjects").getValue().toString();
                /*    for(int i=0;i<3;i++){
                        for(int j=0;j<11;j++){
                           if(instructor.subjects.contains(input12.getItemAtPosition(j)))
                               input12.setSelection(j);

                        }
                    } */
                 String s12= String.valueOf(list.size());
                 Log.d("list",s12);
                    String s11= String.valueOf(input12.getChildCount());
                    Log.d("input",s11);

                    lessonPl3 = dataSnapshot.child("lessonsPlace").getValue().toString();
                    for(int i=0;i<3;i++){
                        if(place2.getItemAtPosition(i).equals(lessonPl3))
                            place2.setSelection(i);
                    }
                    lessonPri3 = dataSnapshot.child("lessonsPrice").getValue().toString();
                    teachingMeth3 = dataSnapshot.child("teachingMethod").getValue().toString();
                    for(int i=0;i<3;i++){
                        if(method2.getItemAtPosition(i).equals(teachingMeth3))
                            method2.setSelection(i);
                    }
                    paymentMeth3 = dataSnapshot.child("paymentMethod").getValue().toString();
                    for(int i=0;i<3;i++){
                        if(payment2.getItemAtPosition(i).equals(paymentMeth3))
                            payment2.setSelection(i);
                    }
                    phoneNumber3 = dataSnapshot.child("phoneNum").getValue().toString();
                    yoe3 = dataSnapshot.child("yoe").getValue().toString();
                    //   likes1 = dataSnapshot.child("likes").getValue().toString();
                    //     location1 = dataSnapshot.child("location").getValue().toString();
                   editTextFirstName2.setText(firstName3);
                    editTextLastName2.setText(lastName3);
                  //  datePicker2.setText(birth3);
                    editTextEmailInstructor2.setText(email3);
                   // Gender1.setText(gender3);
                    // subjects3.setText(subject3);
                 //   lessonPlace.setText(lessonPl3);
                    lessonsPrice2.setText(lessonPri3);
                 //   teachingMethod.setText(teachingMeth3);
                  //  paymentMethod1.setText(paymentMeth3);
                    editTextNumberInstructor2.setText(phoneNumber3);
                    yearsExperience2.setText(yoe3);
                }
                else {
                  //  Toast.makeText(editProfile.this, "لقد فشل جلب المعلومات, ", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       // likes = (TextView) findViewById(R.id.InstructorLikes);
        buttonBrowse22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();
            }
        });


save2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        instructorEmail = editTextEmailInstructor2.getText().toString();
        //instructorPassword = editTextPasswordInstructor.getText().toString();

        firstName= editTextFirstName2.getText().toString();
        lastName= editTextLastName2.getText().toString();
        yearsOfExperience = yearsExperience2.getText().toString();
        instructorsPhoneNum = editTextNumberInstructor2.getText().toString();
        date = datePicker2.getDayOfMonth()+"/"+datePicker2.getMonth()+"/"+datePicker2.getYear();
        priceString = lessonsPrice2.getText().toString();
        // To know if user selects male or female
        int radioId= radioGroupGender22.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);
        if(radioButton!=null)
            gender = radioButton.getText().toString();
        chosen=input12.getSelectedStrings();
        chosen=input12.getSelectedStrings();

        // Toast.makeText(SignUpInstructorActivity.this,chosen.get( 0 ), Toast.LENGTH_SHORT).show();


        //chosenString= input12.getSelectedItemsAsString(); // this variable contains the specialties chosen by the instructor as a string EX:Arablic,English
        //instructor = new Instructor(firstName,lastName,date,gender,encryptedLocation,longInstructorsPhoneNum, intYearsOfExperience,price,0 , chosenPaymentMethod, chosenPlace , chosenMethod );


        if(!(TextUtils.isEmpty(instructorEmail))){
            // email is empty!
databaseReference3.child("email").setValue(instructorEmail);
        }

        if(!(TextUtils.isEmpty(instructorsPhoneNum))){
            if(instructorsPhoneNum.length()<= 10 ){
                Toast.makeText(editProfile.this, "رقم الهاتف يجب أن يكون من 10 أرقام ويبدأ ب 05", Toast.LENGTH_LONG).show();
                return ;
            }
            else{
            longInstructorsPhoneNum=Long.parseLong(instructorsPhoneNum);
            databaseReference3.child("phoneNum").setValue(longInstructorsPhoneNum);
        }}

        if(!(TextUtils.isEmpty(firstName))){
            databaseReference3.child("firstName").setValue(firstName);
        }

        if(!(TextUtils.isEmpty(lastName))){
            databaseReference3.child("lastName").setValue(lastName);

        }

        if(!(TextUtils.isEmpty(date))){
            databaseReference3.child("dob").setValue(date);

        }

        if(datePicker2.getYear()>2000){
            Toast.makeText(editProfile.this, "عذراً غير مسموح لك التسجيل بهذا العمر", Toast.LENGTH_LONG).show();
return;
        }

        if(!(TextUtils.isEmpty(gender))){
            databaseReference3.child("gender").setValue(gender);

        }

        if(!(TextUtils.isEmpty(yearsOfExperience))){
            intYearsOfExperience=Integer.parseInt(yearsOfExperience);
            databaseReference3.child("yoe").setValue(intYearsOfExperience);

        }

        if(!(TextUtils.isEmpty(priceString))){
            price=Double.parseDouble(priceString);
            databaseReference3.child("lessonsPrice").setValue(price);

        }
        if(!(chosen.equals(""))){
            databaseReference3.child("subjects").setValue(chosen);
            databaseReference3.child("subjects").setValue(chosen);
        }

            databaseReference3.child("paymentMethod").setValue(chosenPaymentMethod);



            databaseReference3.child("lessonsPlace").setValue(chosenPlace);


            databaseReference3.child("teachingMethod").setValue(chosenMethod);

        storageReference=storage.getReference();
        if(!(pdfUri==null)){
            storageReference.child(currentUserId3).putFile(pdfUri);


            String url = storageReference.getDownloadUrl().toString();
            databaseReference3.child("Cv").setValue(url);

        }



        Toast.makeText(editProfile.this, "تم التعديل بنجاح", Toast.LENGTH_LONG).show();

        Intent h= new Intent(editProfile.this,settings.class);
        startActivity(h);

      /*  if(pdfUri==null){

            return ;
        }  */
// if validations are ok we register user

    }
});

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==9 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }
        else
            Toast.makeText(editProfile.this,"الرجاء السماح بالوصول لملفاتك" , Toast.LENGTH_LONG).show();
    }
    private void selectPdf() {

        Intent intent2 = new Intent();
        intent2.setType("application/pdf");
        intent2.setAction(Intent.ACTION_GET_CONTENT);
        //pdfUri=intent2.getData();
        // Toast.makeText(SignUpInstructorActivity.this,"ameera" , Toast.LENGTH_LONG).show();

        startActivityForResult(intent2,86);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==86 && resultCode==RESULT_OK && data!=null){
            pdfUri=data.getData();
            textViewBrowse.setText(" تم اختيار الملف "+ data.getData().getLastPathSegment());
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent==payment2)
            chosenPaymentMethod = parent.getItemAtPosition(position).toString();


        if(parent==place2)
            chosenPlace= parent.getItemAtPosition(position).toString();

        if(parent==method2)
            chosenMethod= parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class SpinnerAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater mInflater;

        public SpinnerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Advanced.ListContent holder;
            View v = convertView;
            if (v == null) {
                mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                v = mInflater.inflate(R.layout.row_edittext, null);
                holder = new Advanced.ListContent();
                holder.text = (TextView) v.findViewById(R.id.textView1);

                v.setTag(holder);
            } else {
                holder = (Advanced.ListContent) v.getTag();
            }

            holder.text.setText(title[position]);

            return v;
        }
    }
}
