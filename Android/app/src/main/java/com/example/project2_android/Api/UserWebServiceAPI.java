package com.example.project2_android.Api;

import com.example.project2_android.Entities.Post;
import com.example.project2_android.Entities.User;
import com.google.gson.JsonObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserWebServiceAPI {

    @POST("users")
    Call<String> createUser(@Body User user);

    @PATCH("users/{email}")
    Call<User> updateUser(@Path("email") String email, @Body User user, @Header("Authorization") String token);

    @GET("users/{email}")
    Call<User> getUser(@Path("email") String email, @Header("Authorization") String token);

    @DELETE("users/{email}")
    Call<Void> deleteUser(@Path("email") String email, @Header("Authorization") String token);

    @POST("users/{email}/friends")
    Call<Void> sendFriendRequest(@Header("Authorization") String token, @Path("email") String email,
                                 @Body JsonObject requestBody);

    @PATCH("users/{email}/friends/{fEmail}")
    Call<Void> acceptFriendRequest(@Header("Authorization") String token, @Path("email") String email, @Path("fEmail") String friendEmail);

    @DELETE("users/{email}/friendRec/{fEmail}")
    Call<Void> declineFriendRequest(@Header("Authorization") String token, @Path("email") String email, @Path("fEmail") String friendEmail);

    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{email}/friends")
    Call<List<User>> getFriends(@Path("email") String email, @Header("Authorization") String token);

    @GET("users/{email}/posts")
    Call<List<Post>> getPostsByUser(@Path("email") String email, @Header("Authorization") String token);

    @DELETE("users/{email}/friends/{fEmail}")
    Call<User> deleteFriend(@Header("Authorization") String token,@Path("email") String email, @Path("fEmail") String friendEmail);
}