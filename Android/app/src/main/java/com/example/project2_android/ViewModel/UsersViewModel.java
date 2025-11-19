package com.example.project2_android.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;

import com.example.project2_android.Api.OnResultListener;
import com.example.project2_android.Entities.DataManager;
import com.example.project2_android.Entities.User;
import com.example.project2_android.Repositories.UsersRepository;


import java.util.List;

public class UsersViewModel extends ViewModel{
    private final UsersRepository mRepository;
    private final LiveData<List<User>> users;

    public UsersViewModel(){
        super();
        DataManager.setUsersViewModel(this);
        mRepository = new UsersRepository();
        users = mRepository.getAll();
    }

    public LiveData<List<User>> getAll() { return users; }

    public void getUser(String email, String token, Boolean isActiveUser) {
        mRepository.getUser(email, token, isActiveUser);
    }

    public void add(User user, OnResultListener<String> userListener){
        mRepository.add(user, userListener);
    }

    public void delete(User user){
        mRepository.delete(user);
    }
    public void update(User user){
        mRepository.update(user);
    }

    public void reload() {
        mRepository.reload();
    }

    public void sendFriendRequest(User friend) {
        mRepository.sendFriendRequest(friend);
    }

    public void acceptFriendRequest(User friend) {
        mRepository.acceptFriendRequest(friend);
    }

    public void declineFriendRequest(User friend) {
        mRepository.declineFriendRequest(friend);
    }

    public void getPostsByUser(User user) {
        mRepository.getPostsByUser(user);
    }

    public void deleteFriend(String friendEmail) {
        mRepository.deleteFriend(friendEmail);
    }

}
