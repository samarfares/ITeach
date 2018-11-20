package com.example.tcc.iteach;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetPassword extends AppCompatActivity {
   private Button send;
  private  EditText email;
  private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        email = (EditText) findViewById(R.id.email);
        send = (Button) findViewById(R.id.buttonSend);
        mAuth = FirebaseAuth.getInstance();


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = email.getText().toString();
                if(TextUtils.isEmpty(userEmail)) {
                    Toast.makeText( resetPassword.this, "من فضلك ادخل ايميلك اولا" ,Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                            Toast.makeText( resetPassword.this, "من فضلك تحقق من  رسائل ايميلك" ,Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(resetPassword.this,MainActivity.class));}
                            else{
                                Toast.makeText( resetPassword.this, "لقد حدث خطأ ما" ,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}
