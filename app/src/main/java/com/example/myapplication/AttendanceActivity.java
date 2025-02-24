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

import com.example.myapplication.models.AttendanceRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*public class AttendanceActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference attendanceRef;
    private String studentId = "studentId1"; // Example student ID
    private Button markAttendanceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        database = FirebaseDatabase.getInstance();
        attendanceRef = database.getReference("students").child(studentId).child("attendance");

        markAttendanceButton = findViewById(R.id.markAttendanceButton);
        markAttendanceButton.setOnClickListener(view -> markAttendance());
    }

    private void markAttendance() {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        AttendanceRecord record = new AttendanceRecord(currentDate, true);

        attendanceRef.push().setValue(record).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Attendance marked", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to mark attendance", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
*/