package com.example.tcc.iteach;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    DatabaseReference databaseReference2 , likesRef ;
   List<Instructor> list = new ArrayList<>();

    AdapterInstructor adapterInstructor;
ImageButton buttonLike ;
Button buttonReserve;
String email , ID;
boolean likeChecker= false;
FirebaseUser firebaseUser;
int countLikes;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_instructor_profile);
listViewInstructorProfile = (ListView) findViewById(R.id.listViewInstructorProfile);
textViewInstructorProfile= (TextView)findViewById(R.id.textViewInstructorProfile);
    textViewRate= (TextView)findViewById(R.id.textViewRate);
    buttonReserve=(Button) findViewById(R.id.buttonReserve);
            buttonLike=(ImageButton) findViewById(R.id.buttonLike);


    likesRef=FirebaseDatabase.getInstance().getReference().child("InstructorsLikes");

    databaseReference= FirebaseDatabase.getInstance().getReference("Instructors");
//    databaseReference2= FirebaseDatabase.getInstance().getReference("Instructors").child(list.get(0).getUserID());

    buttonLike.setOnClickListener(this);
buttonReserve.setOnClickListener(this);

databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        for (DataSnapshot snap : dataSnapshot.getChildren()){

            ID= getIntent().getExtras().getString("insId");
            if (snap.child("userID").getValue().toString().equals(ID))
                setLikeButtonStatus(ID);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

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
  //ins.likeInstructor();
   final String likedInsId = ins.getUserID();
   final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //databaseReference2.setValue(ins);
likeChecker=true;

likesRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

       if(likeChecker){
           if (dataSnapshot.child(likedInsId).hasChild(currentUser)){
               likesRef.child(likedInsId).child(currentUser).removeValue();
               likeChecker=false;

           }
           else { likesRef.child(likedInsId).child(currentUser).setValue(true);
               likeChecker=false;}
       }

        }


    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }});

setLikeButtonStatus(likedInsId);

}


public void setLikeButtonStatus (final String likedInsId){
likesRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
if (dataSnapshot.child(likedInsId).hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
    countLikes=(int) dataSnapshot.child(likedInsId).getChildrenCount();
buttonLike.setImageResource(R.drawable.like);
textViewRate.setText(((Integer.toString(countLikes)+" likes")));
    databaseReference2.child("likes").setValue(countLikes);
}

else{
    countLikes=(int) dataSnapshot.child(likedInsId).getChildrenCount();
    buttonLike.setImageResource(R.drawable.dislike);
    textViewRate.setText(((Integer.toString(countLikes)+" likes")));
//    databaseReference2.child("likes").setValue(countLikes);


}
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

}
    @Override
    public void onClick(View v) {
        if (v==buttonLike){
            likeInstructor(list.get(0));
           // Toast.makeText(ViewInstructorProfile.this, "likes "+ liked.getLikes(), Toast.LENGTH_LONG).show();
        }

        if (v==buttonReserve){
            Intent intent = new Intent(ViewInstructorProfile.this,ReserveInfo.class);
            intent.putExtra("insID",list.get(0).getUserID());
            intent.putExtra("insName",list.get(0).getFirstName()+ " " + list.get(0).getLastName());
            intent.putExtra("insGender",list.get(0).getGender());
            intent.putExtra("insEmail",list.get(0).getEmail());
            intent.putExtra("insPhoneNum",list.get(0).getPhoneNum());
            intent.putExtra("insLikes",list.get(0).getLikes());
            intent.putExtra("insPaymentMethod",list.get(0).getPaymentMethod());
            intent.putExtra("insLessonsPlace",list.get(0).getLessonsPlace());
            intent.putExtra("insLessonsPrice",list.get(0).getLessonsPrice());
            intent.putExtra("insTeachingMethod",list.get(0).getTeachingMethod());
            intent.putExtra("insYOE",list.get(0).getYoe());
            intent.putExtra("insDOB",list.get(0).getDob());
            intent.putExtra("insLocation",list.get(0).getLocation());
            startActivity(intent);
        }
    }
}