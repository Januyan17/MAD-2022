package com.hospital.booking;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;


    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        emailEditText=(EditText) findViewById(R.id.email);
        resetPasswordButton=(Button)findViewById(R.id.resetPassword);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        auth=FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {

        String email=emailEditText.getText().toString().trim();

        if (email.isEmpty()){
            emailEditText.setError("Email is Required");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Enter Valid Email");
            emailEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){


                    Toast.makeText(ForgetPassword.this,"Please Check Your Email",Toast.LENGTH_SHORT).show();
                    emailEditText.getText().clear();
                    progressBar.setVisibility(View.GONE);
                }else{


                    Toast.makeText(ForgetPassword.this,"This Email Doesnt Belongs to Your Application",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

    }
}