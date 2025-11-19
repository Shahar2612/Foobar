package com.example.project2_android.Repositories;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.project2_android.Api.OnResultListener;
//import com.example.project2_android.AppDb;
import com.example.project2_android.Api.UserAPI;
import com.example.project2_android.DB.AppDb;
import com.example.project2_android.Entities.User;
import com.example.project2_android.DB.UserDao;
//import com.example.project2_android.PostDao;

import java.util.LinkedList;
import java.util.List;

public class UsersRepository {
    private final UserDao dao;
    private final UserListData userListData;
    private final UserAPI userAPI;

    public UsersRepository() {
        AppDb db = AppDb.getInstance();
        dao = db.userDao();
        userListData = new UserListData();
        userAPI = new UserAPI(userListData, dao);
    }

    class UserListData extends MutableLiveData<List<User>> {

        public UserListData() {
            super();
            postValue(new LinkedList<>());

        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                postValue(dao.index());
            }).start();
        }

    }

    public LiveData<List<User>> getAll() {
        return userListData;

    }

    public void getUser(String email, String token, Boolean isActiveUser) {
        userAPI.getUser(email, token, isActiveUser);
    }

    public void add(final User user, OnResultListener<String> userListener) {
        userAPI.createUser(user, userListener);
    }

    public void delete(final User user) {
        userAPI.delete(user);
    }

    public void reload() {
        new GetPostsTask(userAPI, userListData, dao).execute();
    }

    public void update(User user) {
        userAPI.update(user);
    }

    public void sendFriendRequest(User friend) {
        userAPI.sendFriendRequest(friend);
    }

    public void acceptFriendRequest(User friend) {
        userAPI.acceptFriendRequest(friend);
    }

    public void declineFriendRequest(User friend) {
        userAPI.declineFriendRequest(friend);
    }

    public void getPostsByUser(User user) {
        userAPI.getPostsByUser(user);
    }

    public void deleteFriend(String friendEmail) {
        userAPI.deleteFriend(friendEmail);
    }

    public class GetPostsTask extends AsyncTask<Void, Void, Void> {
        private final UserAPI userAPI;

        public GetPostsTask(UserAPI userAPI, MutableLiveData<List<User>> userListData, UserDao dao) {
            this.userAPI = userAPI;
        }

        @Override
        protected Void doInBackground(Void... params) {
            userAPI.getUsers();
            return null;
        }

    }
}