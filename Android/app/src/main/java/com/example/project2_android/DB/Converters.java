package com.example.project2_android.DB;

import androidx.room.TypeConverter;

import com.example.project2_android.Entities.Comment;
import com.example.project2_android.Entities.User;
import com.example.project2_android.Entities.Post;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<Comment> fromString(String value) {
        Type listType = new TypeToken<List<Comment>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Comment> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
    @TypeConverter

    public static String fromUserList(ArrayList<User> users) {
        Gson gson = new Gson();
        return gson.toJson(users);
    }

    @TypeConverter
    public static ArrayList<User> toUserList(String usersString) {
        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<User>>() {
        }.getType();
        return gson.fromJson(usersString, userListType);
    }

    @TypeConverter
    public static String fromPostsList(ArrayList<Post> posts) {
        Gson gson = new Gson();
        return gson.toJson(posts);
    }

    @TypeConverter
    public static ArrayList<Post> toPostsList(String postsJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Post>>() {}.getType();
        return gson.fromJson(postsJson, type);

    }

    @TypeConverter
    public static ArrayList<Integer> fromStringToIntList(String value) {
        if (value == null || value.isEmpty()) {
            return new ArrayList<>();
        }
        String[] parts = value.split(",");
        ArrayList<Integer> result = new ArrayList<>();
        for (String part : parts) {
            result.add(Integer.parseInt(part));
        }
        return result;
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Integer> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer item : list) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(item);
        }
        return stringBuilder.toString();
    }

    @TypeConverter
    public static ArrayList<String> fromStringArray(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromStringArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}

