package com.example.assignment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private TextView tvResult;
    private Button btnLogin, btnRegister, btnCancel;

    private UserDAO userDAO;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        tvResult = findViewById(R.id.tvResult);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnCancel = findViewById(R.id.btnCancel);

        db = AppDatabase.getInstance(this);
        userDAO = db.userDAO();

        btnLogin.setOnClickListener(view -> handleLogin());
        btnRegister.setOnClickListener(view -> handleRegister());
        btnCancel.setOnClickListener(view -> handleCancel());
    }

    private void handleLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            tvResult.setText("Please enter both username and password");
            return;
        }

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            User existingUser = userDAO.findByUsername(username);

            runOnUiThread(() -> {
                if (existingUser == null) {
                    tvResult.setText("User not found");
                    return;

                }


                String userPassword = existingUser.getPassword();
                String userRole = existingUser.getRole();

                if (userPassword != null && userPassword.equals(password)) {
                    tvResult.setText("Login success!");

                    SharedPreferences sharedPreferences = getSharedPreferences("user_perfs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("is_logged_in", true);
                    editor.putString("username", username);
                    editor.putString("user_type", userRole);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    tvResult.setText("Incorrect password");
                }
            });
        });
    }
    private void handleRegister() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            tvResult.setText("Please enter both username and password");
            return;
        }

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            User existingUser = userDAO.findByUsername(username);
            runOnUiThread(() -> {
                if (existingUser != null) {
                    tvResult.setText("User already exists, please try again");
                } else {
                    String role = "user";
                    User newUser = new User(username, password, role);

                    executor.execute(() -> {
                        userDAO.insert(newUser);
                        runOnUiThread(() -> {
                            tvResult.setText("Registration Successful!");

                        });
                    });
                }
            });
        });
    }



    private void handleCancel() {
        etUsername.setText("");
        etPassword.setText("");
        tvResult.setText("");
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}