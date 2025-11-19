package com.example.project2_android.DB;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2_android.Entities.User;

import java.util.List;
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> index();

    @Query("SELECT * FROM user WHERE email = :email")
    User get(String email);

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(User... users);

    @Delete
    void delete(User... users);


    @Query("DELETE FROM user")
    void deleteAll();

    @Insert
    void insert(List<User> result);

}
