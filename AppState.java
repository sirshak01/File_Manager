package com.ApkEditor.pro.core;

import android.net.Uri;
import java.util.Stack;

public class AppState {

    public static Uri currentUri = null;
    public static Uri rootUri = null;
    public static Stack<Uri> history = new Stack<Uri>();

    public static void setRoot(Uri uri) {
        rootUri = uri;
        currentUri = uri;
        history.clear();
    }

    public static void openFolder(Uri uri) {
        if (currentUri != null) {
            history.push(currentUri);
        }
        currentUri = uri;
    }

    public static boolean canGoBack() {
        return !history.isEmpty();
    }

    public static Uri goBack() {
        if (!canGoBack()) {
            return rootUri;
        }

        currentUri = history.pop();
        return currentUri;
    }

    public static void reset() {
        currentUri = rootUri;
        history.clear();
    }

    public AppState() {
        // not needed
    }
}