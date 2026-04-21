package com.ApkEditor.pro.file;

import java.io.File;
import java.util.ArrayList;

public class SelectionManager {

    private static ArrayList<File> selected = new ArrayList<>();

    // Toggle selection
    public static void toggle(File file) {
        if (file == null) return;

        if (selected.contains(file)) {
            selected.remove(file);
        } else {
            selected.add(file);
        }
    }

    // Check if selected
    public static boolean isSelected(File file) {
        return file != null && selected.contains(file);
    }

    // Get all selected files
    public static ArrayList<File> getAll() {
        return selected;
    }

    // Clear selection
    public static void clear() {
        selected.clear();
    }
}