package com.example.project2_android;
import static com.example.project2_android.Entities.ActiveUser.getActiveUser;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;

import com.example.project2_android.DB.AppDb;
import com.example.project2_android.DB.PostDao;
import com.example.project2_android.Entities.DataManager;
import com.example.project2_android.Entities.Dialogs;
import com.example.project2_android.Entities.Post;
import com.example.project2_android.Entities.UploadPicture;
import com.example.project2_android.Entities.User;
import com.example.project2_android.ViewModel.PostsViewModel;
import com.example.project2_android.ViewModel.UsersViewModel;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This activity represents the home page of the application, where users can view and interact with posts.
 */
public class HomePageActivity extends AppCompatActivity {
    private User activeUser;
    private Uri imageUriUpload;
    private String imageStringUpload;
    private UploadPicture uploadPicture;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<Post> posts;
    private PostsViewModel viewModel;
    private UsersViewModel usersViewModel;
    private boolean newPost = false;
    private static final String HOME_NIGHT_MODE_PREF = "HomeNightMode";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_feed2);
        activeUser = getActiveUser();

        ImageView profilePicView = findViewById(R.id.profilePic);
        profilePicView.setImageBitmap(activeUser.getPictureBitmap());

        // Initialize RecyclerView for displaying posts
        viewModel = new ViewModelProvider(this).get(PostsViewModel.class);

        usersViewModel = DataManager.getUsersViewModel();

        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        final PostsListAdapter adapter = new PostsListAdapter(this, activeUser, posts,
                viewModel, this, uploadPicture);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> viewModel.reload());

        viewModel.get().observe(this, posts -> {
            posts = SortPosts(posts);
            adapter.setPosts(posts);
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
            if (newPost) {
                adapter.notifyItemInserted(0);
                lstPosts.scrollToPosition(0);
                newPost = false;
            }
        });


        // Set up click listener for the close button
        showPopupMenu();

        // Set up click listener for the "like" button on each post
        setupLikeButton(adapter);

        // Set up click listener for the "search" button
        setupSearchButton();

        // Set up click listener for the "select image" button
        setupSelectImageButton();

        // Set up click listener for the "post" button to add a new post
        setUpAddPost();

        //dark mode
        setupDarkModeSwitch();

        //friend requests
        showFriendRequests();
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
            viewModel.update(currentPost);

            // Notify the adapter that data has changed
            //   adapter.notifyItemChanged(position);
        });
    }

    private void setupSearchButton() {
        View btnSearch = findViewById(R.id.search_icon);
        btnSearch.setOnClickListener(v -> Dialogs.showSearchDialog(v.getContext()));
    }


    private void setupSelectImageButton() {
        ImageButton btnSelectImage = findViewById(R.id.btnAddPicture);
        ImageView imageUpload = new ImageView(this);
        uploadPicture = new UploadPicture(imageUpload, imageUriUpload);
        btnSelectImage.setOnClickListener(v -> uploadPicture.showPopupMenu(v, this, this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // method from UploadPicture handle the result
        try {
            uploadPicture.onActivityResult(requestCode, resultCode, data, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //imageUriUpload = uploadPicture.getImageUri();
        imageStringUpload = uploadPicture.getImageBase64();
    }

    //add post
    private void setUpAddPost() {
        View btnAddPost = findViewById(R.id.btnPost);
        btnAddPost.setOnClickListener(v -> {
            EditText etPostContent = findViewById(R.id.status);
            String postContent = etPostContent.getText().toString().trim();

            //close the keyboard
            View view = HomePageActivity.this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            if (!postContent.isEmpty() || imageStringUpload != null) {
                Post post = new Post(activeUser, postContent, imageStringUpload);
                newPost = true;
                viewModel.add(post);

                // Clear the input field
                etPostContent.setText("");
                imageStringUpload = " ";
            }
        });
    }

    private List<Post> SortPosts(List<Post> posts) {
        if (posts != null) {
            Collections.sort(posts, (o1, o2) -> o2.getDateFormat().compareTo(o1.getDateFormat()));
        }
        return posts;
    }

    //dark mode
    private void setupDarkModeSwitch() {
        Switch switcher = findViewById(R.id.switchMode);

        switcher.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
        switcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int newNightMode = isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;

            if (AppCompatDelegate.getDefaultNightMode() != newNightMode) {
                // Only recreate if the night mode has changed
                AppCompatDelegate.setDefaultNightMode(newNightMode);
                recreate();
            }
        });
    }

    // bottom menu for log out
    private void showPopupMenu() {
        ImageButton btnLogOut = findViewById(R.id.menu);
        btnLogOut.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.inflate(R.menu.log_out_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.logOut) {
                    finish();
                }
                if (item.getItemId() == R.id.editUser) {
                    Intent intent = new Intent(this, UpdateUserActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.deleteUser) {
                    PostDao postDao = AppDb.getInstance().postDao();
                    // unfriend all the user's friends
                    List<String> friends = activeUser.getFriends();
                    for (String friend : friends) {
                        usersViewModel.deleteFriend(friend);
                    }
                    //delete all the user's comments
                    List<String> comments = activeUser.getUserComments();
                    for (String commentId : comments) {
                        viewModel.deleteCommentUser(commentId);
                    }
                    //delete all the user's posts
                    List<String> posts = activeUser.getPosts();

                    for (String postId : posts) {
                        Post post = postDao.get(postId);
                        viewModel.delete(post);
                    }
                    //delete the user
                    usersViewModel.delete(activeUser);
                    finish();
                }
                return true;
            });
            popupMenu.show();
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
            for (User user : Objects.requireNonNull(usersViewModel.getAll().getValue())) {
                if (requests.contains(user.getEmail())) {
                    requestsUsers.add(user);
                    requests.remove(user.getEmail());
                }
            }
            //if there are users in the requests- request them from the server
            if (requestsUsers.size() != size) {
                for (String request : requests) {
                    usersViewModel.getUser(request, DataManager.getToken(), false);
                }
                usersViewModel.getAll().observe(this, users -> {
                    for (User user : users) {
                        if (requests.contains(user.getEmail())) {
                            requestsUsers.add(user);
                            //activeUser.addFriendRequestUsers(user);
                        }
                    }
                });
            }
            Dialogs.showFriendRequestsDialog(v.getContext(), requestsUsers);
        });
    }
}
