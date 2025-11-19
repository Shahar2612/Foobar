package com.example.project2_android.DB;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.project2_android.Entities.User;
import com.example.project2_android.Entities.Post;
import com.example.project2_android.MyApplication;

//Managing access to the DB is via dedicated class using @Database
@Database(entities = {Post.class, User.class}, version = 13)
@TypeConverters({Converters.class})
public abstract class AppDb extends RoomDatabase {

    public abstract PostDao postDao();
    public abstract UserDao userDao();

    private static volatile AppDb INSTANCE;

    public static AppDb getInstance() {
        if (INSTANCE == null) {
            synchronized (AppDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(MyApplication.context,
                            AppDb.class, " appDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
