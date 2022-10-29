package com.hospital.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class HospitalsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        ListView lvCards = (ListView) findViewById(R.id.list_cards);
        CardsAdapter adapter = new CardsAdapter(this);

        lvCards.setAdapter(adapter);
        adapter.addAll(new CardModel(R.drawable.image1),
                new CardModel(R.drawable.image2),
                new CardModel(R.drawable.image3),
                new CardModel(R.drawable.image4),
                new CardModel(R.drawable.image5),
                new CardModel(R.drawable.image1),
                new CardModel(R.drawable.image2),
                new CardModel(R.drawable.image3),
                new CardModel(R.drawable.image4),
                new CardModel(R.drawable.image5));

        lvCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast toast = Toast.makeText(getApplicationContext(), "Hospital clicked", Toast.LENGTH_LONG);
                toast.show();

                Intent appointment = new Intent(HospitalsList.this, HospitalDescription.class);
                startActivity(appointment);
            }
        });

    }
}