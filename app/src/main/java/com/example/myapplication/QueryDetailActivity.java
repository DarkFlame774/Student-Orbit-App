package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.CommentAdapter;
import com.example.myapplication.models.Comment;
import com.example.myapplication.models.Query;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QueryDetailActivity extends AppCompatActivity {

    private RecyclerView commentsRecyclerView;
    private EditText commentEditText;
    private Button postCommentButton;
    private TextView charCounterTextView;
    private TextView queryTitleTextView;
    private TextView queryDescriptionTextView;
    private CommentAdapter commentAdapter;
    private FirebaseFirestore db;
    private String queryId;
    private ListenerRegistration commentsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_detail);

        // Initialize UI components
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        commentEditText = findViewById(R.id.commentEditText);
        postCommentButton = findViewById(R.id.postCommentButton);
        charCounterTextView = findViewById(R.id.charCounterTextView);
        queryTitleTextView = findViewById(R.id.queryTitleTextView);
        queryDescriptionTextView = findViewById(R.id.queryDescriptionTextView);

        // Initialize Firestore and queryId
        db = FirebaseFirestore.getInstance();
        queryId = getIntent().getStringExtra("queryId");

        // Set up RecyclerView
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(new ArrayList<>());
        commentsRecyclerView.setAdapter(commentAdapter);

        loadQueryDetails();
        loadComments();

        // Character counter listener
        commentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                charCounterTextView.setText(length + "/300");
                postCommentButton.setEnabled(length > 0);  // Enable button if text is not empty
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        // Post Comment Button
        postCommentButton.setOnClickListener(v -> postComment());
    }

    private void loadQueryDetails() {
        db.collection("queries").document(queryId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Query query = documentSnapshot.toObject(Query.class);
                        if (query != null ) {
                            queryTitleTextView.setText(query.getTitle());
                            queryDescriptionTextView.setText(query.getDescription());
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle error
                });
    }



    private void loadComments() {
        commentsListener = db.collection("queries")
                .document(queryId)
                .collection("comments")
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.ASCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        // Handle error appropriately
                        return;
                    }
                    List<Comment> comments = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : snapshots) {
                        Comment comment = doc.toObject(Comment.class);
                        comments.add(comment);
                    }
                    commentAdapter.updateComments(comments);
                    commentsRecyclerView.scrollToPosition(comments.size() - 1); // Scroll to the latest comment
                });
    }

    private void postComment() {
        String content = commentEditText.getText().toString().trim();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (content.isEmpty()) return; // Exit if comment is empty

        // Create new comment
        String commentId = db.collection("queries").document(queryId).collection("comments").document().getId();
        Comment newComment = new Comment(commentId, queryId, userId, content, Timestamp.now());

        db.collection("queries")
                .document(queryId)
                .collection("comments")
                .add(newComment)
                .addOnSuccessListener(documentReference -> {
                    commentEditText.setText(""); // Clear input field after posting
                    Toast.makeText(this, "Comment posted!", Toast.LENGTH_SHORT).show(); // Notify user
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreError", "Error uploading comment: ", e); // Log the error
                    Toast.makeText(this, "Failed to post comment: " + e.getMessage(), Toast.LENGTH_SHORT).show(); // Show a message to the user
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (commentsListener != null) {
            commentsListener.remove(); // Stop listening for comments when activity is not visible
        }
    }
}
