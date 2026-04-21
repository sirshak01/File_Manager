import android.content.Context;
import android.content.SharedPreferences;

public class HiddenFilesManager {

    private static final String PREF_NAME = "file_manager_prefs";
    private static final String SHOW_HIDDEN_KEY = "show_hidden_files";

    // Check if hidden files should be shown
    public static boolean shouldShowHidden(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(SHOW_HIDDEN_KEY, false);
    }

    // Toggle hidden files visibility
    public static void toggleHiddenFiles(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean current = prefs.getBoolean(SHOW_HIDDEN_KEY, false);
        prefs.edit().putBoolean(SHOW_HIDDEN_KEY, !current).apply();
    }

    // Set hidden files visibility
    public static void setShowHidden(Context context, boolean show) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(SHOW_HIDDEN_KEY, show).apply();
    }
}