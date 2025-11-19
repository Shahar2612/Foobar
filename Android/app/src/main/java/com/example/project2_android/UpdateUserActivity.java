package com.example.project2_android;
import static com.example.project2_android.Entities.ActiveUser.getActiveUser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.project2_android.DB.AppDb;
import com.example.project2_android.DB.PostDao;
import com.example.project2_android.Entities.DataManager;
import com.example.project2_android.Entities.UploadPicture;
import com.example.project2_android.Entities.User;
import com.example.project2_android.Entities.Post;

import java.io.IOException;

import com.example.project2_android.ViewModel.UsersViewModel;
import com.example.project2_android.ViewModel.PostsViewModel;

import java.util.ArrayList;


public class UpdateUserActivity extends AppCompatActivity {

    private EditText firstName, lastName;
    private Uri imageUri;

    private String imageBase64;

    UploadPicture uploadPicture;
    User activeUser;
    UsersViewModel usersViewModel;
    private PostDao dao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user);
        usersViewModel = DataManager.getUsersViewModel();

        // Initialize EditText fields
        firstName = findViewById(R.id.etFirstName);
        lastName = findViewById(R.id.etLastName);
        activeUser = getActiveUser();

        // Set the initial text of the fields to the current  editTexts
        firstName.setText(activeUser.getFirstName());
        lastName.setText(activeUser.getLastName());


        setupCancelButton();
        setupSelectImageButton();
        setupUpdateButton();

    }

    private void setupCancelButton() {
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> finish());
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
        imageBase64 = uploadPicture.getImageBase64();
    }


    private void setupUpdateButton() {
        Button btnSignUp = findViewById(R.id.btnUpdate);
        btnSignUp.setOnClickListener(v -> {
            String fName = firstName.getText().toString().trim();
            String lName = lastName.getText().toString().trim();
            activeUser.setFirstName(fName);
            activeUser.setLastName(lName);
            activeUser.setDisplayName(fName + " " + lName);
            if (imageBase64 != null) {
                activeUser.setPicture(imageBase64);
            }
            AsyncTask.execute(() -> usersViewModel.update(activeUser));
            PostsViewModel postsViewModel = DataManager.getPostsViewModel();
            ArrayList<String> posts = activeUser.getPosts();
            AppDb db = AppDb.getInstance();
            dao = db.postDao();
            for (String post : posts) {
                Post postContent = dao.get(post);
                postContent.setName(activeUser.getDisplayName());
                postContent.setUserPic(activeUser.getPicture());
                postsViewModel.update(postContent);
            }
            finish();
        });
    }
}