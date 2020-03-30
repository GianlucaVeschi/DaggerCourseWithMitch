package com.gianlucaveschi.daggercourse.ui.main.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gianlucaveschi.daggercourse.R;
import com.gianlucaveschi.daggercourse.models.Post;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> posts = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_list_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PostViewHolder)holder).bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts){
        this.posts = posts;
        notifyDataSetChanged();
    }

    /*----------------------------------------- VIEW HOLDER --------------------------------------*/
    public class PostViewHolder extends RecyclerView.ViewHolder{

        TextView postTitle;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.title);
        }

        public void bind(Post post){
            postTitle.setText(post.getTitle());
        }
    }
}
