package com.example.project2_android.Api;

import static com.example.project2_android.MyApplication.context;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.project2_android.DB.PostDao;
import com.example.project2_android.DB.UserDao;
import com.example.project2_android.Entities.Comment;
import com.example.project2_android.Entities.Post;
import com.example.project2_android.Entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommentAPI {
    Retrofit retrofit;
    CommentWebServiceAPI commentWebServiceAPI;
    private MutableLiveData<List<Post>> postListData;
    PostDao postDao;
    UserDao userDao;

    public CommentAPI(MutableLiveData<List<Post>> postListData, PostDao postDao, UserDao userDao) {
        this.postListData = postListData;
        this.postDao = postDao;
        this.userDao = userDao;
        retrofit = RetrofitBuilder.getInstance();
        commentWebServiceAPI = retrofit.create(CommentWebServiceAPI.class);
    }

    public void addComment(String token, User activeUser, Post post, Comment comment) {
        Call<Comment> call = commentWebServiceAPI.addComment(token, activeUser.getEmail(), post.getId(), comment);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(@NonNull Call<Comment> call, @NonNull Response<Comment> response) {
                if (response.isSuccessful()) {
                    Comment newComment = response.body();
                    post.addCommentInfo(newComment);
                    post.setComments(post.getComments() + 1);
                    activeUser.addComment(newComment.getId());
                    List<Post> posts = postListData.getValue();
                    if (posts != null) {
                        int index = posts.indexOf(post);
                        posts.set(index, post);
                    }
                    new Handler(Looper.getMainLooper()).post(() -> {
                        postDao.update(post);
                        userDao.update(activeUser);
                        postListData.setValue(posts);
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<Comment> call, @NonNull Throwable t) {
                Log.e("CommentAPI", t.toString());
            }
        });
    }

    public void deleteComment(String token, User activeUser, Post post, Comment comment) {
        Call<Void> call = commentWebServiceAPI.deleteComment(token, activeUser.getEmail(), post.getId(), comment.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    List<Post> posts = postListData.getValue();
                    if (posts != null) {
                        int index = posts.indexOf(post);
                        posts.set(index, post);
                        new Handler(Looper.getMainLooper()).post(() -> {
                            activeUser.removeComment(comment.getId());
                            post.removeCommentInfo(comment);
                            post.setComments(post.getComments() - 1);
                            postDao.update(post);
                            userDao.update(activeUser);
                            postListData.setValue(posts);
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(context, "Failed to delete comment", Toast.LENGTH_SHORT).show();
                    Log.e("CommentAPI", t.toString());
                });


            }
        });
    }

   // this method called only when we need to delete all comments of a user when the user is deleted
    public void deleteCommentUser(String token, String email, String commentID) {
        Call<Post> call = commentWebServiceAPI.deleteCommentUser(token, email, commentID);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.isSuccessful()) {
                    Post post = response.body();
                    List<Post> posts = postListData.getValue();
                    if (posts != null && post != null) {
                        for (int i = 0; i < posts.size(); i++) {
                            if (posts.get(i).getId().equals(post.getId())) {
                                posts.set(i, post);
                                new Handler(Looper.getMainLooper()).post(() -> {
                                    post.setComments(post.getComments() - 1);
                                    post.removeCommentInfoID(commentID);
                                    postDao.update(post);
                                    postListData.setValue(posts);
                                });
                                break;
                            }
                        }
                    }
                    User commenter = userDao.get(email);
                    if (commenter != null) {
                        commenter.removeComment(commentID);
                        userDao.update(commenter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                Log.e("CommentAPI", t.toString());
            }
        });
    }

    public void updateComment(String token, String email, Post post, Comment comment) {
        Call<Post> call = commentWebServiceAPI.updateComment(token, email, post.getId(), comment.getId(), comment);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.isSuccessful()) {
                    Post newPost = response.body();
                    List<Post> posts = postListData.getValue();
                    if (posts != null) {
                        int index = posts.indexOf(post);
                        posts.set(index, newPost);
                    }
                    new Handler(Looper.getMainLooper()).post(() -> {
                        postDao.update(post);
                        postListData.setValue(posts);
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                Log.e("CommentAPI", t.toString());
            }
        });
    }
}
