package com.example.project2_android.Api;

import static com.example.project2_android.Entities.ActiveUser.getActiveUser;
import static com.example.project2_android.Entities.ActiveUser.setActiveUser;
import static com.example.project2_android.MyApplication.context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import retrofit2.Call;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.project2_android.DB.UserDao;
import com.example.project2_android.Entities.ActiveUser;
import com.example.project2_android.Entities.DataManager;
import com.example.project2_android.Entities.Post;
import com.example.project2_android.Entities.User;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserAPI {
    Retrofit retrofit;
    UserWebServiceAPI userWebServiceAPI;
    private final MutableLiveData<List<User>> userListData;
    private final UserDao dao;


    public UserAPI(MutableLiveData<List<User>> userListData, UserDao dao) {
        this.userListData = userListData;
        this.dao = dao;

        retrofit = RetrofitBuilder.getInstance();
        userWebServiceAPI = retrofit.create(UserWebServiceAPI.class);
    }


    public void getUser(String email, String token, Boolean isActiveUser) {
        Call<User> call = userWebServiceAPI.getUser(email, token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    List<User> users = new ArrayList<>(Objects.requireNonNull(userListData.getValue()));

                    // Check if a user with the same email already exists in the list
                    int existingUserIndex = -1;
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).getEmail().equals(user.getEmail())) {
                            existingUserIndex = i;
                            break;
                        }
                    }

                    if (existingUserIndex != -1) {
                        // Replace the existing user with the new one
                        users.set(existingUserIndex, user);
                    } else {
                        // Add the new user if it doesn't exist in the list
                        users.add(0, user);
                    }
                    //List<User> finalUsers = users;
                    new Handler(Looper.getMainLooper()).post(() -> {
                        dao.insertOrUpdate(user);
                        if (isActiveUser) {
                            setActiveUser(user);
                        }
                        userListData.postValue(users);

                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d("Error get user", Objects.requireNonNull(t.getMessage()));
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(context,
                        "Something went wrong, please try again", Toast.LENGTH_SHORT).show());
            }
        });
    }



    public void createUser(final User user, final OnResultListener<String> listener) {
        Call<String> call = userWebServiceAPI.createUser(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful()) {
                    dao.insert(user);
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public void delete(final User user) {
        Call<Void> call = userWebServiceAPI.deleteUser(user.getEmail(), DataManager.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                new Thread(() -> dao.delete(user)).start();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("Error delete user", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void update(final User user) {
        Call<User> call = userWebServiceAPI.updateUser(user.getEmail(), user, DataManager.getToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        User updatedUserServer = response.body();
                        dao.insertOrUpdate(updatedUserServer);
                        setActiveUser(updatedUserServer);
                        //updatedUserServer.setToken(user.getToken());
                        List<User> users = userListData.getValue();
                        if (users != null) {
                            for (int i = 0; i < users.size(); i++) {
                                if (users.get(i).getEmail().equals(user.getEmail())) {
                                    users.set(i, updatedUserServer);
                                    break;
                                }
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d("Error update user", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void sendFriendRequest(User friend) {
        User activeUser = getActiveUser();
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("friendEmail", friend.getEmail());
        Call<Void> call = userWebServiceAPI.sendFriendRequest(DataManager.getToken(),
                activeUser.getEmail(), jsonBody);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                    friend.addFriendRequest(activeUser.getEmail());
                    dao.update(friend);
                    }).start();
                    Log.d("Friend request sent", "Friend request sent");
                }

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("Error send Frequest", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void declineFriendRequest(User friend) {
        User activeUser = getActiveUser();
        Call<Void> call = userWebServiceAPI.declineFriendRequest(DataManager.getToken(), activeUser.getEmail(), friend.getEmail());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    activeUser.getFriendRequests().remove(friend.getEmail());
                    dao.update(activeUser);
                    Log.d("Friend request declined", "Friend request declined");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("Error decline Frequest", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void acceptFriendRequest(User friend) {
        User activeUser = getActiveUser();
        Call<Void> call = userWebServiceAPI.acceptFriendRequest(DataManager.getToken(), activeUser.getEmail(), friend.getEmail());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    activeUser.getFriendRequests().remove(friend.getEmail());
                    friend.addFriend(activeUser.getEmail());
                    activeUser.addFriend(friend.getEmail());
                    //setActiveUser(activeUser);
                    dao.update(friend);
                    dao.update(activeUser);
                    Log.d("Friend request accepted", "Friend request accepted");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("Error accept Frequest", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getUsers( ) {
        Call<List<User>> call = userWebServiceAPI.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                List<User> users = response.body();
                new Handler(Looper.getMainLooper()).post(() -> {
                    userListData.setValue(users); //postValue?
                });
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.d("Error get users", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void getPostsByUser(User user) {
        Call<List<Post>> call = userWebServiceAPI.getPostsByUser(user.getEmail(), DataManager.getToken());
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                List<Post> posts = response.body();
                ArrayList<String> postIds = new ArrayList<>();
                for (Post post : posts) {
                    postIds.add(post.getId());
                }
                new Handler(Looper.getMainLooper()).post(() -> {
                    user.setPosts(postIds);
                    dao.insertOrUpdate(user);
                });
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                Log.d("Error get posts by user", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void deleteFriend(String friendEmail) {
        Call<User> call = userWebServiceAPI.deleteFriend(DataManager.getToken(),
                ActiveUser.getActiveUser().getEmail(), friendEmail);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User updatedFriend = response.body();
                    User activeUser = getActiveUser();
                    activeUser.getFriends().remove(friendEmail);
                    dao.insertOrUpdate(updatedFriend);
                    dao.update(activeUser);
                    Log.d("Friend deleted", "Friend deleted");
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d("Error delete friend", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}