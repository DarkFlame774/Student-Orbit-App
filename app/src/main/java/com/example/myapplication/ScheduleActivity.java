package com.example.myapplication;


import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapters.ScheduleAdapter;
import com.example.myapplication.models.ClassSchedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.myapplication.adapters.ScheduleAdapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView classDetails;
    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;
    private FirebaseFirestore db;
    private static final String TAG = "ScheduleActivity"; // Tag for logging
    private HashMap<Long, List<ClassSchedule>> scheduleMap = new HashMap<>(); // To map date to classes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize CalendarView and RecyclerView
        calendarView = findViewById(R.id.calendarView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ScheduleAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Fetch the schedule from Firestore
        fetchSchedule();

        // Set a listener on the CalendarView
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            long selectedDate = getDateInMillis(year, month, dayOfMonth);
            displayClassesForDate(selectedDate);
        });
    }

    private void fetchSchedule() {
        db.collection("schedules").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String className = document.getString("className");
                        String startTime = document.getString("startTime");
                        String endTime = document.getString("endTime");
                        String day = document.getString("day");
                        String room = document.getString("room");
                        String instructor = document.getString("instructor");
                        String date = document.getString("date"); // Add this field in Firestore

                        // Convert date string to a long value for the key
                        long dateInMillis = convertDateStringToMillis(date);

                        ClassSchedule classSchedule = new ClassSchedule(className, startTime, endTime, day, room, instructor);
                        scheduleMap.computeIfAbsent(dateInMillis, k -> new ArrayList<>()).add(classSchedule);
                    }
                } else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void displayClassesForDate(long selectedDate) {
        List<ClassSchedule> classes = scheduleMap.get(selectedDate);
        if (classes != null && !classes.isEmpty()) {
            adapter = new ScheduleAdapter(classes);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            adapter = new ScheduleAdapter(new ArrayList<>());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            // Optionally, you can display a message when no classes are found
        }
    }

    private long getDateInMillis(int year, int month, int day) {
        return new java.util.GregorianCalendar(year, month, day).getTimeInMillis();
    }

    private long convertDateStringToMillis(String date) {
        // Assume date is in "YYYY-MM-DD" format
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]) - 1; // Month is 0-based
        int day = Integer.parseInt(parts[2]);
        return getDateInMillis(year, month, day);
    }
}
