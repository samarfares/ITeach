package com.example.tcc.iteach;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class adminBlackboard extends AppCompatActivity {

    private RecyclerView postList;
    private DatabaseReference  PostsRef;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth;
    Dialog dialog5;
    Spinner spinner;
   adminBlackboard.SpinnerAdapter adapter2;
    String spinner_item;
    String[] title;
    private   String subject;
    Spinner inputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_blackboard);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        firebaseAuth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();
        postList = (RecyclerView) findViewById(R.id.adminboard);
        postList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postList.setLayoutManager(linearLayoutManager);
        postList.getLayoutManager().setMeasurementCacheEnabled(false);

        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        adapter2 = new  SpinnerAdapter(getApplicationContext());

        title = getResources().getStringArray(R.array.specialty);
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Questions> options =
                new FirebaseRecyclerOptions.Builder<Questions>().setQuery(PostsRef, Questions.class).build();

        final FirebaseRecyclerAdapter<Questions, blackboard.PostsViewHolder> adapter =
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
                        if (!model.equals(null)) {
                            holder.setFullname(model.getFullname());
                            holder.setDate(model.getDate());
                            holder.setDescription(model.getDescription());
                            holder.setSubject(model.getSubject());
                            holder.commentsButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent clickAnswersIntent = new Intent(adminBlackboard.this,answerAdmin.class);
                                    clickAnswersIntent.putExtra("postkey",postKey);
                                    startActivity(clickAnswersIntent);
                                }
                            });
                            holder.options.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog5 = new Dialog(adminBlackboard.this);
                                    dialog5.setContentView(R.layout.editing_deleting);
                                    final TextView edit = (TextView) dialog5.findViewById(R.id.edit);
                                    final TextView delete = (TextView) dialog5.findViewById(R.id.delete);
                                    dialog5.show();
                                    delete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            AlertDialog.Builder builder = new AlertDialog.Builder(adminBlackboard.this);
                                            builder.setTitle("هل أنت متأكد؟");
                                            builder.setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    PostsRef.child(postKey).removeValue();
                                                    Toast.makeText(adminBlackboard.this, "تم حذف السؤال بنجاح", Toast.LENGTH_SHORT).show();
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
                                            AlertDialog.Builder builder = new AlertDialog.Builder(adminBlackboard.this);
                                            builder.setTitle("اختر المادة");
                                            inputField = new Spinner(adminBlackboard.this);
                                            inputField.setAdapter(adapter2);
                                           // inputField.setText(model.description);
                                            builder.setView(inputField);
                                            inputField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    spinner_item = title[position];
                                                    subject=spinner_item;
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });
                                            builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    PostsRef.child(postKey).child("subject").setValue(subject);
                                                    Toast.makeText(adminBlackboard.this,"تم تعديل المادة" ,Toast.LENGTH_SHORT).show();
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


                                }
                                //  Toast.makeText(t, "تم حذف السؤال", Toast.LENGTH_SHORT).show();
                            });
                        } }

                };

        postList.setAdapter(adapter);
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
   /* private void SendUserToPostActivity()
    {
        Intent addNewPostIntent = new Intent(blackboard.this, askActivity.class);
        startActivity(addNewPostIntent);
    }  */

    public class SpinnerAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater mInflater;

        public SpinnerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final NameSearch.ListContent holder;
            View v = convertView;
            if (v == null) {
                mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                v = mInflater.inflate(R.layout.row_edittext, null);
                holder = new NameSearch.ListContent();
                holder.text = (TextView) v.findViewById(R.id.textView1);

                v.setTag(holder);
            } else {
                holder = (NameSearch.ListContent) v.getTag();
            }

            holder.text.setText(title[position]);

            return v;
        }
    }

    static class ListContent {
        TextView text;
    }
}
