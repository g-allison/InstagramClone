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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.instagramclone.GridAdapter;
import com.codepath.instagramclone.Post;
import com.codepath.instagramclone.PostActivity;
import com.codepath.instagramclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements GridAdapter.OnPostListener {

    private static final String TAG = "ProfileFragment";

    private RecyclerView mRvPosts;
    protected GridAdapter mAdapter;
    protected List<Post> mAllPosts;

    private TextView mTvUsername;
    private TextView mTvName;
    private ImageView mIvProfileImage;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflates the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final int NUM_COLUMNS = 3;
        super.onViewCreated(view, savedInstanceState);
        mRvPosts = view.findViewById(R.id.rvPosts);

        mAllPosts = new ArrayList<>();
        mAdapter = new GridAdapter(getContext(), mAllPosts, this);

        mRvPosts.setAdapter(mAdapter);
        mRvPosts.setLayoutManager(new GridLayoutManager(mRvPosts.getContext(), NUM_COLUMNS));

        query(view);

    }

    protected void query(View view) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // checks for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername() + ", name: " + post.getName());
                }
                mAllPosts.addAll(posts);
                mAdapter.notifyDataSetChanged();
                Log.d(TAG, "done: allPosts " + mAllPosts);
                Log.d(TAG, "username: " + mAllPosts.get(0).getUser().getUsername());

                mTvName = view.findViewById(R.id.tvName);
                mTvName.setText(mAllPosts.get(0).getName());

                Post post = mAllPosts.get(0);
                mTvUsername = view.findViewById(R.id.tvUsername);
                mTvUsername.setText(getResources().getString(R.string.ampersand) + post.getUser().getUsername());

                mIvProfileImage = view.findViewById(R.id.ivProfileImage);
                Glide.with(view.getContext())
                        .load(post.getProfile().getUrl())
                        .circleCrop()
                        .into(mIvProfileImage);

            }
        });
    }


    @Override
    public void onPostClick(int position) {
        Log.d(TAG, "onTweetClick: clicked");
        Intent intent = new Intent(getContext(), PostActivity.class);
        intent.putExtra("post", Parcels.wrap(mAllPosts.get(position)));
        startActivity(intent);
        Log.d(TAG, "startActivity");
    }
}
