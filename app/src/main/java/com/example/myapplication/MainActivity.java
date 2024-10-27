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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        notesManager = findViewById(R.id.Notes);
        QueryFeature = findViewById(R.id.Query);

        notesManager.setOnClickListener(this);
        QueryFeature.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.Notes){
            Intent intent = new Intent(MainActivity.this,NotesManagement.class);
            startActivity(intent);
        }else if(view.getId() == R.id.Query){
            Intent intent = new Intent(MainActivity.this,QueryListActivity.class);
            startActivity(intent);
        }
    }
}