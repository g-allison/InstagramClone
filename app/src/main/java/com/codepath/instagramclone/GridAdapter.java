package com.codepath.instagramclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;



public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;
    private OnPostListener mOnPostListener;
    private LayoutInflater mInflater;

    public GridAdapter(Context context, List<Post> posts, OnPostListener onPostListener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.posts = posts;
        this.mOnPostListener = onPostListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_grid, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivGrid;

        OnPostListener onPostListener;

        public ViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            ivGrid = itemView.findViewById(R.id.ivGrid);

            itemView.setOnClickListener(this);
            this.onPostListener = onPostListener;
        }

        public void bind(Post post) {
            // Bind the post data to the view elements

            ParseFile image = post.getImage();
            Glide.with(context)
                    .load(image.getUrl())
                    .centerCrop()
                    .into(ivGrid);

        }

        @Override
        public void onClick(View v) {
            onPostListener.onPostClick(getAdapterPosition());
        }
    }

    public interface OnPostListener{
        void onPostClick(int position);
    }
}