package com.example.project2_android.Api;

public interface OnResultListener<T> {
    void onSuccess(T result);
    void onFailure(String errorMessage);
}
