package com.example.project2_android;

import static com.example.project2_android.Entities.ActiveUser.getActiveUser;
import static com.example.project2_android.Entities.DataManager.setToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project2_android.Api.OnResultListener;
import com.example.project2_android.Api.TokenAPI;
import com.example.project2_android.DB.AppDb;
import com.example.project2_android.Entities.User;
import com.example.project2_android.Register.SignUpActivity;
import com.example.project2_android.ViewModel.UsersViewModel;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This activity serves as the main entry point to the application and handles user login functionality.
 */
public class MainActivity extends AppCompatActivity {
    private static final String MAIN_NIGHT_MODE_PREF = "MainNightMode";


    // Method to validate user login credentials
    private void isValidUser(String email, String password) {
        TokenAPI tokenAPI = new TokenAPI();
        tokenAPI.getToken(email, password, new OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {
                UsersViewModel usersViewModel = new ViewModelProvider(MainActivity.this).get(UsersViewModel.class);
                AtomicBoolean userFound = new AtomicBoolean(false);
                AtomicBoolean moveToHomePageCalled = new AtomicBoolean(false);

                final Observer<List<User>>[] observer = new Observer[]{null}; // Declare as an array

                runOnUiThread(() -> {
                    observer[0] = users -> {
                        if (!userFound.get()) {
                            for (User user : users) {
                                if (user.getEmail().equals(email)) {
                                    userFound.set(true);
                                    usersViewModel.getUser(email, "Bearer " + result, true);
                                    break; // Exit the loop once the user is found
                                }
                            }
                        } else if (userFound.get() && getActiveUser() != null && !moveToHomePageCalled.get()) {
                            moveToHomePageCalled.set(true);
                            moveToHomePage("Bearer " + result);
                            // Remove the observer after calling moveToHomePage
                            usersViewModel.getAll().removeObserver(observer[0]);
                        }
                        if (!userFound.get()) {
                            // user not found in the local database, get user from the server
                            userFound.set(true);
                            usersViewModel.getUser(email, "Bearer " + result, true);
                        }
                    };

                    // Observe the LiveData
                    usersViewModel.getAll().observe(MainActivity.this, observer[0]);
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(() -> {
                    TextView textViewError = findViewById(R.id.errorMessage);
                    textViewError.setText(R.string.logIn_invalidError);
                    textViewError.setVisibility(View.VISIBLE);
                });
            }
        });
    }

    void moveToHomePage(String token) {
        setToken(token);
        editTextEmail.setText("");
        editTextPassword.setText("");
        Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
        startActivity(intent);

    }

    private EditText editTextEmail, editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        // Initialize EditText fields
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        // Set up click listener for the sign-up button
        Button btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });


        // Set up click listener for the login button
        Button btnLogIn = findViewById(R.id.btnLogin);
        btnLogIn.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            isValidUser(email, password);


        });
    }
}
