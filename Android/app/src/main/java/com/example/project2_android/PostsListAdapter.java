package com.example.project2_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project2_android.Entities.Comment;
import com.example.project2_android.Entities.DataManager;
import com.example.project2_android.Entities.Dialogs;
import com.example.project2_android.Entities.Post;
import com.example.project2_android.Entities.UploadPicture;
import com.example.project2_android.Entities.User;
import com.example.project2_android.ViewModel.PostsViewModel;
import java.util.List;

/**
 * Adapter class for managing the display of posts in a RecyclerView.
 */
public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {

    private final User activeUser;

    // Interface for handling like button clicks
    interface OnLikeButtonClickListener {
        void onLikeButtonClick(int position);
    }

    // Interface for handling comment button clicks
    interface OnCommentButtonClickListener {
        void onCommentButtonClick(int position);
    }


    // View holder class for holding the post item views
    class PostViewHolder extends RecyclerView.ViewHolder {
        private final Button tvAuthor;
        private final TextView tvContent;
        private final ImageButton profilePic;
        private final ImageView ivPic;
        private final TextView likes;
        private final TextView comments;
        private final ImageButton likeButton;
        private final ImageButton addCommentButton;
        private final Button commentsButton;
        private final ImageButton share;
        private final TextView timeAgo;
        private final ImageButton postMenu;


        // Constructor to initialize the views
        private PostViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivPic = itemView.findViewById(R.id.ivPic);
            likes = itemView.findViewById(R.id.likes);
            commentsButton = itemView.findViewById(R.id.comments);
            likeButton = itemView.findViewById(R.id.addLike);
            profilePic = itemView.findViewById(R.id.profilePic);
            addCommentButton = itemView.findViewById(R.id.addComment);
            comments = itemView.findViewById(R.id.comments);
            share = itemView.findViewById(R.id.share);
            timeAgo = itemView.findViewById(R.id.timeAgo);
            postMenu = itemView.findViewById(R.id.postMenu);

        }
    }

    private final LayoutInflater mInflater;
    //private List<Post> posts = Db.instantiateInfo();
    private List<Post> posts;
    private OnLikeButtonClickListener likeButtonClickListener;
    private OnCommentButtonClickListener commentButtonClickListener;
    private final Context context;
    private final PostsViewModel viewModel;
    Activity activity;
    UploadPicture uploadPicture;

    /**
     * Constructor to initialize the PostsListAdapter.
     *
     * @param context The context in which the adapter will be used.
     */
    public PostsListAdapter(Context context, User activeUser, List<Post> posts,
                            PostsViewModel viewModel, Activity activity, UploadPicture uploadPicture) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.activeUser = activeUser;
        this.posts = posts;
        this.viewModel = viewModel;
        this.activity = activity;
        this.uploadPicture = uploadPicture;
        DataManager.setPostsListAdapter(this);
    }

    /**
     * Sets the listener for like button clicks.
     *
     * @param listener The listener for like button clicks.
     */
    public void setOnLikeButtonClickListener(OnLikeButtonClickListener listener) {
        likeButtonClickListener = listener;
    }


    /**
     * Inflates the item layout and creates the corresponding ViewHolder object.
     *
     * @param parent   The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.post_layout, parent, false);

        return new PostViewHolder(itemView);
    }

    /**
     * Binds the data to the views in each item.
     *
     * @param holder   The ViewHolder object to populate.
     * @param position The position of the item within the data set.
     */
    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        if (posts != null) {
            final Post current = posts.get(position);
            holder.tvAuthor.setText(current.getName());
            holder.tvContent.setText(current.getText());
            Bitmap bitmap = UploadPicture.convertStringToBitmap(current.getPhoto());
            holder.ivPic.setImageBitmap(bitmap);
            bitmap = UploadPicture.convertStringToBitmap(current.getUserPic());
            holder.profilePic.setImageBitmap(bitmap);


            holder.likes.setText(String.valueOf(current.getLike()) + " likes");
            holder.comments.setText(String.valueOf(current.getComments()) + " comments");
            holder.timeAgo.setText(current.getDateFormat());

            //holder.commentsAdapter.setComments(getCommentsForPost(position));
            holder.likeButton.setOnClickListener(v -> {
                if (likeButtonClickListener != null) {
                    // Call the listener's method passing the position of the clicked item
                    likeButtonClickListener.onLikeButtonClick(holder.getAdapterPosition());
                }
            });

            holder.addCommentButton.setOnClickListener(v -> {
                if (commentButtonClickListener != null) {
                    // Call the listener's method passing the position of the clicked item
                    commentButtonClickListener.onCommentButtonClick(holder.getAdapterPosition());
                }
                Dialogs.showCommentDialog(context, position, this, activeUser, viewModel);
            });

            holder.commentsButton.setOnClickListener(v -> {
                // Get the comments for the specific post
                List<Comment> comments = getCommentsForPost(position);
                // Show the comment dialog
                Dialogs.showCommentDialog(context, comments, posts.get(position), viewModel);
            });

            holder.share.setOnClickListener(v -> {
                Dialogs.showShareDialog(context);
            });


            holder.postMenu.setOnClickListener(v -> {
                if (!activeUser.getDisplayName().equals(current.getName())) {
                    return;
                }
                PopupMenu popupMenu = new PopupMenu(context, holder.postMenu);
                popupMenu.getMenuInflater().inflate(R.menu.post_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.editPost) {

                        if (position != RecyclerView.NO_POSITION) {
                            Dialogs.editPostDialog(context, position);
                        }
                        return true;
                    }
                    else if (itemId == R.id.deletePost) {

                        if (position != RecyclerView.NO_POSITION) {
                            viewModel.delete(current);
                            Toast.makeText(context, "Post deleted", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                    return false;
                });
                popupMenu.show();
            });

            holder.profilePic.setOnClickListener(v -> {
                String displayName = current.getName();
                // Create an intent to start the ProfileActivity
                Intent intent = new Intent(context, ProfileActivity.class);
                // Pass the name to the ProfileActivity
                intent.putExtra("friendDisplayName", displayName);
                intent.putExtra("profilePic", current.getUserPic());
                intent.putExtra("email", current.getEmail());
                // Start the activity
                context.startActivity(intent);
            });

            holder.tvAuthor.setOnClickListener(v -> {
                String displayName = current.getName();
                // Create an intent to start the ProfileActivity
                Intent intent = new Intent(context, ProfileActivity.class);
                // Pass the name to the ProfileActivity
                intent.putExtra("friendDisplayName", displayName);
                intent.putExtra("profilePic", current.getUserPic());
                intent.putExtra("email", current.getEmail());
                // Start the activity
                context.startActivity(intent);
            });

        }
    }

    /**
     * Sets the list of posts to be displayed.
     *
     * @param s The list of posts to be displayed.
     */
    public void setPosts(List<Post> s) {
        posts = s;
        notifyDataSetChanged();
    }

    /**
     * Gets the number of items in the data set.
     *
     * @return The number of items in the data set.
     */
    @Override
    public int getItemCount() {
        if (posts != null)
            return posts.size();
        else return 0;
    }

    /**
     * Gets the list of posts.
     *
     * @return The list of posts.
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * Gets the comments for a specific post.
     *
     * @param position The position of the post in the list.
     * @return The list of comments for the post.
     */
    public List<Comment> getCommentsForPost(int position) {
        return posts.get(position).getCommentsInfo();
    }



}