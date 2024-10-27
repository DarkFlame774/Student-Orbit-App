package com.example.myapplication;
import com.example.myapplication.models.Query;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateQueryActivity extends AppCompatActivity {

    private EditText titleEditText, descriptionEditText;
    private Button postButton;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_query);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        postButton = findViewById(R.id.postButton);

        postButton.setOnClickListener(v -> postQuery());
    }

    private void postQuery() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String userId = auth.getCurrentUser().getUid();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String queryId = db.collection("queries").document().getId();
        Query query = new Query(queryId, title, description, userId, Timestamp.now());

        db.collection("queries").document(queryId).set(query)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Query posted successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to post query", Toast.LENGTH_SHORT).show());
    }
}
