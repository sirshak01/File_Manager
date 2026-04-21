package com.ApkEditor.pro.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    // ---------------- DELETE FILE / FOLDER ----------------
    public static boolean deleteFile(File file) {

        if (file == null || !file.exists()) return false;

        if (file.isDirectory()) {

            File[] children = file.listFiles();

            if (children != null) {
                for (File child : children) {
                    deleteFile(child);
                }
            }
        }

        return file.delete();
    }

    // ---------------- LIST FILES ----------------
    public static List<File> listFiles(File dir, boolean showHidden) {

        List<File> result = new ArrayList<File>();

        if (dir == null || !dir.exists()) return result;

        File[] files = dir.listFiles();
        if (files == null) return result;

        for (File f : files) {

            if (!showHidden && f.getName().startsWith(".")) {
                continue;
            }

            result.add(f);
        }

        return result;
    }

    // ---------------- SIZE FORMAT ----------------
    public static String getSize(File file) {

        if (file == null) return "0B";
        if (file.isDirectory()) return "--";

        long size = file.length();

        if (size <= 0) return "0B";

        final String[] units = {"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#.##").format(
                size / Math.pow(1024, digitGroups)
        ) + " " + units[digitGroups];
    }

    // ---------------- IMAGE CHECK ----------------
    public static boolean isImage(File file) {

        if (file == null) return false;

        String name = file.getName().toLowerCase();

        return name.endsWith(".png") ||
               name.endsWith(".jpg") ||
               name.endsWith(".jpeg") ||
               name.endsWith(".gif") ||
               name.endsWith(".webp");
    }

    // ---------------- FILE TYPE ----------------
    public static String getFileType(File file) {

        if (file == null) return "unknown";

        if (file.isDirectory()) return "folder";

        String name = file.getName().toLowerCase();

        if (name.endsWith(".apk")) return "apk";
        if (isImage(file)) return "image";
        if (name.endsWith(".zip")) return "zip";
        if (name.endsWith(".txt")) return "text";

        return "file";
    }
}