package com.example.project2_android.Entities;

import com.example.project2_android.PostsListAdapter;
import com.example.project2_android.ViewModel.PostsViewModel;
import com.example.project2_android.ViewModel.UsersViewModel;

public class DataManager {
    private static PostsListAdapter postsListAdapter;
    private static PostsViewModel postsViewModel;
    private static UsersViewModel usersViewModel;
    private static String token;

    public static PostsListAdapter getPostsListAdapter() {
        return postsListAdapter;
    }

    public static void setPostsListAdapter(PostsListAdapter adapter) {
        postsListAdapter = adapter;
    }

    public static PostsViewModel getPostsViewModel() {
        return postsViewModel;
    }

    public static void setPostsViewModel(PostsViewModel viewModel) {
        postsViewModel = viewModel;
    }

    public static void setUsersViewModel(UsersViewModel viewModel) { usersViewModel = viewModel; }
    public static UsersViewModel getUsersViewModel() { return usersViewModel; }

    public static void setToken(String Token) { token = Token; }
    public static String getToken() { return token; }
}
