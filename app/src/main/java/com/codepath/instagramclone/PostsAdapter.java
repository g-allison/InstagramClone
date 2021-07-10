package com.codepath.instagramclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private Context mContext;
    private List<Post> mPosts;
    private OnPostListener mOnPostListener;

    public PostsAdapter(Context mContext, List<Post> mPosts, OnPostListener onPostListener) {
        this.mContext = mContext;
        this.mPosts = mPosts;
        this.mOnPostListener = onPostListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view, mOnPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvUsername;
        private TextView mTvDescription;
        private TextView mTvCreatedAt;
        private TextView mTvLikeCount;
        private ImageView mIvProfileImage;
        private ImageView mIvImage;
        private ImageButton mIbLike;
        private RelativeLayout mRlContainer;

        OnPostListener onPostListener;

        public ViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            mTvUsername = itemView.findViewById(R.id.tvUsername);
            mIvImage = itemView.findViewById(R.id.ivImage);
            mTvDescription = itemView.findViewById(R.id.tvDescription);
            mTvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            mIvProfileImage = itemView.findViewById(R.id.ivProfileImage);
            mTvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            mIbLike = itemView.findViewById(R.id.ibLike);
            mRlContainer = itemView.findViewById(R.id.rlContainer);
            this.onPostListener = onPostListener;
        }

        public void bind(Post post) {
            mRlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPostListener.onPostClick(post.getUser());

                }
            });

            // Bind the post data to the view elements
            mTvDescription.setText(post.getDescription());
            mTvUsername.setText(itemView.getResources().getString(R.string.ampersand) + post.getUser().getUsername());

            final int[] ran = {(int) (Math.random() * 1000000) + 2};
            mTvLikeCount.setText(ran[0] + itemView.getResources().getString(R.string.likes));


            TimeAgo timeAgo = new TimeAgo(post.getCreatedAt());
            mTvCreatedAt.setText(timeAgo.calculateTimeAgo());

            ParseFile image = post.getImage();
            Glide.with(mContext).load(image.getUrl()).into(mIvImage);

            Glide.with(mContext)
                    .load(post.getProfile().getUrl())
                    .circleCrop()
                    .into(mIvProfileImage);

            mIbLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ran[0]++;
                    mTvLikeCount.setText(ran[0] + itemView.getResources().getString(R.string.likes));
                }
            });

        }
    }

    public interface OnPostListener{
        void onPostClick(ParseUser user);
    }
}