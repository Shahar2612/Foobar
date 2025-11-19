package com.example.project2_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.project2_android.Entities.ActiveUser.getActiveUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_android.DB.AppDb;
import com.example.project2_android.DB.PostDao;
import com.example.project2_android.Entities.DataManager;
import com.example.project2_android.Entities.Dialogs;
//import com.example.project2_android.Entities.NightModeUtil;
import com.example.project2_android.Entities.Post;
import com.example.project2_android.Entities.UploadPicture;
import com.example.project2_android.Entities.User;
import com.example.project2_android.ViewModel.PostsViewModel;
import com.example.project2_android.ViewModel.UsersViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    String friendName;
    String friendEmail;
    String profilePic;
    private AppDb localDb;
    private PostDao postDao;
    private List<Post> posts;
    private PostsListAdapter adapter;
    private UsersViewModel usersViewModel;
    private PostsViewModel postsViewModel;
    private User activeUser;
    private User friend;
    private boolean clicked = false;
    private UploadPicture uploadPicture;
    Button btnFriends;
    private boolean isFetchingFriendRequests = false;
    private boolean isFetchingProfile = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        usersViewModel = DataManager.getUsersViewModel();
        postsViewModel = DataManager.getPostsViewModel();
        localDb = AppDb.getInstance();
        postDao = localDb.postDao();
        Intent intent = getIntent();
        if (intent != null) {
            friendName = intent.getStringExtra("friendDisplayName");
            friendEmail = intent.getStringExtra("email");
            TextView name = findViewById(R.id.username);
            name.setText(friendName);
            profilePic = intent.getStringExtra("profilePic");
            Bitmap profilePicBitmap = UploadPicture.convertStringToBitmap(profilePic);
            ImageView profilePicView = findViewById(R.id.profilePic);
            profilePicView.setImageBitmap(profilePicBitmap);
            AsyncTask.execute(() ->getUserByEmail(friendEmail));
            activeUser = getActiveUser();

            btnFriends = findViewById(R.id.friends);

        }

        showPopupMenu();
        backToFeed();
        friends();
        showFriendRequests();

    }

    private void backToFeed() {
        ImageButton btnFeed = findViewById(R.id.feed);
        btnFeed.setOnClickListener(v -> {
            finish();
        //    Intent intent = new Intent(this, HomePageActivity.class);
       //     startActivity(intent);
        });
    }

    // Get the user by their email name from the server
    private void getUserByEmail(String email) {
        usersViewModel.getUser(email, DataManager.getToken(), false);
        usersViewModel.getUser(activeUser.getEmail(), DataManager.getToken(), false);
        runOnUiThread(() -> {
            usersViewModel.getAll().observe(this, users -> {
                for (User user : users) {
                    if (user.getEmail().equals(email)) {
                        friend = user;
                        showPosts();
                        addFriend();
                        break;
                    }
                }
            });
        });
    }

    private void showPosts() {
        if(friend != null && (friend.getEmail().equals(activeUser.getEmail())
                || activeUser.getFriends().contains(friend.getEmail()))) {
            usersViewModel.getPostsByUser(friend);
            RecyclerView lstPosts = findViewById(R.id.lstPosts);
            List<Post> posts = postDao.getPostsByUser(friend.getEmail());
            adapter = new PostsListAdapter(this, activeUser, posts, postsViewModel, this, uploadPicture);
             lstPosts.setAdapter(adapter);
            lstPosts.setLayoutManager(new LinearLayoutManager(this));
            //posts = SortPosts(posts);
            //adapter.setPosts(posts);
            //setupLikeButton(adapter);

            postsViewModel.getLiveFriendPosts(friend).observe(this, sPosts -> {
                sPosts = SortPosts(sPosts);
                adapter.setPosts(sPosts);
                adapter.notifyDataSetChanged();
            });
        }
    }

    private List<Post> SortPosts(List<Post> posts) {
        if (posts != null) {
            Collections.sort(posts, (o1, o2) -> o2.getDateFormat().compareTo(o1.getDateFormat()));
        }
        return posts;
    }

    private void setupLikeButton(PostsListAdapter adapter) {
        adapter.setOnLikeButtonClickListener(position -> {
            // Get the current post
            Post currentPost = adapter.getPosts().get(position);
            // Toggle the like state
            if (currentPost.isLiked()) {
                // If already liked, subtract 1 from likes count
                currentPost.setLike(currentPost.getLike() - 1);
            } else {
                // If not liked, add 1 to likes count
                currentPost.setLike(currentPost.getLike() + 1);
            }
            // Toggle the like state
            currentPost.setLiked(!currentPost.isLiked());
            postsViewModel.update(currentPost);

        });
    }


    private void addFriend() {
        Button btnAddFriend = findViewById(R.id.addFriend);
        if(activeUser.getEmail().equals(friendEmail)){
            btnAddFriend.setVisibility(Button.GONE);
        }

        List<String> friends = activeUser.getFriends();
        if (friend.getFriendRequests().contains(activeUser.getEmail())) {
            setPending(btnAddFriend);
        }
        if (activeUser.getFriendRequests().contains(friend.getEmail())) {
            btnAddFriend.setText(R.string.pending_approval);
            btnAddFriend.setBackgroundColor(getResources().getColor(R.color.light_grey));
            btnAddFriend.setTextColor(getResources().getColor(R.color.grey));
            btnAddFriend.setEnabled(false);
        }
        if(!friends.contains(friendEmail)){
            btnFriends.setVisibility(Button.GONE);
        } else {
            btnAddFriend.setEnabled(true);
            btnAddFriend.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btnAddFriend.setTextColor(getResources().getColor(R.color.white));
            btnAddFriend.setText(R.string.cancel_friendship);
        }



        btnAddFriend.setOnClickListener(v -> {
            //cancel friendship
            if (btnAddFriend.getText().equals("Cancel Friendship")) {
                Toast.makeText(this, "Friendship cancelled", Toast.LENGTH_SHORT).show();
                btnAddFriend.setText(R.string.add_friend);
                btnAddFriend.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnAddFriend.setEnabled(true);
                clicked = true;
                btnFriends.setVisibility(Button.GONE);
                adapter.setPosts(new ArrayList<>());
                usersViewModel.deleteFriend(friend.getEmail());
            }
            //send friend request
            if (!clicked) {
                setPending(btnAddFriend);
                usersViewModel.sendFriendRequest(friend);
                Toast.makeText(this, "Friend request sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPending(Button btnAddFriend) {
        btnAddFriend.setBackgroundColor(getResources().getColor(R.color.light_grey));
        btnAddFriend.setTextColor(getResources().getColor(R.color.grey));
        btnAddFriend.setText(R.string.pending);
        // Disable further clicks
        btnAddFriend.setEnabled(false);
        clicked = true;
    }

    private void friends() {
        btnFriends.setOnClickListener(v -> {
            //AsyncTask.execute(() -> usersViewModel.getUser(friend.getEmail(), DataManager.getToken(), false));
            List<String> friends = friend.getFriends();
            Dialogs.showFriendsDialog(this, friends);
        });
    }


    private void showFriendRequests() {
        ImageButton btnFriendRequests = findViewById(R.id.friends2);
        btnFriendRequests.setOnClickListener(v -> {
            //get the updated version of the user
            AsyncTask.execute(() -> usersViewModel.getUser(activeUser.getEmail(), DataManager.getToken(), false));
            List<String> requests = activeUser.getFriendRequests();
            int size = requests.size();
            ArrayList<User> requestsUsers = new ArrayList<>();
            //run on the saved users- so we don't request those from the server
            for (User user : usersViewModel.getAll().getValue()) {
                if (requests.contains(user.getEmail())) {
                    requestsUsers.add(user);
                    requests.remove(user.getEmail());
                }
            }
            //if there are users in the requests- request them from the server
            isFetchingFriendRequests = true;
            if (requestsUsers.size() != size) {
                for (String request : requests) {
                    usersViewModel.getUser(request, DataManager.getToken(), false);
                }
                usersViewModel.getAll().observe(this, users -> {
                    //because there is another observer in the activity-
                    // we need to check if the wanted request is made
                    if (isFetchingFriendRequests) {
                        isFetchingFriendRequests = false;
                        for (User user : users) {
                            if (requests.contains(user.getEmail())) {
                                requestsUsers.add(user);
                            }
                        }
                    }
                });
            }
            Dialogs.showFriendRequestsDialog(v.getContext(), requestsUsers);
        });
    }

    private void showPopupMenu() {
        ImageButton btnLogOut = findViewById(R.id.menu);
        btnLogOut.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.inflate(R.menu.profile_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.logOut) {
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                if (item.getItemId() == R.id.editUser) {
                    Intent intent = new Intent(this, UpdateUserActivity.class);
                    startActivity(intent);
                }
                return true;
            });
            popupMenu.show();
        });
    }

}
