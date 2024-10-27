package com.example.myapplication.adapters;
import com.example.myapplication.R;
import com.example.myapplication.models.Query;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.QueryViewHolder> {
    private List<Query> queries;
    private OnQueryClickListener onQueryClickListener;

    public QueryAdapter(List<Query> queries, OnQueryClickListener listener) {
        this.queries = queries;
        this.onQueryClickListener = listener;
    }

    @NonNull
    @Override
    public QueryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_query, parent, false);
        return new QueryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QueryViewHolder holder, int position) {
        Query query = queries.get(position);
        holder.bind(query);
        holder.itemView.setOnClickListener(v -> onQueryClickListener.onQueryClick(query));
    }

    @Override
    public int getItemCount() {
        return queries.size();
    }

    public void updateQueries(List<Query> newQueries) {
        queries.clear();
        queries.addAll(newQueries);
        notifyDataSetChanged();
    }

    public interface OnQueryClickListener {
        void onQueryClick(Query query);
    }

    static class QueryViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;

        public QueryViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView); // Ensure this matches your layout
        }

        public void bind(Query query) {
            titleTextView.setText(query.getTitle());
        }
    }
}
