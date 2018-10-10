package com.example.tcc.iteach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewInstructorProfile extends AppCompatActivity implements View.OnClickListener {

    ListView listViewInstructorProfile ;
    TextView textViewInstructorProfile , textViewRate;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2 ;
   List<Instructor> list = new ArrayList<>();
AdapterInstructor adapterInstructor;
ImageButton buttonLike , buttonDisLike, buttonNeutral;
Button buttonReserve;
String email;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_instructor_profile);
listViewInstructorProfile = (ListView) findViewById(R.id.listViewInstructorProfile);
textViewInstructorProfile= (TextView)findViewById(R.id.textViewInstructorProfile);
    textViewRate= (TextView)findViewById(R.id.textViewRate);
    buttonReserve=(Button) findViewById(R.id.buttonReserve);
            buttonLike=(ImageButton) findViewById(R.id.buttonLike);
    buttonDisLike=(ImageButton) findViewById(R.id.buttonDisLike);
    buttonNeutral=(ImageButton) findViewById(R.id.buttonNeutral);


    databaseReference= FirebaseDatabase.getInstance().getReference("Instructors");
    buttonLike.setOnClickListener(this);
    buttonDisLike.setOnClickListener(this);
    buttonNeutral.setOnClickListener(this);
buttonReserve.setOnClickListener(this);
}


    @Override
    protected void onStart() {
        super.onStart();
        //String name = getIntent().getExtras().getString("name");

        //textViewInstructorProfile.setText(name);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                 email = getIntent().getExtras().getString("email");

                for (DataSnapshot snap : dataSnapshot.getChildren() ) {
                    Instructor ins = snap.getValue(Instructor.class);
                    if (snap.child("email").getValue().toString().equals(email))
                        list.add(ins);
                }



                adapterInstructor = new AdapterInstructor (ViewInstructorProfile.this,list);
                listViewInstructorProfile.setAdapter(adapterInstructor);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void likeInstructor(Instructor ins){
  ins.likeInstructor();
    String likedInsId = ins.getUserID();
    databaseReference2= FirebaseDatabase.getInstance().getReference("Instructors").child(likedInsId);
        databaseReference2.setValue(ins);
buttonLike.setVisibility(View.INVISIBLE);
buttonDisLike.setVisibility(View.INVISIBLE);
buttonNeutral.setVisibility(View.INVISIBLE);
textViewRate.setVisibility(View.INVISIBLE);
Toast.makeText(ViewInstructorProfile.this,"Thank you for rating!"  , Toast.LENGTH_LONG).show();


}

    private void dislikeInstructor(Instructor ins) {
        ins.dislikeInstructor();
        String dislikedInsId = ins.getUserID();
        databaseReference2= FirebaseDatabase.getInstance().getReference("Instructors").child(dislikedInsId);
        databaseReference2.setValue(ins);
        buttonLike.setVisibility(View.INVISIBLE);
        buttonDisLike.setVisibility(View.INVISIBLE);
        buttonNeutral.setVisibility(View.INVISIBLE);
        textViewRate.setVisibility(View.INVISIBLE);
        Toast.makeText(ViewInstructorProfile.this,"Thank you for rating!"  , Toast.LENGTH_LONG).show();
    }
    private void neutralizeInstructor(Instructor ins) {
        ins.neutralizeInstructor();
        String neutralizedInsId = ins.getUserID();
        databaseReference2= FirebaseDatabase.getInstance().getReference("Instructors").child(neutralizedInsId);
        databaseReference2.setValue(ins);
        buttonLike.setVisibility(View.INVISIBLE);
        buttonDisLike.setVisibility(View.INVISIBLE);
        buttonNeutral.setVisibility(View.INVISIBLE);
        textViewRate.setVisibility(View.INVISIBLE);
        Toast.makeText(ViewInstructorProfile.this,"Thank you for rating!"  , Toast.LENGTH_LONG).show();

    }


    @Override
    public void onClick(View v) {
        if (v==buttonLike){
            likeInstructor(list.get(0));
           // Toast.makeText(ViewInstructorProfile.this, "likes "+ liked.getLikes(), Toast.LENGTH_LONG).show();
        }
        if (v==buttonDisLike){

dislikeInstructor(list.get(0));

        }
        if (v==buttonNeutral){
neutralizeInstructor(list.get(0));


        }
        if (v==buttonReserve){
            Intent intent = new Intent(ViewInstructorProfile.this,ReserveInfo.class);
            intent.putExtra("insID",list.get(0).getUserID());
            intent.putExtra("insName",list.get(0).getFirstName()+list.get(0).getLastName());
            intent.putExtra("insGender",list.get(0).getGender());
            intent.putExtra("insEmail",list.get(0).getEmail());
            intent.putExtra("insPhoneNum",list.get(0).getPhoneNum());
            intent.putExtra("insLikes",list.get(0).getLikes());
            intent.putExtra("insDislikes",list.get(0).getDislikes());
            intent.putExtra("insNeutral",list.get(0).getNeutral());
            intent.putExtra("insPaymentMethod",list.get(0).getPaymentMethod());
            intent.putExtra("insLessonsPlace",list.get(0).getLessonsPlace());
            intent.putExtra("insLessonsPrice",list.get(0).getLessonsPrice());
            intent.putExtra("insTeachingMethod",list.get(0).getTeachingMethod());
            intent.putExtra("insYOE",list.get(0).getYOE());
            intent.putExtra("insDOB",list.get(0).getDOB());
            intent.putExtra("insLocation",list.get(0).getLocation());
            startActivity(intent);

        }
    }




}