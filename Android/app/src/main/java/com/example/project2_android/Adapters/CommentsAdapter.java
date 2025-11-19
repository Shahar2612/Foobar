package com.example.project2_android.Adapters;

import static com.example.project2_android.Entities.ActiveUser.getActiveUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.project2_android.Entities.Comment;
import com.example.project2_android.Entities.Dialogs;
import com.example.project2_android.Entities.Post;
import com.example.project2_android.R;
import com.example.project2_android.ViewModel.PostsViewModel;

import java.util.List;

public class CommentsAdapter extends ArrayAdapter<Comment> {

    private final Context mContext;
    private final int mResource;
    private final Post post;
    PostsViewModel postsViewModel;

    public CommentsAdapter(@NonNull Context context, int resource, @NonNull List<Comment> objects,
                           Post post, PostsViewModel postsViewModel) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.post = post;
        this.postsViewModel = postsViewModel;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        Comment comment = getItem(position);

        if (comment != null) {
            ImageView profilePicImageView = convertView.findViewById(R.id.profilePicImageView);
            TextView commenterName = convertView.findViewById(R.id.commenterName);
            TextView commentText = convertView.findViewById(R.id.commentText);
            ImageButton editButton = convertView.findViewById(R.id.editButton);
            ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);
            if (!comment.getName().equals(getActiveUser().getDisplayName())) {
                deleteButton.setVisibility(View.GONE);
                editButton.setVisibility(View.GONE);
            }
            else {
                deleteButton.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.VISIBLE);
            }

            // Set profile picture and comment text

            profilePicImageView.setImageBitmap(comment.getProfilePicBitmap());
           // profilePicImageView.setImageURI(comment.getProfilePicUri());

            commentText.setText(comment.getText());
            commenterName.setText(comment.getName());

            deleteButton.setOnClickListener(v -> {
                postsViewModel.deleteComment(post, comment);
                remove(comment);
                notifyDataSetChanged();
                notifyDataSetChanged();

            });

            editButton.setOnClickListener(v -> {
                // Display a dialog for editing the comment
                Dialogs.showEditCommentDialog(mContext, comment, post, this, postsViewModel);
            });
        }

        return convertView;
    }
}
