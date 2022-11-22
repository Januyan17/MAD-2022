package com.hospital.booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;


public class CustomerSignup extends AppCompatActivity implements View.OnClickListener {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[@#$%^&+=])" +     // at least 1 special character
            "(?=\\S+$)" +            // no white spaces
            ".{6,}" +                // at least 6 characters
            "$");


    private TextView  userlogin;
    private Button registerUser;
    private EditText editTextusername, editTextphone, editTextage, editTextEmail, editTextpassword;
    private ProgressBar progressBar;


    private FirebaseAuth mAuth;
    private FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signup);

        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();



        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        userlogin=(TextView)findViewById(R.id.textlogin) ;
        userlogin.setOnClickListener(this);

        editTextusername = (EditText) findViewById(R.id.username);
        editTextphone = (EditText) findViewById(R.id.phonunumber);
        editTextage = (EditText) findViewById(R.id.age);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextpassword = (EditText) findViewById(R.id.password);


        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        registerUser.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.registerUser:
                registerUser();
                break;

            case R.id.textlogin:
                startActivity(new Intent(CustomerSignup.this, com.hospital.booking.CustomerLogin.class));
                break;
        }

    }

    private void registerUser() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String username = editTextusername.getText().toString().trim();
        String age = editTextage.getText().toString().trim();
        String phonenumber = editTextphone.getText().toString().trim();


        if (username.isEmpty()) {
            editTextusername.setError("User name is required");
            editTextusername.requestFocus();
            return;
        }
        if (phonenumber.isEmpty()) {

            editTextphone.setError("Phone Number Required");
            editTextphone.requestFocus();
            return;
        }

        if(phonenumber.length()!=10){

            editTextphone.setError("Invalid Phone Number");
            editTextphone.requestFocus();
            return;

        }

        if (age.isEmpty()) {
            editTextage.setError("Age required");
            editTextage.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email Required");
            editTextEmail.requestFocus();
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please Provide Valid Email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextpassword.setError("Password Required");
            editTextpassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextpassword.setError("Minimum Password Length Should Be 6 Character");
            editTextpassword.requestFocus();
            return;
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            editTextpassword.setError("Password Too Weak");
            Toast.makeText(this, " Password Should Contain \n At least 1 upper case letter\nAt least 1 special character  \n Atleast One Digit \n AND  \n No white spaces", Toast.LENGTH_LONG).show();
            editTextpassword.requestFocus();
            return;

        }
        registerUser.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            User user = new User(username, age, email, phonenumber);

                            FirebaseDatabase.getInstance().getReference("SLIIT")
                                    .child(email).child(username).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            user.sendEmailVerification();
                                            Toast.makeText(CustomerSignup.this, "Check Your Email To Verify", Toast.LENGTH_SHORT).show();

                                            if (task.isSuccessful()) {


                                                editTextusername.getText().clear();
                                                editTextage.getText().clear();
                                                editTextEmail.getText().clear();
                                                editTextphone.getText().clear();
                                                editTextpassword.getText().clear();
                                                progressBar.setVisibility(View.GONE);
                                                registerUser.setVisibility(View.VISIBLE);

                                                startActivity(new Intent(CustomerSignup.this, com.hospital.booking.CustomerLogin.class));

//                                                Toast.makeText(MerchantSignup.this, "User has been registered Successfully", Toast.LENGTH_SHORT).show();


                                            } else {


                                                editTextusername.getText().clear();
                                                editTextphone.getText().clear();
                                                editTextage.getText().clear();
                                                editTextEmail.getText().clear();
                                                editTextpassword.getText().clear();

                                                Toast.makeText(CustomerSignup.this, "Failed To Register", Toast.LENGTH_SHORT).show();
                                                registerUser.setVisibility(View.VISIBLE);
                                                progressBar.setVisibility(View.GONE);

                                            }

                                        }
                                    });


                        } else {

                            Toast.makeText(CustomerSignup.this, "OOps Email Already Registered !! Try Another ", Toast.LENGTH_SHORT).show();
                            registerUser.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

    }
}