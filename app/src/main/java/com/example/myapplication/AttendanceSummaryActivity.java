package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AttendanceSummaryActivity extends AppCompatActivity {

    private ListView subjectListView;
    private ArrayList<String> subjectList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_summary);

        subjectListView = findViewById(R.id.subjectListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjectList);
        subjectListView.setAdapter(adapter);
        Button btnDetailedView = findViewById(R.id.btnDetailedView);
        btnDetailedView.setOnClickListener(v -> {
            Intent intent = new Intent(AttendanceSummaryActivity.this, DetailedAttendanceActivity.class);
            startActivity(intent);
        });

        loadSubjectAttendance();

    }

    private void loadSubjectAttendance() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Attendance").child("StudentID1").child("Subjects");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subjectList.clear();
                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    String subject = subjectSnapshot.getKey();
                    int totalClasses = subjectSnapshot.child("totalClasses").getValue(Integer.class);
                    int attendedClasses = subjectSnapshot.child("attendedClasses").getValue(Integer.class);

                    int percentage = (attendedClasses * 100) / totalClasses;
                    subjectList.add(subject + " - " + percentage + "% attendance");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }
}
