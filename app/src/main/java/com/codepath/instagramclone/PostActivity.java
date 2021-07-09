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

public class PostActivity extends AppCompatActivity {

    public static final String TAG = "PostActivity";

    private TextView tvUsername;
    private TextView tvDescription;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPostBinding binding = ActivityPostBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvUsername = binding.tvUsername;
        ivImage = binding.ivImage;
        tvDescription = binding.tvDescription;

        tvUsername.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());
        Glide.with(this)
                .load(post.getImage().getUrl())
                .centerCrop()
                .into(ivImage);
    }
}