package com.example.assignment1;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class RegisteredUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;
    private Button backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_users);

        recyclerView = findViewById(R.id.recyclerViewUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = AppDatabase.getInstance(this);
        UserDAO userDAO = db.userDAO();

        new Thread(new Runnable() {
            @Override
            public void run() {
                userList = userDAO.getAllUsers();

                List<User> filteredUserList = new ArrayList<>();
                for (User user : userList) {
                    if (user != null && user.getUsername() != null && !user.getUsername().isEmpty()) {
                        filteredUserList.add(user);
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userAdapter = new UserAdapter(filteredUserList);
                        recyclerView.setAdapter(userAdapter);
                    }
                });
            }
        }).start();

        backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisteredUsersActivity.this, MainActivity.class);
                Toast.makeText(getApplicationContext(), "Back Button Clicked!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }
}
