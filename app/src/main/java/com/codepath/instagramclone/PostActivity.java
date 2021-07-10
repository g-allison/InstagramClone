package com.codepath.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.instagramclone.databinding.ActivityPostBinding;

import org.parceler.Parcels;


public class PostActivity extends AppCompatActivity {

    private TextView mTvUsername;
    private TextView mTvDescription;
    private TextView mTvCreatedAt;
    private TextView mTvLikeCount;
    private ImageView mIvImage;
    private ImageView mIvProfileImage;
    private ImageButton mIbLike;
    private Post mPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPostBinding binding = ActivityPostBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        mPost = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));

        mTvUsername = binding.tvUsername;
        mTvDescription = binding.tvDescription;
        mTvCreatedAt = binding.tvCreatedAt;
        mIvImage = binding.ivImage;
        mIvProfileImage = binding.ivProfileImage;
        mTvLikeCount = binding.tvLikeCount;
        mIbLike = binding.ibLike;

        mTvUsername.setText(getResources().getString(R.string.ampersand) + mPost.getUser().getUsername());

        final int[] ran = {(int) (Math.random() * 1000000) + 2};
        mTvLikeCount.setText(ran[0] + getResources().getString(R.string.likes));


        mTvDescription.setText(mPost.getDescription());

        TimeAgo timeAgo = new TimeAgo(mPost.getCreatedAt());
        mTvCreatedAt.setText(timeAgo.calculateTimeAgo());

        Glide.with(this)
                .load(mPost.getImage().getUrl())
                .centerCrop()
                .into(mIvImage);

        Glide.with(this)
                .load(mPost.getProfile().getUrl())
                .circleCrop()
                .into(mIvProfileImage);

        mIbLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ran[0]++;
                mTvLikeCount.setText(ran[0] + getResources().getString(R.string.likes));
            }
        });
    }
}