package com.ApkEditor.pro.file;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ImageViewerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        try {
            String path = getIntent().getStringExtra("path");

            if (path == null) {
                Toast.makeText(this, "No image path", Toast.LENGTH_SHORT).show();
                setContentView(imageView);
                return;
            }

            File file = new File(path);

            InputStream inputStream = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this, "Invalid image", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Open error", Toast.LENGTH_SHORT).show();
        }

        setContentView(imageView);
    }
}