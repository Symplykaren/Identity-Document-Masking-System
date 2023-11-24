package com.example.ai_projectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.graphics.BitmapFactory;

public class Redaction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redaction);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve the image data from the Intent
        byte[] imageData = getIntent().getByteArrayExtra("image_data");

        // Now you can use the image data in this activity as needed
        // For example, you can display it in an ImageView
        ImageView imageView = findViewById(R.id.displayImageView);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        imageView.setImageBitmap(bitmap);
    }
}