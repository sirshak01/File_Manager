package com.ApkEditor.pro.file;

import java.io.File;
import java.util.ArrayList;

public class FileManager {

    private File current;

    // Constructor
    public FileManager(File file) {
        if (file != null && file.isDirectory()) {
            this.current = file;
        } else {
            this.current = new File("/");
        }
    }

    // Get current directory
    public File getCurrent() {
        return current;
    }

    // Go to folder
    public void go(File file) {
        if (file != null && file.isDirectory()) {
            current = file;
        }
    }

    // Go back to parent folder
    public void back() {
        if (current != null && current.getParentFile() != null) {
            current = current.getParentFile();
        }
    }

    // List files safely
    public ArrayList<File> list() {

        ArrayList<File> list = new ArrayList<>();

        if (current == null || !current.exists()) {
            return list;
        }

        File[] files = current.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f != null) {
                    list.add(f);
                }
            }
        }

        return list;
    }
}