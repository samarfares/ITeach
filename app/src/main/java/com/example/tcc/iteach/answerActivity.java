package com.example.tcc.iteach;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class answerActivity extends AppCompatActivity {

    private RecyclerView answersList;
    private ImageButton answerButton;
    private EditText answerInputText;
    private String post_key,currentUserID;
    private DatabaseReference answersRef;
    private DatabaseReference postRef;
    private FirebaseAuth mAuth;
    private DatabaseReference allAnswersRef;
    private RecyclerView answerList;
    private DatabaseReference likesRef;
    boolean likeChecker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        post_key = getIntent().getExtras().get("postkey").toString();

        allAnswersRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(post_key).child("comments");
        answersRef = FirebaseDatabase.getInstance().getReference().child("users");
        likesRef = FirebaseDatabase.getInstance().getReference().child("likes");
        postRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(post_key).child("comments");
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        answersList = (RecyclerView) findViewById(R.id.answersList);
        answersList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        answersList.setLayoutManager(linearLayoutManager);
        answerInputText = (EditText) findViewById(R.id.addAnswer);
        answerButton = (ImageButton) findViewById(R.id.answerButton);
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String userName = dataSnapshot.child("fullname").getValue().toString();
                            validateComment(userName);
                            answerInputText.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    private void validateComment(String userName){
        String answerText = answerInputText.getText().toString();
        if(TextUtils.isEmpty(answerText)){
            Toast.makeText(this,"من فضلك ادخل الاجابة اولا",Toast.LENGTH_SHORT).show();
        }
        else {
            Calendar calFordDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
            final String saveCurrentDate = currentDate.format(calFordDate.getTime());

            Calendar calFordTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            final String saveCurrentTime = currentTime.format(calFordDate.getTime());

            final String RandomKey = currentUserID + saveCurrentDate + saveCurrentTime;
            HashMap answersMap = new HashMap();
            answersMap.put("uid",currentUserID);
            answersMap.put("answer",answerText);
            answersMap.put("date",saveCurrentDate);
            answersMap.put("time",saveCurrentTime);
            answersMap.put("userName",userName);
            postRef.child(RandomKey).updateChildren(answersMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(answerActivity.this,"تم نشر الجواب",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(answerActivity.this,"لقد حدث خطأ ما أعد المحاولة",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Answers> options =
                new FirebaseRecyclerOptions.Builder<Answers>().setQuery(allAnswersRef, Answers.class).build();

        FirebaseRecyclerAdapter<Answers, AnswersViewHolder> adapter =
                new FirebaseRecyclerAdapter<Answers, AnswersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AnswersViewHolder holder, int position, @NonNull Answers model) {
                        final String postKey = getRef(position).getKey();
                        holder.setUsername(model.getUserName());
                        holder.setDate(model.getDate());
                        holder.setComment(model.getComment());
                        holder.setTime(model.getTime());
                        holder.setLikeButtonStatus(postKey);
                        holder.likeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                likeChecker = true;

                                likesRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(likeChecker==true){
                                            if(dataSnapshot.child(postKey).hasChild(currentUserID)) {
                                                likesRef.child(postKey).child(currentUserID).removeValue();
                                                likeChecker = false;

                                            }
                                            else {
                                                likesRef.child(postKey).child(currentUserID).setValue(true);
                                                likeChecker = false;
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public answerActivity.AnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_answers_layout, parent, false);
                        AnswersViewHolder viewHolder = new AnswersViewHolder(view);
                        return viewHolder;}

                };




        answersList.setAdapter(adapter);
        answersList.smoothScrollToPosition(adapter.getItemCount());
        adapter.startListening();


    }

    public static class AnswersViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageButton likeButton;
        TextView numberOfLikes;
        int countLikes;
        String currentUserId;
        DatabaseReference likesRef;

        public AnswersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            likeButton = (ImageButton) mView.findViewById(R.id.likeButton);
            numberOfLikes = (TextView) mView.findViewById(R.id.likesNumber);
            likesRef = FirebaseDatabase.getInstance().getReference().child("likes");
            currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        }
        public void setLikeButtonStatus(final String postKey) {
            likesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(postKey).hasChild(currentUserId)) {
                        countLikes = (int) dataSnapshot.child(postKey).getChildrenCount();
                        likeButton.setImageResource(R.drawable.like1);
                        numberOfLikes.setText(Integer.toString(countLikes)+(" اعجاب "));
                    }
                    else {
                        countLikes = (int) dataSnapshot.child(postKey).getChildrenCount();
                        likeButton.setImageResource(R.drawable.dislike);
                        numberOfLikes.setText(Integer.toString(countLikes)+(" اعجاب "));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        public void setComment(String comment) {
            TextView answerText = (TextView) mView.findViewById(R.id.AnswerText);
            answerText.setText(comment);
        }

        public void setDate(String date) {
            TextView answerdate = (TextView) mView.findViewById(R.id.AnswerDate);
            answerdate.setText(date);
        }

        public void setTime(String time) {
            TextView answerTime = (TextView) mView.findViewById(R.id.AnsweerTime);
            answerTime.setText(time);
        }

        public void setUsername(String username) {
            TextView answerUserName = (TextView) mView.findViewById(R.id.AnswerUserName);
            answerUserName.setText(username);
        }
    }

}
