package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Comment;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> comments;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    public void updateComments(List<Comment> newComments) {
        comments.clear();
        comments.addAll(newComments);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.usernameTextView.setText(comment.getUserId());
        holder.contentTextView.setText(comment.getContent());
        holder.timestampTextView.setText(formatTimestamp(comment.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;
        TextView contentTextView;
        TextView timestampTextView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }

    private String formatTimestamp(Timestamp timestamp) {
        Date date = timestamp.toDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
        return sdf.format(date);
    }
}
