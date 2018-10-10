package com.example.tcc.iteach;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Button;

import com.tomer.fadingtextview.FadingTextView;

import java.util.Timer;
import java.util.TimerTask;

public class student_main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Button findInstructor ;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    ViewPager viewPagerQuote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ///////////////////////////// top3
        FadingTextView fadingTextView = (FadingTextView) findViewById(R.id.top3text);
        fadingTextView.setTimeout(FadingTextView.SECONDS,2);

        //////////////////////////// slide show
        viewPagerQuote = (ViewPager) findViewById(R.id.viewPagerQuote);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPagerQuote.setAdapter(viewPagerAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        //////////////////////////
        findInstructor = (Button) findViewById(R.id.findInstructor);
        findInstructor.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(student_main.this,SearchForInstructorActivity.class));
    }

    public class MyTimerTask extends TimerTask{


        @Override
        public void run() {
            student_main.this.runOnUiThread(new Runnable() {
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
        getMenuInflater().inflate(R.menu.student_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent h= new Intent(student_main.this,student_main.class);
            startActivity(h);
        } else if (id == R.id.nav_blackboard) {
            Intent h= new Intent(student_main.this,blackboard2.class);
            startActivity(h);
        } else if (id == R.id.nav_notifications) {
            Intent h= new Intent(student_main.this,notifications2.class);
            startActivity(h);
        } else if (id == R.id.nav_manage) {
            Intent h= new Intent(student_main.this,settings2.class);
            startActivity(h);
        } else if (id == R.id.nav_reservations) {
            Intent h= new Intent(student_main.this,reservations2.class);
            startActivity(h);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}