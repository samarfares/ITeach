package com.example.tcc.iteach;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tomer.fadingtextview.FadingTextView;

import java.util.Timer;
import java.util.TimerTask;

public class instructor_main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Button findInstructor ;
    FirebaseAuth firebaseAuth;

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    ViewPager viewPagerQuote;
    FirebaseUser user;
    TextView verified;
    DatabaseReference databaseReference;
    FadingTextView fadingTextView;
    String[] top3;
String name , email , test,id;
int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_main);
        i=0;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fadingTextView = (FadingTextView) findViewById(R.id.top3text) ;
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        fadingTextView.setTimeout(FadingTextView.SECONDS,2);
        top3 = new String[3];

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        verified = (TextView)  header.findViewById(R.id.textView);
        if(user.isEmailVerified()){
            verified.setText("الحساب مفعل");}
        else {
            verified.setText("الحساب غير مفعل");  }
        ///////////////////////////// top3
        final FadingTextView fadingTextView = (FadingTextView) findViewById(R.id.top3text);
        fadingTextView.setTimeout(FadingTextView.SECONDS,2);

        //////////////////////////// slide show
        viewPagerQuote = (ViewPager) findViewById(R.id.viewPagerQuote);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPagerQuote.setAdapter(viewPagerAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        findInstructor = (Button) findViewById(R.id.findInstructor2);
        findInstructor.setOnClickListener((View.OnClickListener) this);

        firebaseAuth=FirebaseAuth.getInstance();

        databaseReference= FirebaseDatabase.getInstance().getReference("Instructors");
        databaseReference.orderByChild("likes").limitToLast(3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i=0;
                for (DataSnapshot snap : dataSnapshot.getChildren()){
                    name = snap.child("firstName").getValue().toString()+" "+snap.child("lastName").getValue().toString();
                    email = snap.child("email").getValue().toString();
                    test = SignUpInstructorActivity.decryptIt(  snap.child("location").getValue().toString());
                    id= snap.child("userID").getValue().toString();
                    fadingTextView.setText(name);
                    top3[i]=name;
                    i++;
                }
                fadingTextView.setTexts(top3);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(instructor_main.this,SearchForInstructorActivity.class);
        intent.putExtra( "person","instructor" );
        startActivity(intent);
    }
    public class MyTimerTask extends TimerTask{


        @Override
        public void run() {
            instructor_main.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPagerQuote.getCurrentItem()== 0){
                        viewPagerQuote.setCurrentItem(1);
                    } else if (viewPagerQuote.getCurrentItem()== 1){
                        viewPagerQuote.setCurrentItem(2);
                    } else if (viewPagerQuote.getCurrentItem()== 2){
                        viewPagerQuote.setCurrentItem(3);
                    } else if (viewPagerQuote.getCurrentItem()== 3) {
                        viewPagerQuote.setCurrentItem(4);
                    } else if (viewPagerQuote.getCurrentItem()== 4){
                        viewPagerQuote.setCurrentItem(5);
                    } else viewPagerQuote.setCurrentItem(0);
                }});
        }
    }
    ///////////////////////////end slide show



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.instructor_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
  /*      if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent h= new Intent(instructor_main.this,instructor_main.class);
            startActivity(h);
        } else if (id == R.id.nav_blackboard) {
            Intent h= new Intent(instructor_main.this,blackboard.class);
            startActivity(h);
        } else if (id == R.id.nav_notifications) {
            Intent h= new Intent(instructor_main.this,notifications.class);
            startActivity(h);
        } else if (id == R.id.nav_manage) {
            Intent h= new Intent(instructor_main.this,settings.class);
            startActivity(h);
        } else if (id == R.id.nav_schedule) {
            Intent h= new Intent(instructor_main.this,schedule.class);
            startActivity(h);
        } else if (id == R.id.nav_reservations) {
            Intent h= new Intent(instructor_main.this,reservations.class);
            startActivity(h);
        }
        else if (id==R.id.nav_signOut){
            firebaseAuth.signOut();
            Intent b = new Intent(this, MainActivity.class);
            b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(b);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

