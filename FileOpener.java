package com.ApkEditor.pro.file;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.File;

import com.ApkEditor.pro.FileViewerActivity;
import com.ApkEditor.pro.ImageViewerActivity;

public class FileOpener {

    public static void open(Context context, File file) {
        try {

            if (file == null || !file.exists()) {
                Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent;

            // IMAGE FILES
            if (FileUtils.isImage(file)) {

                intent = new Intent(context, ImageViewerActivity.class);
                intent.putExtra("path", file.getAbsolutePath());
                context.startActivity(intent);
                return;
            }

            // TEXT FILES
            if (FileUtils.isTextFile(file)) {

                intent = new Intent(context, FileViewerActivity.class);
                intent.putExtra("path", file.getAbsolutePath());
                context.startActivity(intent);
                return;
            }

            // UNSUPPORTED
            Toast.makeText(context, "Unsupported file type", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, "Open error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // empty constructor (not required but kept for safety in AIDE projects)
    public FileOpener() {
    }
}