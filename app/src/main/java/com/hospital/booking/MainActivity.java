package com.hospital.booking;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<CategoryModel> movieList = new ArrayList<>();
    private CategoriesAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new CategoriesAdapter(movieList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareMovieData();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Intent hospitalList = new Intent(MainActivity.this, HospitalsList.class);
                        startActivity(hospitalList);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        ListView lvCards = (ListView) findViewById(R.id.list_cards);
        CardsAdapter adapter = new CardsAdapter(this);

        lvCards.setAdapter(adapter);
        adapter.addAll(new CardModel(R.drawable.image1),
                new CardModel(R.drawable.image2),
                new CardModel(R.drawable.image3),
                new CardModel(R.drawable.image4),
                new CardModel(R.drawable.image5));

        lvCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast toast = Toast.makeText(getApplicationContext(), "Hospital clicked", Toast.LENGTH_LONG);
//                toast.show();

                Intent appointment = new Intent(MainActivity.this, HospitalDescription.class);
                startActivity(appointment);
            }
        });

    }
    private void prepareMovieData() {
        CategoryModel movie = new CategoryModel("Neurology", "1022 Doctors");
        movieList.add(movie);
        movie = new CategoryModel("Generics", "1452 Doctors");
        movieList.add(movie);
        movie = new CategoryModel("Dentistry", "3212 Doctors");
        movieList.add(movie);
        movie = new CategoryModel("Dermatologist", "2453 Doctors");
        movieList.add(movie);
        movie = new CategoryModel("Cardiologists", "2167 Doctors");
        movieList.add(movie);
        mAdapter.notifyDataSetChanged();
    }
}