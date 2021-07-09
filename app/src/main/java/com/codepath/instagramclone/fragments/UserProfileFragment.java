package com.codepath.instagramclone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.instagramclone.GridAdapter;
import com.codepath.instagramclone.Post;
import com.codepath.instagramclone.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends ProfileFragment {
    ParseUser user;
    private static final String TAG = "UserProfileFragment";
    private TextView tvUsername;
    private TextView tvName;
//    private RecyclerView rvPosts;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public UserProfileFragment(ParseUser user) {
        this.user = user;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        final int NUM_COLUMNS = 3;
        super.onViewCreated(view, savedInstanceState);
//        rvPosts = view.findViewById(R.id.rvPosts);

//        allPosts = new ArrayList<>();
//        adapter = super.adapter;

//        rvPosts.setAdapter(adapter);
//        rvPosts.setLayoutManager(new GridLayoutManager(rvPosts.getContext(), NUM_COLUMNS));

        tvName = view.findViewById(R.id.tvName);
        tvUsername = view.findViewById(R.id.tvUsername);

        tvName.setText(user.getString("name"));
        tvUsername.setText(user.getUsername());

        Log.d(TAG, "Username: " + user.getUsername());

        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        Glide.with(view.getContext())
                .load(user.getParseFile("profilePicture").getUrl())
                .circleCrop()
                .into(ivProfileImage);

    }

    @Override
    protected void query(View view) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.whereEqualTo(Post.KEY_USER, user);
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
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });



    }
}