package com.example.project2_android.Adapters;

import static com.example.project2_android.MyApplication.context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import com.example.project2_android.Entities.DataManager;
import com.example.project2_android.Entities.User;
import com.example.project2_android.ProfileActivity;
import com.example.project2_android.R;
import com.example.project2_android.ViewModel.UsersViewModel;
import java.util.List;


public class FriendRequestAdapter extends ArrayAdapter<User> {
    private final Context mContext;
    private final int mResource;
    private final List<User> friendRequests;
    private final UsersViewModel usersViewModel;

    public FriendRequestAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        friendRequests = objects;
        usersViewModel = DataManager.getUsersViewModel();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }
        User friendRequest = friendRequests.get(position);
        ImageButton profilePic = convertView.findViewById(R.id.profilePicImageView);
        Button friendName = convertView.findViewById(R.id.requesterName);
        Button acceptButton = convertView.findViewById(R.id.acceptButton);
        Button declineButton = convertView.findViewById(R.id.declineButton);


        profilePic.setOnClickListener(v -> {
            // Create an intent to start the ProfileActivity
            Intent intent = new Intent(context, ProfileActivity.class);
            // Pass the name to the ProfileActivity
            intent.putExtra("friendDisplayName", friendRequest.getDisplayName());
            intent.putExtra("profilePic", friendRequest.getPicture());
            intent.putExtra("email", friendRequest.getEmail());
            // Start the activity
            context.startActivity(intent);
        });

        friendName.setOnClickListener(v -> {
            // Create an intent to start the ProfileActivity
            Intent intent = new Intent(context, ProfileActivity.class);
            // Pass the name to the ProfileActivity
            intent.putExtra("friendDisplayName", friendRequest.getDisplayName());
            intent.putExtra("profilePic", friendRequest.getPicture());
            intent.putExtra("email", friendRequest.getEmail());
            // Start the activity
            context.startActivity(intent);
        });


        profilePic.setImageBitmap(friendRequest.getPictureBitmap());
        friendName.setText(friendRequest.getDisplayName());
        acceptButton.setOnClickListener(v -> {
            usersViewModel.acceptFriendRequest(friendRequest);
            friendRequests.remove(friendRequest);
            notifyDataSetChanged();

        });
        declineButton.setOnClickListener(v -> {
            usersViewModel.declineFriendRequest(friendRequest);
            friendRequests.remove(friendRequest);
            notifyDataSetChanged();

        });
        return convertView;
    }
}
