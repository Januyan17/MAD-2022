package com.hospital.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class SigninSignup extends AppCompatActivity implements View.OnClickListener {


    private Button navigatesignin,navigatesignup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_signup);


        navigatesignin=(Button) findViewById(R.id.navigatesignin);
        navigatesignin.setOnClickListener(this);

        navigatesignup=(Button) findViewById(R.id.navigatesignup);
        navigatesignup.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.navigatesignin:
                startActivity(new Intent(SigninSignup.this, com.hospital.booking.CustomerLogin.class));
                break;

            case R.id.navigatesignup:
                startActivity(new Intent(SigninSignup.this, com.hospital.booking.MobileNoEnter.class));
                break;
        }

    }
}