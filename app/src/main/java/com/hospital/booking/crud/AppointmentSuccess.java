package com.hospital.booking.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hospital.booking.R;

public class AppointmentSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_success);

        ImageView back = findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent success = new Intent(AppointmentSuccess.this, AppointmentList.class);
                startActivity(success);
                finish();
            }
        });
    }
}