package com.ApkEditor.pro.file;

import java.io.File;
import java.util.ArrayList;

public class Clipboard {

    public static final int COPY = 1;
    public static final int CUT = 2;

    private static ArrayList<File> files = new ArrayList<>();
    private static int mode = COPY;

    // Set clipboard data
    public static void set(ArrayList<File> list, int m) {
        if (list == null) return;

        files.clear();
        files.addAll(list);
        mode = m;
    }

    // Get files
    public static ArrayList<File> getFiles() {
        return files;
    }

    // Get mode (COPY or CUT)
    public static int getMode() {
        return mode;
    }

    // Check if clipboard has data
    public static boolean hasData() {
        return files != null && !files.isEmpty();
    }

    // Clear clipboard
    public static void clear() {
        files.clear();
        mode = COPY;
    }
}