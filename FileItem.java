package com.ApkEditor.pro.models;

import java.io.File;

public class FileItem {
    File file;

    public FileItem(File file) {
        FileItem fileItem = this;
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return this.file.getName();
    }

    public boolean isDirectory() {
        return this.file.isDirectory();
    }
}
