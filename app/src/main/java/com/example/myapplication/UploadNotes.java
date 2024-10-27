package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadNotes extends AppCompatActivity {

    private ImageView imageView;
    private WebView webView;
    private EditText notesTitle;
    private RelativeLayout uploadNotesBtn;
    private Uri selectedFileUri;
    private String fileName;

    private final ActivityResultLauncher<Intent> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                     selectedFileUri = result.getData().getData();
                    if (selectedFileUri != null) {
                        handleFile(selectedFileUri);
                        notesTitle.setText(getFileName(selectedFileUri));
                    }
                }
            });


    private final int REQ = 5;

    private Bitmap bmp;
    private StorageReference storageReference;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notes);

        CardView addNotes = findViewById(R.id.upload_notes);
        imageView = findViewById(R.id.imageView);
        webView = findViewById(R.id.webView);
        uploadNotesBtn = findViewById(R.id.uploadNotes_btn);
        notesTitle = findViewById(R.id.notes_title);
        fileName = "File";
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress);
        dialog = builder.create();

        uploadNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedFileUri == null){
                    Toast.makeText(UploadNotes.this, "No file selected", Toast.LENGTH_SHORT).show();
                }else{
                    setDialog(true);
                    uploadNotes();
                }
            }
        });

        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotesPicker();

            }
        });
    }

    private void uploadNotes() {
            fileName = String.valueOf(notesTitle.getText());
            StorageReference fileReference = storageReference.child( fileName + getFileExtension(selectedFileUri));
            fileReference.putFile(selectedFileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UploadNotes.this, "Upload successful", Toast.LENGTH_SHORT).show();
                            setDialog(false);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadNotes.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
    }

    private String getFileExtension(Uri uri) {
        String extension = "";
        if (uri.getLastPathSegment() != null) {
            String[] split = uri.getLastPathSegment().split("\\.");
            if (split.length > 1) {
                extension = split[split.length - 1];
            }
        }
        return extension;
    }
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            String[] projection = {android.provider.MediaStore.Images.Media.DISPLAY_NAME};
            try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(0);
                }
            }
        }
        if (result != null && result.contains(".")) {
            result = result.substring(0, result.lastIndexOf('.')); // Remove extension
        }
        return result;
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

    private void setDialog(boolean show){
        if (show)dialog.show();
        else dialog.dismiss();
    }
}

