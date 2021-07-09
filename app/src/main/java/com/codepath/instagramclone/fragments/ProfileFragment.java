package com.codepath.instagramclone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.instagramclone.GridAdapter;
import com.codepath.instagramclone.Post;
import com.codepath.instagramclone.PostActivity;
import com.codepath.instagramclone.PostsAdapter;
import com.codepath.instagramclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment implements GridAdapter.OnPostListener {

    private RecyclerView rvPosts;
    protected GridAdapter adapter;
    protected List<Post> allPosts;
    private static final String TAG = "ProfileFragment";

    private TextView tvUsername;
    private TextView tvName;
    private TextView tvCreatedAt;

    protected ImageView ivProfileImage;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final int NUM_COLUMNS = 3;
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);

        allPosts = new ArrayList<>();
        adapter = new GridAdapter(getContext(), allPosts, this);


        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new GridLayoutManager(rvPosts.getContext(), NUM_COLUMNS));


        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername() + ", name: " + post.getName());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
                Log.d(TAG, "done: allPosts " + allPosts);
                Log.d(TAG, "username: " + allPosts.get(0).getUser().getUsername());

                tvName = view.findViewById(R.id.tvName);
                tvName.setText(allPosts.get(0).getName());

                Post post = allPosts.get(0);
                tvUsername = view.findViewById(R.id.tvUsername);
                tvUsername.setText(post.getUser().getUsername());

                ivProfileImage = view.findViewById(R.id.ivProfileImage);
                Glide.with(view.getContext())
                        .load(post.getProfile().getUrl())
                        .circleCrop()
                        .into(ivProfileImage);

            }
        });
    }

    private void queryPosts() {

    }


    @Override
    public void onPostClick(int position) {
        Log.d(TAG, "onTweetClick: clicked");
        Intent intent = new Intent(getContext(), PostActivity.class);
        intent.putExtra("post", Parcels.wrap(allPosts.get(position)));
        startActivity(intent);
        Log.d(TAG, "startActivity");
    }
}
