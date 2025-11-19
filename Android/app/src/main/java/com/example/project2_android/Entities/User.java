package com.example.project2_android.Entities;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class User implements Serializable {
    @SerializedName("_id")
    private String id;
    private String firstName;
    private String lastName;
    @PrimaryKey @NonNull
    private String email;
    private String password;
    private String picture;
    private int pictureInt;
    private ArrayList<String> friends;
    private ArrayList<String> friendRequests;
    private ArrayList<String> posts;
    private String displayName;
    private ArrayList<String> userComments;


    // Constructor
    public User(String firstName, String lastName, @NonNull String email, String password, String picture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = firstName + " " + lastName;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.userComments = new ArrayList<>();

    }

    // Getters
    public String getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getDisplayName() {
        return displayName;
    }
    @NonNull
    public String getEmail() { return email; }
    public String getPassword() {
        return password;
    }
    public String getPicture() {
        return picture;
    }
    public Bitmap getPictureBitmap() {
        return UploadPicture.convertStringToBitmap(picture);
    }
    public ArrayList<String> getPosts() { return posts; }
    public ArrayList<String> getFriends() { return friends; }
    public ArrayList<String> getFriendRequests() { return friendRequests; }
    public ArrayList<String> getUserComments() { return userComments; }




    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    public void setPictureInt(int pictureInt) {
        this.pictureInt = pictureInt;
    }
    public int getPictureInt() {
        return pictureInt;
    }

    public void setFriendRequests(ArrayList<String> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public void addFriendRequest(String friendEmail) {
        if (this.friendRequests == null) {
            this.friendRequests = new ArrayList<>();
        }
        this.friendRequests.add(friendEmail);
    }

    public void setFriends(ArrayList<String> friends) { this.friends = friends; }

    public void addFriend(String friendEmail) {
        if (this.friends == null) {
            this.friends = new ArrayList<>();
        }
        this.friends.add(friendEmail);
    }
    public void setPosts(ArrayList<String> posts) { this.posts = posts; }
    public void addPostId(String postId) {
        if (this.posts == null) {
            this.posts = new ArrayList<>();
        }
        this.posts.add(postId);
    }

    public void removePost(String postId) {
        if (this.posts != null) {
            this.posts.remove(postId);
        }
    }

    public void setUserComments(ArrayList<String> userComments) { this.userComments = userComments; }
    public void addComment(String comment) {
        if (this.userComments == null) {
            this.userComments = new ArrayList<>();
        }
        this.userComments.add(comment);
    }

    public void removeComment(String comment) {
        if (this.userComments != null) {
            this.userComments.remove(comment);
        }
    }

}