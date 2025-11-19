package com.example.project2_android.Api;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;



import retrofit2.Call;

import com.example.project2_android.DB.PostDao;
import com.example.project2_android.DB.UserDao;
import com.example.project2_android.Entities.ActiveUser;
import com.example.project2_android.Entities.Post;
import com.example.project2_android.Entities.User;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PostAPI {
  
    private final PostDao postDao;
    private final UserDao userDao;
    Retrofit retrofit;
    PostWebServiceAPI postWebServiceAPI;

    private final MutableLiveData<List<Post>> postListData;

    public PostAPI(MutableLiveData<List<Post>> postListData, PostDao postDao, UserDao userDao) {
        this.postListData = postListData;
        this.postDao = postDao;
        this.userDao = userDao;

        retrofit = RetrofitBuilder.getInstance();
        postWebServiceAPI = retrofit.create(PostWebServiceAPI.class);
    }

    public void get(String token) {
        Call<List<Post>> call = postWebServiceAPI.getPosts(token);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                List<Post> ServerPosts = response.body();
                postDao.insertOrUpdate(ServerPosts);
                //get the combined posts from the database
                List<Post> posts = postDao.index();

                new Handler(Looper.getMainLooper()).post(() -> {
                    postListData.setValue(posts);
                });
            }


            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                //Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.d("Error get posts", Objects.requireNonNull(t.getMessage()));
            }
        });
    }


    public void add(String token, User activeUser, final Post post) {

        Call<Post> call = postWebServiceAPI.createPost(token, activeUser.getEmail(), post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.isSuccessful()) {
                    Post newPost = response.body();
                    activeUser.addPostId(newPost.getId());
                    List<Post> posts = postListData.getValue();
                    if (posts != null) {
                        posts.add(0, newPost);
                    }
                    new Handler(Looper.getMainLooper()).post(() -> {
                        userDao.update(activeUser);
                        postDao.insert(newPost);
                        postListData.setValue(posts);

                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
               // Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.d("Error add post", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void delete(String token, User activeUser, final Post post) {
        Call<Void> call = postWebServiceAPI.deletePost(token, activeUser.getEmail(), post.getId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    activeUser.removePost(post.getId());
                    List<Post> posts = postListData.getValue();
                    if (posts != null) {
                        posts.remove(post);
                    }
                    new Handler(Looper.getMainLooper()).post(() -> {
                        userDao.update(activeUser);
                        postDao.delete(post);
                        postListData.setValue(posts);
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.d(TAG, "Executing on thread delete failure: " + Thread.currentThread().getName());
                } else if (t instanceof IOException) {

                    //  Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.d("Error delete post", Objects.requireNonNull(t.getMessage()));
                }
            }
        });
    }

    public void update(String token, String email, final Post post) {
        boolean isLiked = post.isLiked();
        Call<Post> call = postWebServiceAPI.updatePost(token, email, post.getId(), post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.isSuccessful()) {
                    Post newPost = response.body();
                    if (newPost != null) {
                        newPost.setLiked(isLiked);
                    }
                    List<Post> posts = postListData.getValue();
                    if (posts != null) {
                        for (int i = 0; i < posts.size(); i++) {
                            if (posts.get(i).getId().equals(post.getId())) {
                                posts.set(i, newPost);
                                break;
                        }
                    }
                    }
                    new Handler(Looper.getMainLooper()).post(() -> {
                        postDao.insertOrUpdate(newPost);
                        postListData.setValue(posts);
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                Log.d("Error update post", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}

