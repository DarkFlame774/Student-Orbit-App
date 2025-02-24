package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.EdgeToEdge;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView notesManager;
    CardView QueryFeature;
    CardView ClassSchedule;
    CardView attendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        notesManager = findViewById(R.id.Notes);
        QueryFeature = findViewById(R.id.Query);
        ClassSchedule = findViewById(R.id.schedule);
        attendance = findViewById(R.id.attendance);

        notesManager.setOnClickListener(this);
        QueryFeature.setOnClickListener(this);
        ClassSchedule.setOnClickListener(this);
        attendance.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.Notes){
            Intent intent = new Intent(MainActivity.this,NotesManagement.class);
            startActivity(intent);
        }else if(view.getId() == R.id.Query){
            Intent intent = new Intent(MainActivity.this,QueryListActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.schedule) {
            Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.attendance) {
            Intent intent = new Intent(MainActivity.this, AttendanceSummaryActivity.class);
            startActivity(intent);
        }
    }
}