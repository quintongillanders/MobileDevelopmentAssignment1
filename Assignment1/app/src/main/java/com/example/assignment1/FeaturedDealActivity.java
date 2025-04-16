package com.example.assignment1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FeaturedDealActivity extends AppCompatActivity {
    Button backbutton1;
    RecyclerView recyclerView;
    FeaturedDealAdapter adapter;
    List<FeaturedDeal> dealList = new ArrayList<>();
    String selectedCategory;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_featured_deal2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backbutton1 = findViewById(R.id.backbutton1);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        selectedCategory = intent.getStringExtra("category_name");

        adapter = new FeaturedDealAdapter(dealList,this);
        recyclerView.setAdapter(adapter);

        insertData();
        loadFeaturedDeals();

        Context cnt = getApplicationContext();

        backbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Back Button Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FeaturedDealActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterDeals(query, true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterDeals(newText, false);
                return true;
            }
        });
    }


    private void filterDeals(String query, boolean showToastIfEmpty) {
        FeaturedDealDatabase db = FeaturedDealDatabase.getInstance(this);
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            List<FeaturedDeal> filteredDeals;

            if (query == null || query.trim().isEmpty()) {
                // Show all deals if search bar is empty
                filteredDeals = db.featuredDealDAO().getAllDeals();
            } else {
                // Search by category:
                List<FeaturedDeal> allDeals = db.featuredDealDAO().getAllDeals();
                filteredDeals = new ArrayList<>();

                for (FeaturedDeal deal : allDeals) {
                    if (deal.getCategory() != null && deal.getCategory().toLowerCase().contains(query.toLowerCase())) {
                        filteredDeals.add(deal);
                    }
                }
            }

            List<FeaturedDeal> fallbackDeals = db.featuredDealDAO().getAllDeals();

            runOnUiThread(() -> {
                if (filteredDeals != null && !filteredDeals.isEmpty()) {
                    adapter.updateDealList(filteredDeals);
                } else {
                    if (showToastIfEmpty) {
                        Toast.makeText(FeaturedDealActivity.this, "No deals found, check back later!", Toast.LENGTH_SHORT).show();
                    }
                    adapter.updateDealList(fallbackDeals);
                }
            });
        });
    }



    private void insertData() {
        FeaturedDealDatabase db = FeaturedDealDatabase.getInstance(this);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {


            List<FeaturedDeal> existingDeals = db.featuredDealDAO().getAllDeals();
            db.featuredDealDAO().deleteAllDeals(); // this line is to reset the database for testing.

                FeaturedDeal deal1 = new FeaturedDeal("Restaurant", "Smoke on the water", 30.00, R.drawable.fork_and_knife);
                FeaturedDeal deal2 = new FeaturedDeal("Restaurant", "Ocean view seafood", 40.00, R.drawable.fork_and_knife);
                FeaturedDeal deal3 = new FeaturedDeal("Hotels", "Ocean View Hotel", 200.00, R.drawable.hotel);
                FeaturedDeal deal4 = new FeaturedDeal("Places to visit", "Ocean View Beach", 0.00, R.drawable.palm_tree);
                FeaturedDeal deal5 = new FeaturedDeal("Beaches", "Vespucci Beach", 0.00, R.drawable.sun);
                FeaturedDeal deal6 = new FeaturedDeal("Events", "Movie night", 20.00, R.drawable.events);
                FeaturedDeal deal7 = new FeaturedDeal("Hotels", "SkyCity", 50.00, R.drawable.hotel);
                FeaturedDeal deal8 = new FeaturedDeal("Places to visit", "Rainbows End", 50.00, R.drawable.palm_tree);
                FeaturedDeal deal9 = new FeaturedDeal("Places to visit", "Diamond Casino and Resort", 0.00, R.drawable.palm_tree);
                FeaturedDeal deal10 = new FeaturedDeal("Beaches", "Portland Beach", 0.00, R.drawable.sun);
                FeaturedDeal deal11 = new FeaturedDeal("Events", "Fireworks show", 20.00, R.drawable.events);


                // Insert deals to the FeaturedDeal list

                db.featuredDealDAO().insert(deal1);
                db.featuredDealDAO().insert(deal2);
                db.featuredDealDAO().insert(deal3);
                db.featuredDealDAO().insert(deal4);
                db.featuredDealDAO().insert(deal5);
                db.featuredDealDAO().insert(deal6);
                db.featuredDealDAO().insert(deal7);
                db.featuredDealDAO().insert(deal8);
                db.featuredDealDAO().insert(deal9);
                db.featuredDealDAO().insert(deal10);
                db.featuredDealDAO().insert(deal11);

        });
    }


    private void loadFeaturedDeals() {
        FeaturedDealDatabase db = FeaturedDealDatabase.getInstance(this);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            List<FeaturedDeal> deals = db.featuredDealDAO().getAllDeals();

            runOnUiThread(() -> {
                if (deals.isEmpty()) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "No deals found at this time", Toast.LENGTH_SHORT).show());
                } else {
                    adapter.updateDealList(deals);
                }
            });
        });
    }
}




