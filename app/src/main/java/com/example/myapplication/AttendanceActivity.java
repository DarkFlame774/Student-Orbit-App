package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AttendanceActivity extends AppCompatActivity {

    private EditText studentNameInput;
    private Button submitButton;
    private TextView statusTextView;

    // Firebase references
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // Initialize views
        studentNameInput = findViewById(R.id.studentNameInput);
        submitButton = findViewById(R.id.submitButton);
        statusTextView = findViewById(R.id.statusTextView);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Check if user is logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Redirect to login if user is not logged in
            startActivity(new Intent(AttendanceActivity.this, LoginActivity.class));
            finish();
            return;
        }

        // Get Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("attendanceRecords");

        // Submit button action
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get student name
                String studentName = studentNameInput.getText().toString().trim();
                if (!studentName.isEmpty()) {
                    submitAttendance(studentName);
                } else {
                    Toast.makeText(AttendanceActivity.this, "Please enter student name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to submit attendance record to Firebase
    private void submitAttendance(String studentName) {
        // Get the current user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid(); // Get the authenticated user's UID
            String id = databaseReference.push().getKey(); // Generate unique key for record

            // Create attendance record linked to the user
            AttendanceRecord record = new AttendanceRecord(id, studentName, System.currentTimeMillis(), userId);

            // Store the record in the database under the user’s ID
            databaseReference.child(userId).child(id).setValue(record)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            statusTextView.setText("Status: Attendance recorded for " + studentName);
                        } else {
                            statusTextView.setText("Status: Failed to record attendance");
                        }
                    });
        }
    }

    // Attendance record model class
    public static class AttendanceRecord {
        private String id;
        private String studentName;
        private long timestamp;
        private String userId; // Add user ID to the record

        public AttendanceRecord() {
            // Default constructor required for calls to DataSnapshot.getValue(AttendanceRecord.class)
        }

        public AttendanceRecord(String id, String studentName, long timestamp, String userId) {
            this.id = id;
            this.studentName = studentName;
            this.timestamp = timestamp;
            this.userId = userId;
        }

        public String getId() {
            return id;
        }

        public String getStudentName() {
            return studentName;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String getUserId() {
            return userId;
        }
    }
}
