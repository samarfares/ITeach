package com.example.tcc.iteach;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class blackboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private RecyclerView postList;
    private DatabaseReference UsersRef, PostsRef;
    Questions question;
    private Button askButton,options;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

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

        askButton = (Button) findViewById(R.id.askButton);
       // options = (Button) findViewById(R.id.options);
        postList = (RecyclerView) findViewById(R.id.allQuestionList);
        postList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postList.setLayoutManager(linearLayoutManager);
        postList.getLayoutManager().setMeasurementCacheEnabled(false);

        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        // DisplayAllUsersQuestions();
        askButton.setOnClickListener(this);
    }

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
        getMenuInflater().inflate(R.menu.instructor_main, menu);
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
            Intent h= new Intent(blackboard.this,instructor_main.class);
            startActivity(h);
        } else if (id == R.id.nav_blackboard) {
            Intent h= new Intent(blackboard.this,blackboard.class);
            startActivity(h);
        } else if (id == R.id.nav_notifications) {
            Intent h= new Intent(blackboard.this,notifications.class);
            startActivity(h);
        } else if (id == R.id.nav_manage) {
            Intent h= new Intent(blackboard.this,settings.class);
            startActivity(h);
        } else if (id == R.id.nav_schedule) {
            Intent h= new Intent(blackboard.this,schedule.class);
            startActivity(h);
        } else if (id == R.id.nav_reservations) {
            Intent h= new Intent(blackboard.this,reservations.class);
            startActivity(h);
        }
        else if (id==R.id.nav_signOut){
            firebaseAuth.signOut();
            startActivity(new Intent(this, MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Questions> options =
                new FirebaseRecyclerOptions.Builder<Questions>().setQuery(PostsRef, Questions.class).build();

        FirebaseRecyclerAdapter<Questions, PostsViewHolder> adapter =
                new FirebaseRecyclerAdapter<Questions, PostsViewHolder>(options) {
                    @NonNull
                    @Override
                    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_questions_layout, parent,false);
                        PostsViewHolder viewHolder = new PostsViewHolder(view);
                        return  viewHolder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull Questions model) {

                        final String postKey = getRef(position).getKey();
                        holder.setFullname(model.getFullname());
                        holder.setDate(model.getDate());
                        holder.setDescription(model.getDescription());
                        holder.commentsButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent clickAnswersIntent = new Intent(blackboard.this,answerActivity.class);
                                clickAnswersIntent.putExtra("postkey",postKey);
                                startActivity(clickAnswersIntent);
                            }
                        });
                    }

                };

        postList.setAdapter(adapter);
        adapter.startListening();
    }
    /* private void DisplayAllUsersQuestions() {
        Query query = FirebaseDatabase.getInstance().getReference().child("Posts").orderByKey();
        FirebaseRecyclerOptions<Questions> options = new FirebaseRecyclerOptions.Builder<Questions>().setQuery(query, Questions.class).build();
        FirebaseRecyclerAdapter<Questions, PostsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Questions, PostsViewHolder>(options) {


            @NonNull
            @Override
            public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_questions_layout,parent,false);

                return new PostsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull Questions model) {
                final String postKey = getRef(position).getKey();
                holder.setFullname(model.getFullname());
                holder.setDate(model.getDate());
                holder.setDescription(model.getDescription());
                holder.commentsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent clickAnswersIntent = new Intent(blackboard.this,answerActivity.class);
                        clickAnswersIntent.putExtra("postkey",postKey);
                        startActivity(clickAnswersIntent);
                    }
                });
            }


        };
        postList.setAdapter(firebaseRecyclerAdapter);
    }  */

    public static class PostsViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageButton commentsButton;

        public PostsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            commentsButton = (ImageButton) mView.findViewById(R.id.commetsButton);

        }
        public void setFullname(String fullname)
        {
            TextView username = (TextView) mView.findViewById(R.id.userName);
            username.setText(fullname);
        }


        public void setDate(String date)
        {
            TextView PostDate = (TextView) mView.findViewById(R.id.questionDate);
            PostDate.setText(" " + date);
        }
        public void setDescription(String description)
        {
            TextView PostDescription = (TextView) mView.findViewById(R.id.questionDescription);
            PostDescription.setText(description);
        }
    }
   /* private void SendUserToPostActivity()
    {
        Intent addNewPostIntent = new Intent(blackboard.this, askActivity.class);
        startActivity(addNewPostIntent);
    }  */

    @Override
    public void onClick(View v) {
        if(v==askButton ){
            finish();
            Intent addNewPostIntent = new Intent(blackboard.this, askActivity.class);
            startActivity(addNewPostIntent);
        }

    }
}
