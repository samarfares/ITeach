package com.example.tcc.iteach;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class askActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    private Button publishButton;
    private EditText questionText;
    String Description;
    private DatabaseReference UsersRef, PostsRef;
    private FirebaseAuth mAuth;

    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl, current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();

        publishButton = (Button) findViewById(R.id.publish);
        questionText = (EditText) findViewById(R.id.questionText);
        // subjectSpinner = (Spinner) findViewById(R.id.selectSubject);
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        publishButton.setOnClickListener(this);
    }

    private void ValidatePostInfo() {
        Description = questionText.getText().toString();


        if (TextUtils.isEmpty(Description)) {
            Toast.makeText(this, "Please type your question...", Toast.LENGTH_SHORT).show();
        } else {
            //  loadingBar.setTitle("Add New Post");
            // loadingBar.setMessage("Please wait, while we are updating your new post...");
            // loadingBar.show();
            // loadingBar.setCanceledOnTouchOutside(true);

            //  StoringImageToFirebaseStorage();
            SavingPostInformationToDatabase();
        }
    }


    private void SavingPostInformationToDatabase() {

        ArrayAdapter<CharSequence> specialtyAdapter = ArrayAdapter.createFromResource(this, R.array.specialty, android.R.layout.simple_spinner_item);
        specialtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // subjectSpinner.setAdapter(specialtyAdapter);
        //  subjectSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        UsersRef.child(current_user_id).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userFullName = dataSnapshot.child("fullname").getValue().toString();
                    //  String userProfileImage = dataSnapshot.child("profileimage").getValue().toString();
                    System.out.print("after listening");
                    Calendar calFordDate = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                    saveCurrentDate = currentDate.format(calFordDate.getTime());

                    Calendar calFordTime = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                    saveCurrentTime = currentTime.format(calFordDate.getTime());

                    postRandomName = saveCurrentDate + saveCurrentTime;

                    HashMap postsMap = new HashMap();
                    postsMap.put("uid", current_user_id);
                    postsMap.put("date", saveCurrentDate);
                    postsMap.put("time", saveCurrentTime);
                    postsMap.put("description", Description);
                    // postsMap.put("postimage", downloadUrl);
                    // postsMap.put("profileimage", userProfileImage);

                    postsMap.put("fullname", userFullName);
                    PostsRef.child(current_user_id + postRandomName).updateChildren(postsMap)
                            .addOnCompleteListener(new OnCompleteListener() {

                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        //  SendUserToMainActivity();
                                        Toast.makeText(askActivity.this, "New Post is updated successfully.", Toast.LENGTH_SHORT).show();
                                        //  loadingBar.dismiss();
                                    } else {
                                        Toast.makeText(askActivity.this, "Error Occured while updating your post.", Toast.LENGTH_SHORT).show();
                                        // loadingBar.dismiss();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print("failed to post ");
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String chosenPaymentMethod, chosenSpecialty, chosenPlace;

        //  if (view == subjectSpinner)
        //    chosenSpecialty = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View v) {
        if(v == publishButton)
            ValidatePostInfo();
        startActivity(new Intent(this, blackboard.class));
    }
}
