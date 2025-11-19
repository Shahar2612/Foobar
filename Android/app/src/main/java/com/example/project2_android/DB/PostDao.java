package com.example.project2_android.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.project2_android.Entities.Post;
import java.util.List;

//Retrieving and updating the database is done via Data Access Objects
@Dao
public interface PostDao {
    @Query("SELECT * FROM post")
    List<Post> index();

    @Query("SELECT * FROM post WHERE id = :id")
    Post get(String id);

    @Query("SELECT * FROM post WHERE email = :email")
    List<Post> getPostsByUser(String email);

    @Insert
    void insert(Post... posts);
    @Insert
    void insert(List<Post> posts);

    @Update
    void update(Post... posts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(Post... posts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(List<Post> posts);

    @Delete
    void delete(Post post);

    @Query("DELETE FROM post")
    void deleteAll();

    @Query("DELETE FROM post WHERE id NOT IN (SELECT id FROM post ORDER BY id DESC LIMIT 25)")
    void keepLatest25Posts();

}