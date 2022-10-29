package com.hospital.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hospital.booking.crud.AppointmentList;

public class HospitalDescription extends AppCompatActivity {

    TextView description;
    Button createAppointment;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        description = findViewById(R.id.txt_description_text);
        description.setText("All we care is your health and well being hence, we, at Royal Hospital treat all our patients with full care, assisting all their needs while ensuring their health and fitness. Here, patients have access to all medical and surgical sub-specialties within a single institution, empowering the medical team to address all the patient’s health needs and treat the whole person.");

        createAppointment = findViewById(R.id.btn_suggest);
        createAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HospitalDescription.this, AppointmentList.class);
                startActivity(intent);
            }
        });
    }
}