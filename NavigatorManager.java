package com.ApkEditor.pro.file;

import android.os.Environment;

import java.io.File;
import java.util.Stack;

public class NavigatorManager {

    private File currentDir;
    private File rootDir;
    private Stack<File> history = new Stack<File>();

    public interface OnChangeListener {
        void onChange(File newDir);
    }

    private OnChangeListener listener;

    public NavigatorManager(OnChangeListener listener) {
        this.listener = listener;

        rootDir = Environment.getExternalStorageDirectory();
        currentDir = rootDir;
    }

    public File getCurrent() {
        return currentDir;
    }

    public void setRoot(File file) {
        if (file != null && file.isDirectory()) {
            rootDir = file;
            currentDir = file;
            history.clear();
            notifyChange();
        }
    }

    public void open(File file) {
        if (file != null && file.isDirectory()) {
            history.push(currentDir);
            currentDir = file;
            notifyChange();
        }
    }

    public boolean back() {

        if (!history.isEmpty()) {
            currentDir = history.pop();
            notifyChange();
            return true;
        }

        if (!currentDir.equals(rootDir)) {
            currentDir = rootDir;
            notifyChange();
            return true;
        }

        return false;
    }

    public boolean canGoBack() {
        return !history.isEmpty() || !currentDir.equals(rootDir);
    }

    public void reset() {
        currentDir = rootDir;
        history.clear();
        notifyChange();
    }

    private void notifyChange() {
        if (listener != null) {
            listener.onChange(currentDir);
        }
    }
}