package com.example.project2_android.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@Entity
public class Post {
    @PrimaryKey @NonNull
    @SerializedName("_id")
    private String id;
    private int like, comments;
    private String name, text, photo = "", email;
    private Date date;
    private String userPic;
    private boolean isLiked;
    private List<Comment> commentsInfo;


    public Post(String name, String text, String photo) {
        this.name = name;
        this.text = text;
        this.photo = photo;
        this.date = new Date();

    }
    //constructor for new post
    public Post(User user, String text, String photo) {
        this.name = user.getDisplayName();
        this.text = text;
        this.photo = photo;
        this.date = new Date();
        this.userPic = user.getPicture();
        this.email = user.getEmail();
        this.commentsInfo = new ArrayList<>();
    }

    @NonNull
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getText(){
        return text;
    }
    public int getLike(){
        return like;
    }
    public String getPhoto(){ return photo; }
    public int getComments(){
        return comments;
    }
    public String getUserPic(){ return userPic; }
    public Date getDate() { return date; }
    public String getDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        return dateFormat.format(date);
    }
    public String getEmail(){ return email; }
    public List<Comment> getCommentsInfo(){
        return commentsInfo;
    }


    public void setId(@NonNull String id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setText(String content){
        this.text = content;
    }
    public void setLike(int like){
        this.like = like;
    }
    public void setComments(int comments){
        this.comments = comments;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) {
        isLiked = liked;
    }
    public void setCommentsInfo(List<Comment> commentsInfo){
        this.commentsInfo = commentsInfo;
    }
    public void addCommentInfo(Comment comment){
        if (commentsInfo == null) {
            commentsInfo = new ArrayList<>();
        }
        commentsInfo.add(comment);
    }
    public void removeCommentInfo(Comment comment){ commentsInfo.remove(comment); }
    public void removeCommentInfoID(String commentID){
        for (int i = 0; i < commentsInfo.size(); i++) {
            if (commentsInfo.get(i).getId().equals(commentID)) {
                commentsInfo.remove(i);
                break;
            }
        }
    }
    public void setUserPic(String userPic){
        this.userPic = userPic;
    }
    public void setPhoto(String photo){
        this.photo = photo;
}
    public void setEmail(String email){ this.email = email; }

}