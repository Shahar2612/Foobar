package com.example.project2_android.Api;


import com.example.project2_android.Entities.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;

import retrofit2.http.Header;

import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostWebServiceAPI {

    @GET("posts")
    Call<List<Post>> getPosts(@Header("Authorization") String token);

    @POST("users/{email}/posts")
    Call<Post> createPost(@Header("Authorization") String token, @Path("email") String email, @Body Post post);

    @DELETE("users/{email}/posts/{pid}")
    Call<Void> deletePost(@Header("Authorization") String token, @Path("email") String email, @Path("pid") String id);

    @PATCH("users/{email}/posts/{pid}")
    Call<Post> updatePost(@Header("Authorization") String token, @Path("email") String email, @Path("pid") String id, @Body Post post);
}

