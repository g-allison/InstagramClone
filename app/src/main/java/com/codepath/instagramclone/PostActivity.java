package com.codepath.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.instagramclone.databinding.ActivityPostBinding;

import org.parceler.Parcels;

import java.util.Date;

public class PostActivity extends AppCompatActivity {

    public static final String TAG = "PostActivity";

    private TextView tvUsername;
    private TextView tvDescription;
    private TextView tvCreatedAt;
    private TextView tvLikeCount;
    private ImageView ivImage;
    private ImageView ivProfileImage;
    private ImageButton ibLike;

    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPostBinding binding = ActivityPostBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvUsername = binding.tvUsername;
        tvDescription = binding.tvDescription;
        tvCreatedAt = binding.tvCreatedAt;
        ivImage = binding.ivImage;
        ivProfileImage = binding.ivProfileImage;
        tvLikeCount = binding.tvLikeCount;
        ibLike = binding.ibLike;

        tvUsername.setText("@" + post.getUser().getUsername());

        final int[] ran = {(int) (Math.random() * 1000000) + 2};
        tvLikeCount.setText(ran[0] + " likes");

        tvDescription.setText(post.getDescription());

        TimeAgo timeAgo = new TimeAgo(post.getCreatedAt());
        tvCreatedAt.setText(timeAgo.calculateTimeAgo());

        Glide.with(this)
                .load(post.getImage().getUrl())
                .centerCrop()
                .into(ivImage);

        Glide.with(this)
                .load(post.getProfile().getUrl())
                .circleCrop()
                .into(ivProfileImage);

        ibLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ran[0]++;
                tvLikeCount.setText(ran[0] + " likes");
            }
        });
    }
}