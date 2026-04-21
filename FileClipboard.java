package com.ApkEditor.pro.file;

import java.io.File;
import java.util.ArrayList;

public class FileClipboard {

    public static final int COPY = 0;
    public static final int CUT = 1;

    // CHANGED: store paths instead of File objects (Option 6)
    private static ArrayList<String> items = new ArrayList<String>();
    private static int mode = COPY;

    // Set single file to clipboard
    public static void setSingle(File file, int m) {

        if (file == null) return;

        items.clear();
        items.add(file.getAbsolutePath()); // CHANGED
        mode = m;
    }

    // Get items (converted back to File objects for compatibility)
    public static ArrayList<File> getItems() {

        ArrayList<File> list = new ArrayList<File>();

        for (String path : items) {
            if (path != null) {
                list.add(new File(path));
            }
        }

        return list;
    }

    // Get mode
    public static int getMode() {
        return mode;
    }

    // Check if clipboard has data
    public static boolean hasData() {
        return items != null && !items.isEmpty();
    }

    // Clear clipboard
    public static void clear() {
        items.clear();
        mode = COPY;
    }
}