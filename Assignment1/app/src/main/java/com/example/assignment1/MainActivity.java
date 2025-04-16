package com.example.assignment1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button featureddealsButton, categoriesButton, loginButton, logoutButton, loginadminButton, loginsupplierButton, registeredUsersButton;
    TextView tvLoggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        featureddealsButton = findViewById(R.id.featureddealsButton);
        categoriesButton = findViewById(R.id.categoriesButton);
        loginButton = findViewById(R.id.loginButton);
        logoutButton = findViewById(R.id.logoutButton);
        loginadminButton = findViewById(R.id.loginadminButton);
        loginsupplierButton = findViewById(R.id.loginsupplierButton);
        registeredUsersButton = findViewById(R.id.registeredUsersButton);
        tvLoggedInUser = findViewById(R.id.loggedInAs);

        Context cnt = getApplicationContext();
        SharedPreferences sharedPreferences = getSharedPreferences("user_perfs", MODE_PRIVATE);
        boolean isLoggedin = sharedPreferences.getBoolean("is_logged_in", false);
        String username = sharedPreferences.getString("username", "");
        String userType = sharedPreferences.getString("user_type", "");

        updateUI(isLoggedin, username, userType);

        categoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Categories Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(cnt, CategoryActivity.class);
                startActivity(intent);
            }
        });

        featureddealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Featured Deals Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(cnt, FeaturedDealActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Login as customer Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        loginsupplierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Login as supplier Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        loginadminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Login as admin Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginAdminActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("user_perfs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("is_logged_in", false);
                editor.putString("username", "");
                editor.apply();

                updateUI(false, "", userType);
            }
        });

        registeredUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Registered Users button clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, RegisteredUsersActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateUI(boolean isLoggedin, String username, String userType) {
        if(isLoggedin) {
            loginButton.setVisibility(View.GONE);
            loginsupplierButton.setVisibility(View.GONE);
            loginadminButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
            categoriesButton.setVisibility(View.VISIBLE);
            featureddealsButton.setVisibility(View.VISIBLE);
            tvLoggedInUser.setText("Logged in as: " + username);

            if("admin".equals(userType)) {
                registeredUsersButton.setVisibility(View.VISIBLE);
            } else {
                registeredUsersButton.setVisibility(View.GONE);
            }
        } else {
            loginButton.setVisibility(View.VISIBLE);
            loginadminButton.setVisibility(View.VISIBLE);
            loginsupplierButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
            categoriesButton.setVisibility(View.GONE);
            featureddealsButton.setVisibility(View.GONE);
            registeredUsersButton.setVisibility(View.GONE);
            tvLoggedInUser.setVisibility(View.GONE);

        }
    }
}
