package com.ApkEditor.pro.smart;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;

public class SmartFileEngine {

    public static String getType(File f) {

        if (f == null) return "unknown";
        if (f.isDirectory()) return "folder";

        String n = f.getName().toLowerCase();

        if (n.endsWith(".png") || n.endsWith(".jpg") || n.endsWith(".jpeg")) return "image";
        if (n.endsWith(".txt") || n.endsWith(".log")) return "text";
        if (n.endsWith(".apk")) return "apk";
        if (n.endsWith(".zip")) return "zip";
        if (n.endsWith(".mp4") || n.endsWith(".mkv")) return "video";
        if (n.endsWith(".mp3") || n.endsWith(".wav")) return "audio";

        return "file";
    }

    public static String getIcon(File f) {

        String t = getType(f);

        if (t.equals("folder")) return "📁";
        if (t.equals("image")) return "🖼️";
        if (t.equals("text")) return "📄";
        if (t.equals("apk")) return "📦";
        if (t.equals("zip")) return "🗜️";
        if (t.equals("video")) return "🎬";
        if (t.equals("audio")) return "🎵";

        return "📄";
    }

    public static String getSize(File f) {

        if (f == null || f.isDirectory()) return "--";

        long s = f.length();

        String[] u = {"B","KB","MB","GB"};

        int i = (int)(Math.log10(s)/Math.log10(1024));
        if (i < 0) i = 0;
        if (i >= u.length) i = u.length - 1;

        return new DecimalFormat("#.##").format(
                s / Math.pow(1024, i)
        ) + " " + u[i];
    }

    public static String getInfo(File f) {

        return "Name: " + f.getName() +
                "\nPath: " + f.getAbsolutePath() +
                "\nType: " + getType(f) +
                "\nSize: " + getSize(f) +
                "\nModified: " + new Date(f.lastModified()).toString();
    }
}