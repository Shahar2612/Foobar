package com.example.project2_android.Entities;


import android.graphics.Bitmap;


import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("_id")
    private String id;
    private String name;
    private String text;
    private String email;
    private String photo;
    private User commenter;
    private Post post;
    private String postId;

    public Comment(User commenter, String commentText, Post post) {
        this.commenter = commenter;
        this.text = commentText;
        this.name = commenter.getDisplayName();
        this.email = commenter.getEmail();
        this.photo = commenter.getPicture();
        this.post = post;
        this.postId = post.getId();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public Bitmap getProfilePicBitmap() { return UploadPicture.convertStringToBitmap(photo); }
    public void setPhoto(String photo) { this.photo = photo; }
    public Post getPost() { return this.post; }

    @NonNull
    @Override
    public String toString() {
        return photo + name + '\n' + text;
    }
}
