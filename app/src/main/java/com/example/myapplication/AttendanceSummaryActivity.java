package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapters.AttendanceSummaryAdapter;
import com.example.myapplication.models.AttendanceRecord;
import com.example.myapplication.models.AttendanceSummary;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceSummaryActivity extends AppCompatActivity {

    private RecyclerView subjectRecyclerView;
    private Button btnDetailedView;
    private FirebaseFirestore db;
    private static final String TAG = "AttendanceSummaryActivity";

    private AttendanceSummaryAdapter adapter;
    private List<AttendanceSummary> AttendanceSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_summary);

        subjectRecyclerView = findViewById(R.id.subjectRecyclerView);
        btnDetailedView = findViewById(R.id.btnDetailedView);

        db = FirebaseFirestore.getInstance();
        AttendanceSummary = new ArrayList<>();
        adapter = new AttendanceSummaryAdapter(AttendanceSummary);

        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectRecyclerView.setAdapter(adapter);

        fetchAttendanceSummary();

        btnDetailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceSummaryActivity.this, DetailedAttendanceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchAttendanceSummary() {
        db.collection("attendance_records").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, int[]> subjectAttendanceMap = new HashMap<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subjectName = document.getString("subjectName");
                        boolean isPresent = document.getBoolean("isPresent");

                        if (!subjectAttendanceMap.containsKey(subjectName)) {
                            subjectAttendanceMap.put(subjectName, new int[]{0, 0});
                        }
                        int[] counts = subjectAttendanceMap.get(subjectName);
                        counts[1]++; // Total classes
                        if (isPresent) {
                            counts[0]++; // Attended classes
                        }
                    }

                    AttendanceSummary.clear();
                    for (Map.Entry<String, int[]> entry : subjectAttendanceMap.entrySet()) {
                        String subject = entry.getKey();
                        int attended = entry.getValue()[0];
                        int total = entry.getValue()[1];
                        int percentage = (int) ((attended / (float) total) * 100);
                        AttendanceSummary.add(new AttendanceSummary(subject, percentage));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
