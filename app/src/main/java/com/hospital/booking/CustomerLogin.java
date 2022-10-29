package com.hospital.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class CustomerLogin extends AppCompatActivity implements View.OnClickListener {
    private TextView register,forgotPassword;
    private EditText editTextEmail, EditTextPassword;
    private Button signIn ;


    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        mAuth = FirebaseAuth.getInstance();

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        EditTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        forgotPassword=(TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, com.hospital.booking.MobileNoEnter.class));
                break;


            case R.id.signIn:
                userLogin();
                break;


            case R.id.forgotPassword:
                startActivity(new Intent(this,ForgetPassword.class));
                break;



        }
    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = EditTextPassword.getText().toString().trim();


        if (email.isEmpty()) {

            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }

//        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            editTextEmail.setError("Enter Valid Email");
//            return;
//
//        }
        if (password.isEmpty()) {
            EditTextPassword.setError("Password Required");
            EditTextPassword.requestFocus();
            return;
        }

//        if(email.equals("admin@gmail.com") && password.equals("admin"))
//        {
//            startActivity(new Intent(CustomerLogin.this,MyProfile.class));
//        }

        else{
            //    BCrypt.Result resultpassword=BCrypt.verifyer().verify(password.toCharArray(),hashpassword);
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if (user.isEmailVerified()) {

                            EditTextPassword.getText().clear();
                            editTextEmail.getText().clear();
                            startActivity(new Intent(CustomerLogin.this, MyProfile.class));
                            progressBar.setVisibility(View.GONE);
                            finish();
                        } else {
//
//                        user.sendEmailVerification();
                            Toast.makeText(CustomerLogin.this, "Please Verify Your Account", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
//
                        }


                    } else {
                        Toast.makeText(CustomerLogin.this, "Failed Login Please Check Your Credential", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }


                }

            });
        }

    }
}