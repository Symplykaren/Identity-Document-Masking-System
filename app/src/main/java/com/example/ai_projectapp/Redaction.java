// FUNCTIONING FACE REDACTION SCRIPT //
package com.example.ai_projectapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.BitmapFactory;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.io.OutputStream;

import android.util.Base64;
import org.json.JSONObject;

import java.net.MalformedURLException;

public class Redaction extends AppCompatActivity {

    private byte[] imageData;

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
        imageData = getIntent().getByteArrayExtra("image_data");

        // Display the image in an ImageView
        ImageView imageView = findViewById(R.id.displayImageView);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        imageView.setImageBitmap(bitmap);

        // Handle Face button click
        Button faceButton = findViewById(R.id.cameraBtn);
        faceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform face redaction
                performFaceRedaction();
            }
        });

        // Handle Text button click
        Button textButton = findViewById(R.id.galleryBtn);
        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform text redaction (you can implement this similar to performFaceRedaction)
                performTextRedaction();
            }
        });
    }

    // Executor for background tasks
    private final Executor executor = Executors.newSingleThreadExecutor();

    // Method to perform face redaction using Executor
    private void performFaceRedaction() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create a URL for Flask API endpoint
                    URL url = new URL(" https://a179-168-131-152-90.ngrok.io/face_redaction");

                    // Open a connection to the URL
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    try {
                        // Set up the connection properties
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setDoOutput(true);

                        // Write the image data to the connection's output stream
                        try (OutputStream os = urlConnection.getOutputStream()) {
                            os.write(imageData);
                        }

                        // Read the processed image data from the connection's input stream
                        try (InputStream inputStream = urlConnection.getInputStream()) {
                            // Convert InputStream to Bitmap
                            Bitmap processedBitmap = BitmapFactory.decodeStream(inputStream);

                            // Update UI on the main thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Update the ImageView with the processed image
                                    ImageView imageView = findViewById(R.id.displayImageView);
                                    imageView.setImageBitmap(processedBitmap);
                                }
                            });
                        }
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    // Handle error on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Redaction.this, "Error processing image", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    // Method to perform text redaction using Executor
    private void performTextRedaction() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create a URL for Flask API endpoint for text redaction
                    URL url = new URL(" https://a179-168-131-152-90.ngrok.io/text_redaction");

                    // Open a connection to the URL
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    try {
                        // Set up the connection properties
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setDoOutput(true);

                        // Write the image data to the connection's output stream
                        try (OutputStream os = urlConnection.getOutputStream()) {
                            os.write(imageData);
                        }

                        // Read the processed image data from the connection's input stream
                        try (InputStream inputStream = urlConnection.getInputStream()) {
                            // Convert InputStream to Bitmap
                            Bitmap processedBitmap = BitmapFactory.decodeStream(inputStream);

                            // Update UI on the main thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Update the ImageView with the processed image
                                    ImageView imageView = findViewById(R.id.displayImageView);
                                    imageView.setImageBitmap(processedBitmap);
                                }
                            });
                        }
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    // Handle error on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Redaction.this, "Error processing image", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}









//package com.example.ai_projectapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import android.graphics.BitmapFactory;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//import java.io.OutputStream;
//
//public class Redaction extends AppCompatActivity {
//
//    private byte[] imageData;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_redaction);
//
//        // toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        // back button
//        assert getSupportActionBar() != null;
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        // Retrieve the image data from the Intent
//        imageData = getIntent().getByteArrayExtra("image_data");
//
//        // Display the image in an ImageView
//        ImageView imageView = findViewById(R.id.displayImageView);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
//        imageView.setImageBitmap(bitmap);
//
//        // Handle Face button click
//        Button faceButton = findViewById(R.id.cameraBtn);
//        faceButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Perform face redaction
//                performRedaction("face");
//            }
//        });
//
//        // Handle Text button click
//        Button textButton = findViewById(R.id.galleryBtn);
//        textButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Perform text redaction
//                performRedaction("text");
//            }
//        });
//    }
//
//    // Executor for background tasks
//    private final Executor executor = Executors.newSingleThreadExecutor();
//
//    // Method to perform redaction using Executor
//    private void performRedaction(String redactionType) {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    // Create a URL for your Flask API endpoint
//                    String endpoint = redactionType.equals("face") ? "face_redaction" : "text_redaction";
//                    URL url = new URL("https://187e-168-131-152-90.ngrok.io" + endpoint);
//
//                    // Open a connection to the URL
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//                    try {
//                        // Set up the connection properties
//                        urlConnection.setRequestMethod("POST");
//                        urlConnection.setDoOutput(true);
//
//                        // Write the image data to the connection's output stream
//                        try (OutputStream os = urlConnection.getOutputStream()) {
//                            os.write(imageData);
//                        }
//
//                        // Read the processed image data from the connection's input stream
//                        try (InputStream inputStream = urlConnection.getInputStream()) {
//                            // Convert InputStream to byte array
//                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                            byte[] buffer = new byte[1024];
//                            int len;
//                            while ((len = inputStream.read(buffer)) != -1) {
//                                byteArrayOutputStream.write(buffer, 0, len);
//                            }
//                            byte[] imageData = byteArrayOutputStream.toByteArray();
//
//                            // Convert byte array to Bitmap
//                            Bitmap processedBitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
//
//                            // Update UI on the main thread
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // Update the ImageView with the processed image
//                                    ImageView imageView = findViewById(R.id.displayImageView);
//                                    imageView.setImageBitmap(processedBitmap);
//                                }
//                            });
//                        }
//                    } finally {
//                        urlConnection.disconnect();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                    // Handle error on the main thread
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(Redaction.this, "Error processing image", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });
//    }
//}






                         //// FUNCTIONING FACE REDACTION SCRIPT //
