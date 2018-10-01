package com.example.tcc.iteach;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    List<Instructor> list;
    MyAdapterSearch myAdapter;
    EditText text;
    public static final String DATABASE_PATH = "Instructors";
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_search_results );
        list = new ArrayList<>();
        text=(EditText) findViewById( R.id.test );

        databaseReference = FirebaseDatabase.getInstance().getReference("Instructors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){

                    Instructor instructor = snap.getValue(Instructor.class);
                    list.add(instructor);

                }
                for (int i=0;i<list.size();i++)
                {
                    String test=SignUpInstructorActivity.decryptIt( list.get( i ).location );
                    Double lat= Double.valueOf( test.substring( test.indexOf( "(" )+1,test.indexOf( "," ) ) );
                    Double lng= Double.valueOf( test.substring( test.indexOf( "," )+1,test.indexOf( ")" ) ) );
                    LatLng t =new LatLng(lat,lng );
                    LatLng b=new LatLng( 24,46 );
                    Double dis=SearchForInstructorActivity.CalculationByDistance(t,b);
                    text.setText( dis.toString() );

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
