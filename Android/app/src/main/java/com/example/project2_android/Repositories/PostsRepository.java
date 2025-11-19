package com.example.project2_android.Repositories;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.project2_android.Api.CommentAPI;
import com.example.project2_android.Api.PostAPI;
import com.example.project2_android.DB.AppDb;
import com.example.project2_android.DB.UserDao;
import com.example.project2_android.Entities.Comment;
import com.example.project2_android.Entities.DataManager;
import com.example.project2_android.Entities.Post;

import com.example.project2_android.DB.PostDao;

import com.example.project2_android.Entities.User;
import com.example.project2_android.Entities.ActiveUser;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PostsRepository {
    private final PostDao postDao;
    private final UserDao userDao;
    private final PostListData postListData;
    private final PostAPI postAPI;
    private final CommentAPI commentAPI;
    private final User activeUser;

    public PostsRepository() {
        AppDb db = AppDb.getInstance();
        postDao = db.postDao();
        userDao = db.userDao();
        activeUser = ActiveUser.getActiveUser();
        postListData = new PostListData();
        commentAPI = new CommentAPI(postListData, postDao, userDao);
        postAPI = new PostAPI(postListData, postDao, userDao);

    }

    class PostListData extends MutableLiveData<List<Post>> {

        public PostListData() {
            super();
            new Thread(() -> {
            List<Post> posts = postDao.index();
            List<Post> friendsPosts = new ArrayList<>();
            List<Post> otherPosts = new ArrayList<>();

            if (activeUser != null && posts != null) {
                // Separate friends' posts from others
                for (Post post : posts) {
                    if (activeUser.getFriends() != null && (activeUser.getFriends().contains(post.getEmail())
                            || post.getEmail().equals(activeUser.getEmail()))) {
                        friendsPosts.add(post);
                    } else {
                        otherPosts.add(post);
                    }
                }
            }

                // Prioritize friends' posts
                List<Post> combinedPosts = new ArrayList<>(friendsPosts);
                combinedPosts.addAll(otherPosts);
                postDao.deleteAll();
                postDao.insert(combinedPosts);
                postDao.keepLatest25Posts();
                postValue(combinedPosts);
                }).start();
            }


        @Override
        protected void onActive() {
            super.onActive();
            postValue(postDao.index());
            postAPI.get(DataManager.getToken());
        }

    }

    public LiveData<List<Post>> getAll() {
        return postListData;
    }

    public void add(final Post post) {
        postAPI.add(DataManager.getToken(), activeUser, post);
    }

    public void delete(final Post post) {
        postAPI.delete(DataManager.getToken(), activeUser, post);
    }

    public void update(final Post post) {
        postAPI.update(DataManager.getToken(), activeUser.getEmail(), post);
    }

    public void addComment(Post post, Comment comment) {
        commentAPI.addComment(DataManager.getToken(), activeUser, post, comment);
    }

    public void deleteComment(final Post post, final Comment comment) {
        commentAPI.deleteComment(DataManager.getToken(), activeUser, post, comment);
    }

    public void deleteCommentUser(final String commentID) {
        commentAPI.deleteCommentUser(DataManager.getToken(), activeUser.getEmail(), commentID);
    }

    public void updateComment(final Post post, final Comment comment) {
        commentAPI.updateComment(DataManager.getToken(), activeUser.getEmail(), post, comment);
    }

    public LiveData<List<Post>> getLiveFriendPosts(User friend) {
        List<Post> friendPosts = new ArrayList<>();
        //filter posts by friend
        for(Post post : postListData.getValue()){
            if(post.getEmail().equals(friend.getEmail())){
                friendPosts.add(post);
            }
        }
        return new MutableLiveData<>(friendPosts);
    }
    public void reload() {
        new GetPostsTask(postAPI, postListData, postDao).execute();
    }

    public class GetPostsTask extends AsyncTask<Void, Void, Void> {
        private final PostAPI postAPI;

        public GetPostsTask(PostAPI postAPI, MutableLiveData<List<Post>> postListData, PostDao dao) {
            this.postAPI = postAPI;

        }

        @Override
        protected Void doInBackground(Void... params) {
            postAPI.get(DataManager.getToken());
            return null;
        }
    }

}