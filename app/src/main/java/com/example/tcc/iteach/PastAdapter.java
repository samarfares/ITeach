package com.example.tcc.iteach;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PastAdapter extends ArrayAdapter<String> {


    private Context context;

    TextView feedbackText;
    Button saveFeedback;
    Dialog dialog;


    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    ArrayList<String> list;
    ArrayList<String> keyList;


    Lesson lesson;
    String currentDateString, uID;
    int i;
    int j;



    public PastAdapter(Context context, ArrayList<String> records) {
        super(context, 0, records);

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        String item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.past_content, parent, false);
        }

        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        uID =  firebaseUser.getUid();
        lesson = new Lesson();
        DateFormat format = new SimpleDateFormat("EEE, d MMM yyyy");
        currentDateString = format.format(Calendar.getInstance().getTime());

        databaseReference = FirebaseDatabase.getInstance().getReference("Lessons");
        list = new ArrayList<>();
        keyList = new ArrayList<>();

        ///////////////////////////

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    lesson = ds.getValue(Lesson.class);
                    try {
                        DateFormat format = new SimpleDateFormat("EEE, d MMM yyyy");
                        Date date1 = format.parse(currentDateString);
                        Date date2 = format.parse(lesson.getDate());
                        i = date1.compareTo(date2);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                    if (lesson.getInstructorID().equals(uID)) {
                        if (i >= 0) {
                            if (i == 0) {
                                int t = Integer.parseInt(lesson.getTime().substring(0, lesson.getTime().indexOf(":")));
                                Calendar rightNow = Calendar.getInstance();
                                int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                if (t < currentHourIn24Format) {

                        /*FirebaseDatabase.getInstance().getReference("Students").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    student = ds.getValue(Person.class);
                                    if (ds.getKey().equals(lesson.getStudentID())){
                                        stuName = student.getFirstName() +" " + student . getLastName();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
*/
                                    list.add("التاريخ : " + lesson.getDate() + "\n" + "الوقت : " + lesson.getTime() + "\n" + "المادة : " + lesson.getSubject() + "\n" + "السعر : " + lesson.getPrice() + "\n" + "طريقة الدفع : " + lesson.getPaymentMethod() + "\n" + "مكان الدرس : " + lesson.getLessonPlace() + "\n" + "طريقة التدريس : " + lesson.getTeachingMethod() + "\n" + "ملاحظاتك : " + lesson.getFeedback());
                                    keyList.add(ds.getKey());

                                }
                            } else {
                                keyList.add(ds.getKey());
                                list.add("التاريخ : " + lesson.getDate() + "\n" + "الوقت : " + lesson.getTime() + "\n" + "المادة : " + lesson.getSubject() + "\n" + "السعر : " + lesson.getPrice() + "\n" + "طريقة الدفع : " + lesson.getPaymentMethod() + "\n" + "مكان الدرس : " + lesson.getLessonPlace() + "\n" + "طريقة التدريس : " + lesson.getTeachingMethod() + "\n" + "ملاحظاتك : " + lesson.getFeedback());
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ///////////////////////////


        final TextView lessonTextView = (TextView) convertView.findViewById(R.id.lesson3);

        final Button feedback = (Button) convertView.findViewById(R.id.feedback);
        feedback.setTag(position);

        lessonTextView.setText(item);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getContext());
                dialog.setTitle("أدخل ملاحظاتك على هذا الدرس");
                dialog.setContentView(R.layout.dilaog_template2);
                feedbackText = (EditText) dialog.findViewById(R.id.editTextFeedback);
                saveFeedback = (Button) dialog.findViewById(R.id.saveFeedback);
                feedbackText.setEnabled(true);
                saveFeedback.setEnabled(true);

                saveFeedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPrefesSave(feedbackText.getText().toString());
                        SharedPreferences sp = getContext().getSharedPreferences("members",0);
                        String number = sp.getString("members", null);
                        if (number.equals("")){
                            Toast.makeText(getContext(), "يجب إدخال ملاحظاتك !", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            FirebaseDatabase.getInstance().getReference("Lessons").child(keyList.get(position)).child("feedback").setValue(number);

                            dialog.cancel();
                        }

                    }
                });
                dialog.show();

            }
        });
        return convertView;
    }
    public void SharedPrefesSave (String members){

        SharedPreferences prefs = getContext().getSharedPreferences("members", 0);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("members", members);
        prefEdit.commit();
    }
}