package com.codepath.instagramclone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.instagramclone.Post;
import com.codepath.instagramclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends ProfileFragment {
    private static final String TAG = "UserProfileFragment";

    private TextView mTvUsername;
    private TextView mTvName;
    private ImageView mIvProfileImage;
    private ParseUser mUser;


    public UserProfileFragment() {
        // Required empty public constructor
    }

    public UserProfileFragment(ParseUser user) {
        this.mUser = user;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);

        mTvName = view.findViewById(R.id.tvName);
        mTvUsername = view.findViewById(R.id.tvUsername);

        mTvName.setText(mUser.getString("name"));
        mTvUsername.setText(mUser.getUsername());

        Log.d(TAG, "Username: " + mUser.getUsername());

        mIvProfileImage = view.findViewById(R.id.ivProfileImage);
        Glide.with(view.getContext())
                .load(mUser.getParseFile("profilePicture").getUrl())
                .circleCrop()
                .into(mIvProfileImage);

    }

    @Override
    protected void query(View view) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.whereEqualTo(Post.KEY_USER, mUser);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                mAllPosts.addAll(posts);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}