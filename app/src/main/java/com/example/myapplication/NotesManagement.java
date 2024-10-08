package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NotesManagement extends AppCompatActivity implements View.OnClickListener{

    CardView uploadNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notes_management);

        uploadNotes = findViewById(R.id.addNotes_icon);

        uploadNotes.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.addNotes_icon){
            Intent intent = new Intent(NotesManagement.this,UploadNotes.class);
            startActivity(intent);
        }
    }
}