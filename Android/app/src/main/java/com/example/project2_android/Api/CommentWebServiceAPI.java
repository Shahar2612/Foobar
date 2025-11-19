package com.example.project2_android.Api;

import com.example.project2_android.Entities.Post;
import com.example.project2_android.Entities.Comment;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentWebServiceAPI {

    @POST("users/{email}/posts/{pid}")
    Call<Comment> addComment(@Header("Authorization") String token, @Path("email") String email,
                              @Path("pid") String id, @Body Comment comment);

    @DELETE("users/{email}/posts/{pid}/{cid}")
    Call<Void> deleteComment(@Header("Authorization") String token, @Path("email") String email,
                          @Path("pid") String pid, @Path("cid") String cid);

    @PATCH("users/{email}/posts/{pid}/{cid}")
    Call<Post> updateComment(@Header("Authorization") String token, @Path("email") String email,
                               @Path("pid") String pid, @Path("cid") String cid ,@Body Comment comment);

    @DELETE("users/{email}/posts/comments/{cid}")
    Call<Post> deleteCommentUser(@Header("Authorization") String token, @Path("email") String email,
                                   @Path("cid") String cid);
}
