package com.example.project2_android.Register;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.project2_android.Api.OnResultListener;
import com.example.project2_android.Entities.UploadPicture;
import com.example.project2_android.Entities.User;

import java.io.IOException;

import com.example.project2_android.R;
import com.example.project2_android.ViewModel.UsersViewModel;

import java.util.Map;


public class SignUpActivity extends AppCompatActivity {

    private EditText firstName, lastName, email, password, confirmPassword;
    private Uri imageUri;

    private String imageBase64;

    UploadPicture uploadPicture;

    UsersViewModel usersViewModel;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        // Initialize EditText fields
        firstName = findViewById(R.id.etFirstName);
        lastName = findViewById(R.id.etLastName);
        email = findViewById(R.id.etEmailAddress);
        password = findViewById(R.id.etPassword);
        confirmPassword = findViewById(R.id.etConfirmPassword);

        setupCloseButton();
        setupSelectImageButton();
        setupSignUpButton();

    }

    private void setupCloseButton() {
        Button btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> finish());
    }

    private void setupSelectImageButton() {
        Button btnSelectImage = findViewById(R.id.btnSelectImage);
        ImageView imageGallery = new ImageView(this);
        uploadPicture = new UploadPicture(imageGallery, imageUri);
        btnSelectImage.setOnClickListener(v -> uploadPicture.showPopupMenu(v, this, this));
    }





    // This method is called when the user returns from the gallery or camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // method from UploadPicture handle the result

        try {
            uploadPicture.onActivityResult(requestCode, resultCode, data, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // imageUri = uploadPicture.getImageUri();
        imageBase64 = uploadPicture.getImageBase64();

    }


    private void setupSignUpButton() {
        Button btnSignUp = findViewById(R.id.btnRegister);
        btnSignUp.setOnClickListener(v -> validateAndRegister());
    }

    private void validateAndRegister() {
        SignUpValidation validator = new SignUpValidation();
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        Map<EditText, String> errorFields =
                validator.getInvalidFields(firstName, lastName, email, password, confirmPassword);

        boolean isPictureValid = validator.isPictureValid(imageBase64);

        if (errorFields.isEmpty() && isPictureValid) {
            // All data is valid, continue with registration
            registerUser(fName, lName, mail, pass, imageBase64);


        } else {
            // Display error messages and update colors
            displayErrorMessages(errorFields, isPictureValid);
        }
    }



    private void registerUser(String fName, String lName, String mail, String pass, String imageBase64) {
        User user = new User(fName, lName, mail, pass, imageBase64);
        usersViewModel.add(user, new OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {
                finish();
            }

            @Override
            public void onFailure(String result) {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show());
            }
        });

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void displayErrorMessages(Map<EditText, String> errorFields, boolean isPictureValid) {
        for (Map.Entry<EditText, String> entry : errorFields.entrySet()) {
            EditText editText = entry.getKey();
            String errorMessage = entry.getValue();

            // Set red color for red error fields
            editText.setHintTextColor(Color.RED);
            editText.setError(errorMessage);

            // Set a custom error icon
            Drawable errorIcon = getResources().getDrawable(R.drawable.ic_error);
            errorIcon.setBounds(0, 0, errorIcon.getIntrinsicWidth(), errorIcon.getIntrinsicHeight());

            // Set the error icon to the right of the EditText
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, errorIcon, null);

            if (!isPictureValid) {
                TextView tvPicture = findViewById(R.id.picture);
                tvPicture.setTextColor(Color.RED);
                tvPicture.setCompoundDrawablesRelativeWithIntrinsicBounds(null,
                        null, errorIcon, null);
                tvPicture.setError("Please select a picture");
            } else {
                TextView tvPicture = findViewById(R.id.picture);
                tvPicture.setTextColor(Color.WHITE);
            }

            // Add an OnFocusChangeListener to reset color when the user provides corrected input
            editText.setOnFocusChangeListener((view, hasFocus) -> {
                if (hasFocus) {
                    // Reset color when the field gains focus
                    editText.setHintTextColor(Color.WHITE);
                    editText.setCompoundDrawablesRelativeWithIntrinsicBounds(null,
                            null, null, null);

                    editText.setError(null);

                }
            });
        }
    }




}