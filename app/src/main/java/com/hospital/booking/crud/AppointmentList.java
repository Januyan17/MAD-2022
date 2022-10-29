package com.hospital.booking.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hospital.booking.HospitalDescription;
import com.hospital.booking.R;

import java.util.ArrayList;
import java.util.List;

public class AppointmentList extends AppCompatActivity {
    private Button edititemsBtn;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_bookings_list);

        Button create = findViewById(R.id.createNew);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentList.this, CreateAppoinment.class);
                startActivity(intent);
                finish();
            }
        });

        ListView listView = (ListView) findViewById(R.id.list_appointment_cards);
        AppointmentsAdapter appointmentsAdapter = new AppointmentsAdapter(this);
        listView.setAdapter(appointmentsAdapter);


        FirebaseApp.initializeApp(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("BookingApp");
        mDatabaseReference.child("Appointments").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if(dataSnapshot.exists()){
                    List<AppointmentModel> appointmentList = new ArrayList<>();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        AppointmentModel appointmentModel = postSnapshot.getValue(AppointmentModel.class);
                        appointmentList.add(appointmentModel);

                    }
                    appointmentsAdapter.clear();
                    appointmentsAdapter.addAll(appointmentList);
                }else {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                // TODO: Implement this method
            }

        });
    }

}
