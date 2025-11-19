package com.example.project2_android.Entities;

public class ActiveUser {
    private static User activeUser;

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User activeUser) {
        ActiveUser.activeUser = activeUser;
    }
}
