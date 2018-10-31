package com.example.tcc.iteach;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class filterQuestions extends AppCompatActivity {
    private ImageButton searchButton,voiceButton;
    private EditText searchInputText;
    private RecyclerView searchResaults;

    private DatabaseReference UsersRef, PostsRef;
    Questions question;
    private Button askButton,my,filter;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth;
    private  String currentUserId,databaseUserID;
    Dialog dialog5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_questions);
      voiceButton = (ImageButton) findViewById(R.id.voiceSearch);
        searchInputText = (EditText) findViewById(R.id.searchInput);
        searchResaults = (RecyclerView) findViewById(R.id.searchResults);
        firebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        searchResaults.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
       searchResaults.setLayoutManager(linearLayoutManager);
       searchResaults.getLayoutManager().setMeasurementCacheEnabled(false);

        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        searchInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchBoxInput = searchInputText.getText().toString();
                searchQuestion(searchBoxInput);
            }
        });

    /*    searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchBoxInput = searchInputText.getText().toString();
                searchQuestion(searchBoxInput);
            }
        });  */
    }

    @Override
    protected void onStart() {
        super.onStart();
        searchInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchBoxInput = searchInputText.getText().toString();
                searchQuestion(searchBoxInput);
            }
        });
    }

    private void searchQuestion(String searchBoxInput)  {
      FirebaseRecyclerOptions<Questions> options =
              new FirebaseRecyclerOptions.Builder<Questions>().setQuery(PostsRef.orderByChild("subject").startAt(searchBoxInput).endAt(searchBoxInput+"\uf8ff"), Questions.class).build();
      FirebaseRecyclerAdapter<Questions, blackboard.PostsViewHolder> adapter =
              new FirebaseRecyclerAdapter<Questions, blackboard.PostsViewHolder>(options) {
                  @NonNull
                  @Override
                  public blackboard.PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_questions_layout, parent, false);
                      blackboard.PostsViewHolder viewHolder = new blackboard.PostsViewHolder(view);
                      return viewHolder;
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
                          holder.setSubject(model.getSubject());
                          holder.commentsButton.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                  Intent clickAnswersIntent = new Intent(filterQuestions.this,answerActivity.class);
                                  clickAnswersIntent.putExtra("postkey",postKey);
                                  startActivity(clickAnswersIntent);
                              }
                          });
                          holder.options.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  dialog5  = new Dialog(filterQuestions.this);
                                  dialog5.setContentView(R.layout.editing_deleting);
                                  final TextView edit = (TextView) dialog5.findViewById(R.id.edit);
                                  final TextView delete = (TextView) dialog5.findViewById(R.id.delete);
                                  dialog5.show();
                                  delete.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          PostsRef.child(postKey).removeValue();
                                          Toast.makeText(filterQuestions.this, "تم حذف السؤال بنجاح", Toast.LENGTH_SHORT).show();
                                          dialog5.cancel();
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
                                          AlertDialog.Builder builder = new AlertDialog.Builder(filterQuestions.this);
                                          builder.setTitle("اكتب النص المعدل");
                                          final EditText inputField = new EditText(filterQuestions.this);
                                          inputField.setText(model.description);
                                          builder.setView(inputField);
                                          builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialog, int which) {
                                                  PostsRef.child(postKey).child("description").setValue(inputField.getText().toString());
                                                  Toast.makeText(filterQuestions.this,"تم تعديل السؤال" ,Toast.LENGTH_SHORT).show();
                                                  dialog5.cancel();
                                              }
                                          }).setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialog, int which) {
                                                  dialog5.cancel();
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
                      } }

              };

      searchResaults.setAdapter(adapter);
      adapter.startListening();
                      }


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

        public void setSubject(String subject) {
            TextView PostSubject = (TextView) mView.findViewById(R.id.questionSubject);
            PostSubject.setText(subject);

        }
    }

    public void getSpeechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
       // intent.putExtra("android.speech.extra.EXTRA_LANGUAGES", new  String[]{});
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        }
        else {
            Toast.makeText(filterQuestions.this, "هذه الميزة غير مدعومة بجهازك", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if(resultCode == RESULT_OK && data != null) {
                ArrayList<String> resault =  data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
              searchInputText.setText(resault.get(0));
                    searchQuestion(resault.get(0));
                }
                break;
        }
    }
}
