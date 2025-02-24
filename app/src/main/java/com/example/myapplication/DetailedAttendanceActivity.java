package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.adapters.AttendanceAdapter;
import com.example.myapplication.models.AttendanceRecord;
import com.google.firebase.Timestamp;

import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DetailedAttendanceActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private TextView noAttendanceMessage;
    private AttendanceAdapter adapter;
    private FirebaseFirestore db;
    private static final String TAG = "DetailedAttendanceActivity";
    private HashMap<Long, List<AttendanceRecord>> attendanceMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_attendance);

        db = FirebaseFirestore.getInstance();
        calendarView = findViewById(R.id.attendanceCalendarView);
        recyclerView = findViewById(R.id.attendanceRecyclerView);
        noAttendanceMessage = findViewById(R.id.noAttendanceMessage);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AttendanceAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        fetchAttendanceRecords();

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            long selectedDate = getDateInMillis(year, month, dayOfMonth);
            displayAttendanceForDate(selectedDate);
        });
    }

    private void fetchAttendanceRecords() {
        db.collection("attendance_records").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subjectName = document.getString("subjectName");
                        int lectureNumber = document.getLong("lectureNumber").intValue();
                        boolean isPresent = document.getBoolean("isPresent");
                        Timestamp timestamp = document.getTimestamp("date");

                        if (timestamp != null) {
                            Date date = timestamp.toDate();
                            String time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date);
                            long dateInMillis = getDateInMillis(date);

                            AttendanceRecord record = new AttendanceRecord(subjectName, lectureNumber, isPresent, time);
                            attendanceMap.computeIfAbsent(dateInMillis, k -> new ArrayList<>()).add(record);
                        }
                    }
                } else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void displayAttendanceForDate(long selectedDate) {
        List<AttendanceRecord> records = attendanceMap.get(selectedDate);
        if (records != null && !records.isEmpty()) {
            adapter = new AttendanceAdapter(records);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            noAttendanceMessage.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            noAttendanceMessage.setVisibility(View.VISIBLE);
        }
    }

    private long getDateInMillis(int year, int month, int day) {
        return new java.util.GregorianCalendar(year, month, day).getTimeInMillis();
    }

    private long getDateInMillis(Date date) {
        return date.getTime();
    }
}
