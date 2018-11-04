package com.example.tcc.iteach;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SubjectActivity extends AppCompatActivity {

    String[] title;
    ListView listView;
    private Button addNew;
    private Button add;
    private EditText editText;
    private Button buttonLogout;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_subject );


        firebaseAuth = FirebaseAuth.getInstance();

        title = getResources().getStringArray(R.array.specialty);
        listView=(ListView) findViewById( R.id.list1 );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, title);
        listView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(listView);

        editText=(EditText) findViewById( R.id.subject );
        editText.setVisibility( View.GONE );

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    firebaseAuth.
                             signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        addNew=(Button) findViewById(R.id.addNew);
        addNew.setVisibility( View.GONE );

        add=(Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setVisibility( View.VISIBLE );

                addNew.setVisibility( View.VISIBLE );
                listView.setVisibility( View.GONE );
                add.setVisibility( View.GONE );



            }
        });



    }


    public void uploadData(View view){
        String subject=editText.getText().toString();
        if(subject== null ){
            // email is empty!
            Toast.makeText(this, "فضلاً أدخل اسم المادة", Toast.LENGTH_LONG).show();return;}


    }

}
