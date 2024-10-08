package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.storage.StorageReference;

public class UploadNotes extends AppCompatActivity {

    private ImageView imageView;
    private WebView webView;
    private TextView notesTextView;
    private EditText notesTitle;
    private RelativeLayout uploadNotesBtnLayout;

    private final ActivityResultLauncher<Intent> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedFileUri = result.getData().getData();
                    if (selectedFileUri != null) {
                        handleFile(selectedFileUri);
                    }
                }
            });

    private final int REQ = 5;
    private Uri notesData;
    private Bitmap bmp;
    private StorageReference storageReference;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notes);

        CardView addNotes = findViewById(R.id.upload_notes);
        imageView = findViewById(R.id.imageView);
        webView = findViewById(R.id.webView);


        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotesPicker();

            }
        });
    }

    private void openNotesPicker() {
        Intent pickNotes = new Intent(Intent.ACTION_GET_CONTENT);
        pickNotes.setType("*/*"); // Allow all types, you can specify "application/pdf" or "image/*"
        pickNotes.addCategory(Intent.CATEGORY_OPENABLE);
        filePickerLauncher.launch(Intent.createChooser(pickNotes, "Select a file"));
    }

    private void handleFile(Uri uri) {
        String mimeType = getContentResolver().getType(uri);
        if (mimeType != null) {
            if (mimeType.startsWith("image/")) {
                showImage(uri);
            } else if (mimeType.equals("application/pdf")) {
                showPdf(uri);
            } else {
                Toast.makeText(this, "Unsupported file type", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showPdf(Uri uri) {
        webView.setVisibility(WebView.VISIBLE);
        imageView.setVisibility(ImageView.GONE);
        webView.loadUrl(uri.toString());

        // Configure WebView settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
    }

    private void showImage(Uri uri) {
        imageView.setVisibility(ImageView.VISIBLE);
        webView.setVisibility(WebView.GONE);
        try {
            bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            imageView.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
        }
    }
}

