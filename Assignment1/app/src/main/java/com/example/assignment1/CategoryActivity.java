package com.example.assignment1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryActivity extends AppCompatActivity {
    Button backbutton2;
    RecyclerView recyclerView;
    RVAdapter rvAdapter;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_category);

        backbutton2 = findViewById(R.id.backbutton2);
        recyclerView = findViewById(R.id.recyclerView);

        String data[] = {
                "Restaurants: Best Resturants in NZ",
                "Hotels",
                "Places to visit",
                "Beaches: Take a look at the beaches around NZ",
                "Events"};

        int [] images = {
                R.drawable.fork_and_knife, // Restaurants
                R.drawable.hotel,         // Hotels
                R.drawable.palm_tree,   // Places to visit
                R.drawable.sun,         // Beaches
                R.drawable.events       // Events

        };


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new RVAdapter(data, images);
        recyclerView.setAdapter(rvAdapter);

        backbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Back Button Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }
}
