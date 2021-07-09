package com.codepath.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private ImageView ivImage;
    private TextView tvCreatedAt;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPostBinding binding = ActivityPostBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvUsername = binding.tvUsername;
        ivImage = binding.ivImage;
        tvDescription = binding.tvDescription;
        tvCreatedAt = binding.tvCreatedAt;

        tvUsername.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());

        TimeAgo timeAgo = new TimeAgo(post.getCreatedAt());
        tvCreatedAt.setText(timeAgo.calculateTimeAgo());

        Glide.with(this)
                .load(post.getImage().getUrl())
                .centerCrop()
                .into(ivImage);
    }
}