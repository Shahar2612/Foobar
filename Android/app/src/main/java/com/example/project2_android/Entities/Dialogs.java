package com.example.project2_android.Entities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.project2_android.Adapters.CommentsAdapter;
import com.example.project2_android.Adapters.FriendRequestAdapter;
import com.example.project2_android.Adapters.FriendsAdapter;
import com.example.project2_android.PostsListAdapter;
import com.example.project2_android.R;
import com.example.project2_android.UpdatePostActivity;
import com.example.project2_android.ViewModel.PostsViewModel;
import java.util.List;

/**
 * Utility class for displaying various types of dialogs.
 */
public class Dialogs {

    /**
     * Displays a dialog for adding a comment to a post.
     * Increments the comments count of the post upon submission.
     *
     * @param context  The context in which the dialog should be displayed.
     * @param position The position of the post in the adapter.
     * @param adapter  The adapter managing the posts list.
     */
    public static void showCommentDialog(Context context, int position, PostsListAdapter adapter,
                                         User activeUser, PostsViewModel postsViewModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.comment_dialog, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        EditText commentEditText = dialogView.findViewById(R.id.editTextComment);
        Button submitButton = dialogView.findViewById(R.id.buttonSubmit);
        Button cancelButton = dialogView.findViewById(R.id.buttonCancel);

        submitButton.setOnClickListener(v -> {
            // Get the comment text entered by the user
            String commentText = commentEditText.getText().toString().trim();

            // Check if the comment is not empty
            if (!commentText.isEmpty()) {
                // Get the current post
                Post currentPost = adapter.getPosts().get(position);

                Comment comment = new Comment(activeUser, commentText, currentPost);
                postsViewModel.addComment(currentPost, comment);

                // Add the comment to the post's comments list
           //     currentPost.addComment(comment);

                // Increment the comments count
            //    currentPost.setComments(currentPost.getComments() + 1);

                // Notify the adapter that data has changed
                adapter.notifyItemChanged(position);

                // Dismiss the dialog
                alertDialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();
    }

    /**
     * Displays a dialog showing a list of comments.
     *
     * @param context  The context in which the dialog should be displayed.
     * @param comments The list of comments to display.
     */
    public static void showCommentDialog(Context context, List<Comment> comments, Post post,
                             PostsViewModel postsViewModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.comment_list, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        ListView listViewComments = dialogView.findViewById(R.id.listViewComments);
        CommentsAdapter adapter = new CommentsAdapter(context, R.layout.comment_item, comments,
                post, postsViewModel);
        listViewComments.setAdapter(adapter);
        alertDialog.show();
    }

    /**
     * Displays a dialog for sharing content through various platforms.
     *
     * @param context The context in which the dialog should be displayed.
     */
    public static void showShareDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.share_layout, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Displays a dialog for editing content through various platforms.
     *
     * @param context The context in which the dialog should be displayed.
     */
    public static void editPostDialog(Context context, int position) {

        Intent intent = new Intent(context, UpdatePostActivity.class);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    public static void showSearchDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.search_dialog, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        Button cancelButton = dialogView.findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();

    }

    public static void showEditCommentDialog(Context context, Comment comment, Post post,
                                 ArrayAdapter<Comment> adapter, PostsViewModel postsViewModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.edit_comment, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        EditText commentEditText = dialogView.findViewById(R.id.editTextComment);
        Button submitButton = dialogView.findViewById(R.id.buttonSubmit);
        Button cancelButton = dialogView.findViewById(R.id.buttonCancelEdit);

        // Set the initial text of the EditText to the current comment text
        commentEditText.setText(comment.getText());

        submitButton.setOnClickListener(v -> {
            // Get the new text from the EditText
            String newText = commentEditText.getText().toString().trim();

            // Update the comment text
            comment.setText(newText);

            // Notify the adapter of the change
            adapter.notifyDataSetChanged();

            // Dismiss the dialog
            alertDialog.dismiss();

            postsViewModel.updateComment(post, comment);
        });

        cancelButton.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();
    }
    public static void showFriendRequestsDialog(Context context, List<User> requests) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.friend_requests, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        ListView listViewRequests = dialogView.findViewById(R.id.listViewRequests);
        FriendRequestAdapter adapter = new FriendRequestAdapter(context, R.layout.request_item, requests);
        listViewRequests.setAdapter(adapter);
        alertDialog.show();
    }

    public static void showFriendsDialog(Context context, List<String> friends) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.friends_list, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        ListView listViewFriends = dialogView.findViewById(R.id.listView);
        FriendsAdapter adapter = new FriendsAdapter(context, R.layout.friend_item, friends);
        listViewFriends.setAdapter(adapter);
        alertDialog.show();
    }
}