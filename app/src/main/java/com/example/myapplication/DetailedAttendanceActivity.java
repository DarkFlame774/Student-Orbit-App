package com.example.myapplication;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

public class DetailedAttendanceActivity extends AppCompatActivity {

    private TableLayout attendanceTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_attendance);

        attendanceTable = findViewById(R.id.attendanceTable);

        loadAttendanceData();
    }

    private void loadAttendanceData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Attendance").child("StudentID1").child("Subjects");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int rowNumber = 1;
                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot dateSnapshot : subjectSnapshot.child("attendanceDates").getChildren()) {
                        String date = dateSnapshot.getKey();
                        String status = dateSnapshot.getValue(String.class);
                        String subjectCode = subjectSnapshot.getKey();
                        String subjectName = subjectSnapshot.child("subjectName").getValue(String.class);
                        String time = "09:43";  // Example time, retrieve actual time as needed

                        addTableRow(rowNumber, date, "Lecture Data", subjectCode, subjectName, time, status);
                        rowNumber++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    private void addTableRow(int srNo, String date, String lectureScheduled, String subjectCode, String subjectName, String time, String status) {
        TableRow row = new TableRow(this);

        TextView srNoView = new TextView(this);
        srNoView.setText(String.valueOf(srNo));
        row.addView(srNoView);

        TextView dateView = new TextView(this);
        dateView.setText(date);
        row.addView(dateView);

        TextView lectureScheduledView = new TextView(this);
        lectureScheduledView.setText(lectureScheduled);
        row.addView(lectureScheduledView);

        TextView subjectCodeView = new TextView(this);
        subjectCodeView.setText(subjectCode);
        row.addView(subjectCodeView);

        TextView subjectNameView = new TextView(this);
        subjectNameView.setText(subjectName);
        row.addView(subjectNameView);

        TextView timeView = new TextView(this);
        timeView.setText(time);
        row.addView(timeView);

        TextView statusView = new TextView(this);
        statusView.setText(status);
        row.addView(statusView);

        attendanceTable.addView(row);
    }
}
