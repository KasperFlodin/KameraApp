package com.example.kameraapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kameraapp.ImageAdapter;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Uri> imageUris;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        // Initialize imageUris and handle possible null values
        imageUris = getAllShownImagesPath(this);

        // Initialize ImageAdapter with imageUris
        imageAdapter = new ImageAdapter(imageUris);
        recyclerView.setAdapter(imageAdapter);
    }

    private ArrayList<Uri> getAllShownImagesPath(Context context) {
        ArrayList<Uri> listOfAllImages = new ArrayList<>();
        String[] projection = new String[]{MediaStore.Images.Media._ID};

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED + " DESC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                Uri contentUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
                listOfAllImages.add(contentUri);
            }
            cursor.close();
        }

        return listOfAllImages;
    }
}
