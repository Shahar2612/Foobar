package com.example.project2_android.Api;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TokenAPI {
    Retrofit retrofit;
    TokenWebServiceAPI TokenWebServiceAPI;


    public TokenAPI() {
        retrofit = RetrofitBuilder.getInstance();
        TokenWebServiceAPI = retrofit.create(TokenWebServiceAPI.class);
    }

    public void getToken(String email, String password, final OnResultListener<String> listener) {
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("email", email);
        jsonBody.addProperty("password", password);
        Call<String> call = TokenWebServiceAPI.getToken(jsonBody);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful()) {
                    String jsonString = response.body();
                    JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
                    String token = jsonObject.get("token").getAsString();
                    listener.onSuccess(token);
                    //    getUserDetails(userEmail, userToken, resultListener);

                } else {
                    listener.onFailure(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}