package com.example.ai_projectapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import java.io.ByteArrayOutputStream;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {

    // Define the pic id
    private static final int pic_id = 123;

    // Define the button and imageview type variable
    Button camera_open_id;
    ImageView click_image_id;

    // ActivityResultLauncher for camera permission
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission granted, you can open the camera here
                    openCamera();
                } else {
                    // Permission denied, handle accordingly (e.g., show a message or disable camera functionality)
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // By ID we can get each component which id is assigned in XML file get Buttons and imageview.
        camera_open_id = findViewById(R.id.BSelectImage);
        click_image_id = findViewById(R.id.IVPreviewImage);

        // Check for camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, you can open the camera
            openCamera();
        } else {
            // Request camera permission
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }

        // Set OnClickListener for the camera button
        camera_open_id.setOnClickListener(v -> openCamera());
    }

    private void openCamera() {
        // Create the camera_intent ACTION_IMAGE_CAPTURE it will open the camera for capturing the image
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Start the activity with camera_intent, and request pic id
        startActivityForResult(camera_intent, pic_id);
    }

    // This method will help to retrieve the image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Match the request 'pic id with requestCode
        if (requestCode == pic_id && resultCode == RESULT_OK) {
            // BitMap is a data structure of the image file which stores the image in memory
            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            // Set the image in the ImageView for display
//            click_image_id.setImageBitmap(photo);

            // Convert the Bitmap to another format if needed
            // For example, you can convert it to a byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();  // Declare byteArray here

            // Create an Intent to start the next activity
            Intent intent = new Intent(this, Redaction.class);

            // Attach the image data as an extra to the Intent
            intent.putExtra("image_data", byteArray);

            // Start the next activity
            startActivity(intent);
        }
    }
}