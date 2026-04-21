package com.ApkEditor.pro.navigation;

import android.net.Uri;
import com.ApkEditor.pro.core.AppState;

public class NavigationManager {
    public static void open(Uri uri) {
        AppState.openFolder(uri);
    }

    public static Uri back() {
        return AppState.goBack();
    }

    public static Uri home() {
        AppState.reset();
        return AppState.rootUri;
    }

    public static boolean canGoBack() {
        return AppState.canGoBack();
    }

    public NavigationManager() {
        NavigationManager navigationManager = this;
    }
}
