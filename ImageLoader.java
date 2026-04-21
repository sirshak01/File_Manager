package com.ApkEditor.pro.file;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

import java.io.File;

public class ImageLoader {

    public static Bitmap load(String path) {

        if (path == null || path.isEmpty()) {
            return null;
        }

        try {
            File file = new File(path);

            // validate file
            if (!file.exists() || file.length() == 0) {
                return null;
            }

            // decoding options
            Options options = new Options();
            options.inSampleSize = 2; // basic compression for performance
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            return BitmapFactory.decodeFile(path, options);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}