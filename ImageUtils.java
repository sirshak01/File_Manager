package com.ApkEditor.pro.file;

import java.io.File;

public class ImageUtils {
    public static boolean isImage(File file) {
        File file2 = file;
        if (file2 == null) {
            return false;
        }
        String toLowerCase = file2.getName().toLowerCase();
        boolean z = toLowerCase.endsWith(".png") || toLowerCase.endsWith(".jpg") || toLowerCase.endsWith(".jpeg") || toLowerCase.endsWith(".gif") || toLowerCase.endsWith(".webp") || toLowerCase.endsWith(".bmp");
        return z;
    }

    public ImageUtils() {
        ImageUtils imageUtils = this;
    }
}
