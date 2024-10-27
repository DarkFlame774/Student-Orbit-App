package com.example.myapplication;
import com.example.myapplication.models.Query;
import com.example.myapplication.adapters.QueryAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class QueryListActivity extends AppCompatActivity {

    private RecyclerView queryRecyclerView;
    private QueryAdapter queryAdapter;
    private FloatingActionButton addQueryFab;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_list);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Set up RecyclerView
        queryRecyclerView = findViewById(R.id.queryRecyclerView);
        queryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        queryAdapter = new QueryAdapter(new ArrayList<>(), this::openQueryDetail);
        queryRecyclerView.setAdapter(queryAdapter);

        // Floating Action Button to add a new query
        addQueryFab = findViewById(R.id.addQueryFab);
        addQueryFab.setOnClickListener(v -> openCreateQueryActivity());

        // Load queries from Firestore
        loadQueries();
    }

    private void loadQueries() {
        db.collection("queries")
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .addSnapshotListener(this::onQueriesLoaded);
    }

    private void onQueriesLoaded(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
        if (e != null) {
            showError("Error loading queries: " + e.getMessage());
            return;
        }

        List<Query> queries = new ArrayList<>();
        if (queryDocumentSnapshots != null) {
            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                Query query = doc.toObject(Query.class);

                if (query != null) {
                    queries.add(query);
                }
            }
        }

        if (queries.isEmpty()) {
            showEmptyState();
        } else {
            queryAdapter.updateQueries(queries);
        }
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showEmptyState() {
        Toast.makeText(this, "No queries found", Toast.LENGTH_SHORT).show();
    }

    private void openCreateQueryActivity() {
        Intent intent = new Intent(QueryListActivity.this, CreateQueryActivity.class);
        startActivity(intent);
    }

    private void openQueryDetail(Query query) {
        Intent intent = new Intent(this, QueryDetailActivity.class);
        intent.putExtra("queryId", query.getQueryId());
        startActivity(intent);
    }
}
