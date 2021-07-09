package com.codepath.instagramclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;
    private OnPostListener mOnPostListener;

    public PostsAdapter(Context context, List<Post> posts, OnPostListener onPostListener) {
        this.context = context;
        this.posts = posts;
        this.mOnPostListener = onPostListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view, mOnPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public List<Post> getPosts() {
        return posts;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private TextView tvDescription;
        private TextView tvCreatedAt;
        private TextView tvLikeCount;
        private ImageView ivProfileImage;
        private ImageView ivImage;
        private RelativeLayout rlContainer;

        OnPostListener onPostListener;

        public ViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            rlContainer = itemView.findViewById(R.id.rlContainer);
            this.onPostListener = onPostListener;
        }

        public void bind(Post post) {
            rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPostListener.onPostClick(post.getUser());

                }
            });

            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText("@" + post.getUser().getUsername());
            tvLikeCount.setText(((int)(Math.random() * 1000000) + 2) + " likes");


            TimeAgo timeAgo = new TimeAgo(post.getCreatedAt());
            tvCreatedAt.setText(timeAgo.calculateTimeAgo());

            ParseFile image = post.getImage();
            Glide.with(context).load(image.getUrl()).into(ivImage);

            Glide.with(context)
                    .load(post.getProfile().getUrl())
                    .circleCrop()
                    .into(ivProfileImage);

        }
    }

    public interface OnPostListener{
        void onPostClick(ParseUser user);
    }
}