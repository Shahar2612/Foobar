package com.example.project2_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_android.Entities.DataManager;
import com.example.project2_android.Entities.Post;
import com.example.project2_android.Entities.UploadPicture;
import com.example.project2_android.ViewModel.PostsViewModel;

import java.io.IOException;

public class UpdatePostActivity extends AppCompatActivity {
    UploadPicture uploadPicture;
    String imageStringUpload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_post);
        EditText postEditText = findViewById(R.id.editTextPost);
        Button uploadButton = findViewById(R.id.btnCamera);
        Button submitButton = findViewById(R.id.buttonSubmit);
        Button cancelButton = findViewById(R.id.buttonCancelEdit);
        Button resetButton = findViewById(R.id.buttonNoPicture);

        ImageView imageUpload = new ImageView(this);
        uploadPicture = new UploadPicture(imageUpload, null);
        uploadButton.setOnClickListener(v -> uploadPicture.showPopupMenu(v, this, this));

        // Fetch the post from the adapter's dataset
        PostsListAdapter adapter = DataManager.getPostsListAdapter();
        PostsViewModel postsViewModel = DataManager.getPostsViewModel();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        Post post = adapter.getPosts().get(position);

        // Set the initial text of the EditText to the current post content
        postEditText.setText(post.getText());
        resetButton.setOnClickListener(v -> {
            imageStringUpload = null;
            post.setPhoto(" ");
        });


        submitButton.setOnClickListener(v -> {
            // Get the new text from the EditText
            String newText = postEditText.getText().toString().trim();
            if (imageStringUpload != null) {
                post.setPhoto(imageStringUpload);
            }

            // Update the post content
            post.setText(newText);

            // Update the post in the server
            postsViewModel.update(post);

            // Notify the adapter of the change
            adapter.notifyItemChanged(position);

            finish();


        });
        cancelButton.setOnClickListener(v -> finish());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // method from UploadPicture handle the result
        try {
            uploadPicture.onActivityResult(requestCode, resultCode, data, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //imageUriUpload = uploadPicture.getImageUri();
        imageStringUpload = uploadPicture.getImageBase64();
    }





}
