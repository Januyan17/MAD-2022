package com.hospital.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MobileNoEnter extends AppCompatActivity {

    private EditText enternumber;
    private Button getotpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_no_enter);


        enternumber = findViewById(R.id.input_mobile_number);
        getotpButton = findViewById(R.id.buttongetotp);


        ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);


        getotpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!enternumber.getText().toString().trim().isEmpty()) {

                    if ((enternumber.getText().toString().trim().length() == 10)) {

                        progressBar.setVisibility(View.VISIBLE);
                        getotpButton.setVisibility(View.INVISIBLE);


                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+94" + enternumber.getText().toString(), 30,
                                TimeUnit.SECONDS,
                                MobileNoEnter.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                        progressBar.setVisibility(View.GONE);
                                        getotpButton.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        progressBar.setVisibility(View.GONE);
                                        getotpButton.setVisibility(View.VISIBLE);
                                        Toast.makeText(MobileNoEnter.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getotpButton.setVisibility(View.VISIBLE);

                                        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                                        intent.putExtra("mobile", enternumber.getText().toString());
                                        intent.putExtra("backendotp", backendotp);
                                        startActivity(intent);
                                    }
                                }

                        );


//                Intent intent =new Intent(getApplicationContext(),VerifyOTP.class);
//                intent.putExtra("mobile",enternumber.getText().toString());
//                startActivity(intent);
                    } else {
                        Toast.makeText(MobileNoEnter.this, "Please Enter Correct Number", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(MobileNoEnter.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}