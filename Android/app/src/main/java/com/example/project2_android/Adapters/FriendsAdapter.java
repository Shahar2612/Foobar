package com.example.project2_android.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.project2_android.Entities.DataManager;
import com.example.project2_android.Entities.User;
import com.example.project2_android.ProfileActivity;
import com.example.project2_android.R;
import com.example.project2_android.ViewModel.UsersViewModel;
import java.util.List;

public class FriendsAdapter extends ArrayAdapter<String> {
    private final Context mContext;
    private final int mResource;
    private final List<String> friends;
    private final UsersViewModel usersViewModel;

    public FriendsAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        friends = objects;
        usersViewModel = DataManager.getUsersViewModel();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }
        String email = friends.get(position);
        User friend = null;
        List<User> users = usersViewModel.getAll().getValue();
        if(users!=null) {
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    friend = user;
                    break;
                }
            }
        }
        ImageButton profilePicImageButton = convertView.findViewById(R.id.profilePicImageViewFriend);
        Button friendName = convertView.findViewById(R.id.name);
        TextView noFriendsTextView = convertView.findViewById(R.id.noFriendsTextView);

        if (friends.isEmpty()) {
            noFriendsTextView.setVisibility(View.VISIBLE);
            profilePicImageButton.setVisibility(View.GONE);
            friendName.setVisibility(View.GONE);
        }
        else {
            noFriendsTextView.setVisibility(View.GONE);
            profilePicImageButton.setVisibility(View.VISIBLE);
            friendName.setVisibility(View.VISIBLE);
        }
        if(friend!=null) {
            friendName.setText(friend.getDisplayName());
            profilePicImageButton.setImageBitmap(friend.getPictureBitmap());
        }
        final User finalFriend = friend;
        profilePicImageButton.setOnClickListener(v -> {
            // Open the friend's profile
            String displayName = finalFriend.getDisplayName();
            // Create an intent to start the ProfileActivity
            Intent intent = new Intent(mContext, ProfileActivity.class);
            // Pass the name to the ProfileActivity
            intent.putExtra("friendDisplayName", displayName);
            intent.putExtra("profilePic", finalFriend.getPicture());
            intent.putExtra("email", finalFriend.getEmail());
            // Start the activity
            mContext.startActivity(intent);
        });

        friendName.setOnClickListener(v -> {
            // Open the friend's profile
            String displayName = finalFriend.getDisplayName();
            // Create an intent to start the ProfileActivity
            Intent intent = new Intent(mContext, ProfileActivity.class);
            // Pass the name to the ProfileActivity
            intent.putExtra("friendDisplayName", displayName);
            intent.putExtra("profilePic", finalFriend.getPicture());
            intent.putExtra("email", finalFriend.getEmail());
            // Start the activity
            mContext.startActivity(intent);
        });

        return convertView;
    }
}
