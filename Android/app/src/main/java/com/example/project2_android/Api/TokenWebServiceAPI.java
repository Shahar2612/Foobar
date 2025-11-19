package com.example.project2_android.Api;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import com.google.gson.JsonObject;

public interface TokenWebServiceAPI {
    @POST("token")
    Call<String> getToken(@Body JsonObject  requestBody);
}
