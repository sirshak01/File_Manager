package com.ApkEditor.pro;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import java.io.*;

public class ApkCompilerActivity extends Activity {
    public void onCreate(android.os.Bundle b) {
        super.onCreate(b);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        layout.setPadding(30, 30, 30, 30);

        final EditText pathEdit = new EditText(this);
        pathEdit.setHint("/storage/emulated/0/MyFolder");

        Button btn = new Button(this);
        btn.setText("COMPILE");

        final TextView status = new TextView(this);

        layout.addView(pathEdit);
        layout.addView(btn);
        layout.addView(status);
        setContentView(layout);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String FINAL_PATH = pathEdit.getText().toString();
                final TextView FINAL_STATUS = status;
                new Thread() {
                    public void run() {
                        compile(FINAL_PATH, FINAL_STATUS);
                    }
                }.start();
            }
        });
    }

    void compile(String folderPath, final TextView status) {
        final File FINAL_APK = new File("/storage/emulated/0/NewApp.apk");
        try {
            File folder = new File(folderPath);
            
            java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(new FileOutputStream(FINAL_APK));
            addFiles(folder, folder, zos);
            zos.close();
            
            runOnUiThread(new Runnable() {
                public void run() {
                    status.setText("APK: " + FINAL_APK.getAbsolutePath());
                }
            });
        } catch (final Exception FINAL_EX) {
            runOnUiThread(new Runnable() {
                public void run() {
                    status.setText("Error: " + FINAL_EX.getMessage());
                }
            });
        }
    }

    void addFiles(File base, File dir, java.util.zip.ZipOutputStream zos) throws IOException {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    addFiles(base, f, zos);
                } else {
                    java.util.zip.ZipEntry ze = new java.util.zip.ZipEntry(
                        f.getPath().substring(base.getPath().length() + 1));
                    zos.putNextEntry(ze);
                    FileInputStream fis = new FileInputStream(f);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = fis.read(buf)) > 0) {
                        zos.write(buf, 0, len);
                    }
                    fis.close();
                    zos.closeEntry();
                }
            }
        }
    }
}