//package com.example.ai_projectapp;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import android.graphics.Bitmap;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import android.graphics.BitmapFactory;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//import java.io.OutputStream;
//
//import android.util.Base64;
//import org.json.JSONObject;
//
//import java.net.MalformedURLException;
//
//public class Redaction extends AppCompatActivity {
//
//    private byte[] imageData;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_redaction);
//
//        // toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        // back button
//        assert getSupportActionBar() != null;
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        // Retrieve the image data from the Intent
//        imageData = getIntent().getByteArrayExtra("image_data");
//
//        // Display the image in an ImageView
//        ImageView imageView = findViewById(R.id.displayImageView);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
//        imageView.setImageBitmap(bitmap);
//
//        // Handle Face button click
//        Button faceButton = findViewById(R.id.cameraBtn);
//        faceButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Perform face redaction
//                performFaceRedaction();
//            }
//        });
//
//        // Handle Text button click
//        Button textButton = findViewById(R.id.galleryBtn);
//        textButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Perform text redaction (you can implement this similar to performFaceRedaction)
//            }
//        });
//    }
//
//    // Executor for background tasks
//    private final Executor executor = Executors.newSingleThreadExecutor();
//
//    // Method to perform face redaction using Executor
//    // Method to perform face redaction using Executor
//    private void performFaceRedaction() {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    // Create a URL for your Flask API endpoint
//                    URL url = new URL("https://7bbb-168-131-152-90.ngrok.io/face_redaction");
//
//                    // Open a connection to the URL
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//                    try {
//                        // Set up the connection properties
//                        urlConnection.setRequestMethod("POST");
//                        urlConnection.setDoOutput(true);
//
//                        // Write the image data to the connection's output stream
//                        try (OutputStream os = urlConnection.getOutputStream()) {
//                            os.write(imageData);
//                        }
//
//                        // Read the processed image data from the connection's input stream
//                        try (InputStream inputStream = urlConnection.getInputStream()) {
//                            // Convert InputStream to Bitmap
//                            Bitmap processedBitmap = BitmapFactory.decodeStream(inputStream);
//
//                            // Update UI on the main thread
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // Update the ImageView with the processed image
//                                    ImageView imageView = findViewById(R.id.displayImageView);
//                                    imageView.setImageBitmap(processedBitmap);
//                                }
//                            });
//                        }
//                    } finally {
//                        urlConnection.disconnect();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                    // Handle error on the main thread
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(Redaction.this, "Error processing image", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });
//    }
//}









//package com.example.ai_projectapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import android.graphics.Bitmap;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import android.graphics.BitmapFactory;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//public class Redaction extends AppCompatActivity {
//
//    private byte[] imageData;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_redaction);
//
//        // toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        // back button
//        assert getSupportActionBar() != null;
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        // Retrieve the image data from the Intent
//        imageData = getIntent().getByteArrayExtra("image_data");
//
//        // Display the image in an ImageView
//        ImageView imageView = findViewById(R.id.displayImageView);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
//        imageView.setImageBitmap(bitmap);
//
//        // Handle Face button click
//        Button faceButton = findViewById(R.id.cameraBtn);
//        faceButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Perform face redaction
//                performFaceRedaction();
//            }
//        });
//
//        // Handle Text button click
//        Button textButton = findViewById(R.id.galleryBtn);
//        textButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Perform text redaction (you can implement this similar to performFaceRedaction)
//            }
//        });
//    }
//
//    // Executor for background tasks
//    private final Executor executor = Executors.newSingleThreadExecutor();
//
//    // Method to perform face redaction using Executor
//    private void performFaceRedaction() {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    // Create a URL for your Flask API endpoint
//                    URL url = new URL("https://a019-168-131-152-90.ngrok.io/face_redaction");
//
//                    // Open a connection to the URL
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//                    try {
//                        // Set up the connection properties
//                        urlConnection.setRequestMethod("POST");
//                        urlConnection.setDoOutput(true);
//
//                        // Write the image data to the connection's output stream
//                        urlConnection.getOutputStream().write(imageData);
//
//                        // Read the processed image data from the connection's input stream
//                        InputStream inputStream = urlConnection.getInputStream();
//                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                        byte[] buffer = new byte[1024];
//                        int len;
//                        while ((len = inputStream.read(buffer)) != -1) {
//                            outputStream.write(buffer, 0, len);
//                        }
//
//                        // Update UI on the main thread
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                // Update the ImageView with the processed image
//                                ImageView imageView = findViewById(R.id.displayImageView);
//                                Bitmap processedBitmap = BitmapFactory.decodeByteArray(outputStream.toByteArray(), 0, outputStream.size());
//                                imageView.setImageBitmap(processedBitmap);
//                            }
//                        });
//                    } finally {
//                        urlConnection.disconnect();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                    // Handle error on the main thread
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(Redaction.this, "Error processing image", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });
//    }
//}










//package com.example.ai_projectapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.graphics.BitmapFactory;
//
//public class Redaction extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_redaction);
//
//        // toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        // back button
//        assert getSupportActionBar() != null;
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        // Retrieve the image data from the Intent
//        byte[] imageData = getIntent().getByteArrayExtra("image_data");
//
//        // Now you can use the image data in this activity as needed
//        // For example, you can display it in an ImageView
//        ImageView imageView = findViewById(R.id.displayImageView);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
//        imageView.setImageBitmap(bitmap);
//    }
//}