package com.hospital.booking.crud;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hospital.booking.MainActivity;
import com.hospital.booking.R;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateAppoinment extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Button sucsAppoinment;
    private Button backAppoinment;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseInstance;
    String hospitalName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appoinment);

        Intent intent = getIntent();
        hospitalName = intent.getStringExtra("id");


        EditText name = (EditText) findViewById(R.id.textaddd);
        EditText address = (EditText) findViewById(R.id.bookname3);
        EditText doctorname = (EditText) findViewById(R.id.booknamedoctorname);
        EditText phone = (EditText) findViewById(R.id.bookname4);
        EditText age = (EditText) findViewById(R.id.bookname);
        TextView date = (TextView) findViewById(R.id.textcalender);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        FirebaseApp.initializeApp(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("BookingApp");

        backAppoinment=findViewById(R.id.RegButton);
        sucsAppoinment=findViewById(R.id.btnbookappinment);
        backAppoinment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent success = new Intent(CreateAppoinment.this, MainActivity.class);
                startActivity(success);
                finish();

            }
        });
        sucsAppoinment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivitySuc();
            }

            private void openActivitySuc() {//success page button navigation
                String newId = mDatabaseReference.child("Appointments").push().getKey();

                AppointmentModel appointment = new AppointmentModel();
                appointment.setId(newId);
                appointment.setPatientName(name.getText().toString());
                appointment.setDoctorName(doctorname.getText().toString());
                appointment.setBookingDate(date.getText().toString());
                appointment.setAge(age.getText().toString());
                appointment.setPhoneNo(phone.getText().toString());
                appointment.setPatientAddress(address.getText().toString());

                FirebaseDatabase.getInstance().getReference("BookingApp")
                        .child("Appointments").child(newId).setValue(appointment)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CreateAppoinment.this, "Appointment Created successfully", Toast.LENGTH_LONG)
                                .show();
                        Intent success = new Intent(CreateAppoinment.this, AppointmentSuccess.class);
                        startActivity(success);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateAppoinment.this, "Appointment creation failed", Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        });


        ImageView button=(ImageView) findViewById(R.id.buttoncalender);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker= new DatePikerFragment();
                datePiker.show(getSupportFragmentManager(),"date Picker");
            }
        });


    };


        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String CurrentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView textView =(TextView)findViewById(R.id.textcalender);
        textView.setText(CurrentDateString);
        
    }


}