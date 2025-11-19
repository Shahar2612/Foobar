package com.example.project2_android.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import com.example.project2_android.Entities.Comment;
import com.example.project2_android.Entities.DataManager;
import com.example.project2_android.Entities.Post;
import com.example.project2_android.Entities.User;
import com.example.project2_android.Repositories.PostsRepository;


import java.util.List;

public class PostsViewModel extends ViewModel{
    private final PostsRepository mRepository;
    private final LiveData<List<Post>> posts;


    public PostsViewModel(){
        super();
        mRepository = new PostsRepository();
        DataManager.setPostsViewModel(this);
        posts = mRepository.getAll();
    }

    public LiveData<List<Post>> get(){
        return posts;
    }

    public void add(Post post){
        mRepository.add(post);
    }

    public void delete(Post post){
        mRepository.delete(post);
    }

    public void update(Post post){ mRepository.update(post); }

    public void addComment(Post post, Comment comment){ mRepository.addComment(post, comment); }

    public void deleteComment(Post post, Comment comment){
        mRepository.deleteComment(post, comment); }

    public void deleteCommentUser(String commentID){
        mRepository.deleteCommentUser(commentID);
    }

    public void updateComment(Post post, Comment comment){
        mRepository.updateComment(post, comment);
    }

    public void reload() {
        mRepository.reload();
    }

    public LiveData<List<Post>> getLiveFriendPosts(User friend){
        return mRepository.getLiveFriendPosts(friend);
    }

}