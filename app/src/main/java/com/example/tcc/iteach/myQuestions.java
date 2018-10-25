package com.example.tcc.iteach;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class myQuestions extends AppCompatActivity {
    private RecyclerView myQuestions;
    private DatabaseReference PostsRef;
    private FirebaseAuth mAuth;
    private  String currentUserId,databaseUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_questions);
        myQuestions = (RecyclerView) findViewById(R.id.myQuestions);
        myQuestions.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        myQuestions.setLayoutManager(linearLayoutManager);
        myQuestions.getLayoutManager().setMeasurementCacheEnabled(false);

        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Questions> options =
                new FirebaseRecyclerOptions.Builder<Questions>().setQuery(PostsRef.orderByChild("uid").startAt(currentUserId).endAt(currentUserId+"\uf8ff"), Questions.class).build();

        FirebaseRecyclerAdapter<Questions, blackboard.PostsViewHolder> adapter =
                new FirebaseRecyclerAdapter<Questions, blackboard.PostsViewHolder>(options) {
                    @NonNull
                    @Override
                    public blackboard.PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_questions_layout, parent,false);
                        blackboard.PostsViewHolder viewHolder = new blackboard.PostsViewHolder(view);
                        return  viewHolder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull blackboard.PostsViewHolder holder, int position, @NonNull final Questions model) {

                        final String postKey = getRef(position).getKey();
                        databaseUserID = model.getUid();
                        if (!currentUserId.equals(databaseUserID)) {
                            holder.options.setVisibility(View.INVISIBLE);
                        }
                        if (currentUserId.equals(databaseUserID)) {
                            holder.options.setVisibility(View.VISIBLE);
                        }
                        if (!model.equals(null)) {
                            holder.setFullname(model.getFullname());
                            holder.setDate(model.getDate());
                            holder.setDescription(model.getDescription());
                            holder.commentsButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent clickAnswersIntent = new Intent(myQuestions.this, answerActivity.class);
                                    clickAnswersIntent.putExtra("postkey", postKey);
                                    startActivity(clickAnswersIntent);
                                }
                            });
                            holder.options.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Dialog dialog = new Dialog(myQuestions.this);
                                    dialog.setContentView(R.layout.editing_deleting);
                                    final TextView edit = (TextView) dialog.findViewById(R.id.edit);
                                    final TextView delete = (TextView) dialog.findViewById(R.id.delete);
                                    dialog.show();
                                    delete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            PostsRef.child(postKey).removeValue();
                                            Toast.makeText(myQuestions.this, "تم حذف السؤال بنجاح", Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                        }
                                    });
                                    edit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                       /*     final Dialog dialog = new Dialog(blackboard.this);
                                            dialog.setContentView(R.layout.edit_question);
                                            dialog.setTitle("اكتب النص المعدل هنا");
                                             final TextView edit = (EditText) dialog.findViewById(R.id.editQ);
                                            final TextView update = (Button) dialog.findViewById(R.id.update);
                                            edit.setText(model.description);
                                            dialog.show();
                                            update.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    PostsRef.child(postKey).child("description").setValue(edit.getText().toString());
                                                    Toast.makeText(blackboard.this, "تم تعديل السؤال بنجاح", Toast.LENGTH_SHORT).show();
                                               dialog.cancel(); } }); */
                                            AlertDialog.Builder builder = new AlertDialog.Builder(myQuestions.this);
                                            builder.setTitle("اكتب النص المعدل");
                                            final EditText inputField = new EditText(myQuestions.this);
                                            inputField.setText(model.description);
                                            builder.setView(inputField);
                                            builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    PostsRef.child(postKey).child("description").setValue(inputField.getText().toString());
                                                    Toast.makeText(myQuestions.this,"تم تعديل السؤال" ,Toast.LENGTH_SHORT).show();
                                                    dialog.cancel();
                                                }
                                            }).setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                            Dialog dialog2 = builder.create();
                                            dialog2.show();
                                        }
                                    });

                            /*    AlertDialog.Builder builder = new AlertDialog.Builder(blackboard.this);
                                builder.setMessage("اختر اح الخيارات").setPositiveButton("edit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).setNegativeButton("delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }); */

                                }
                                //  Toast.makeText(t, "تم حذف السؤال", Toast.LENGTH_SHORT).show();
                            });
                        }

                    }  };

        myQuestions.setAdapter(adapter);
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
        ImageButton options;

        public PostsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            commentsButton = (ImageButton) mView.findViewById(R.id.commetsButton);
            options = (ImageButton) mView.findViewById(R.id.options);

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
}
