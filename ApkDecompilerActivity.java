package com.ApkEditor.pro;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import java.io.*;

public class ApkDecompilerActivity extends Activity {
    public void onCreate(android.os.Bundle b) {
        super.onCreate(b);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        layout.setPadding(30, 30, 30, 30);

        final EditText pathEdit = new EditText(this);
        pathEdit.setHint("/storage/emulated/0/app.apk");

        Button btn = new Button(this);
        btn.setText("DECOMPILE");

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
                        decompile(FINAL_PATH, FINAL_STATUS);
                    }
                }.start();
            }
        });
    }

    void decompile(String apkPath, final TextView status) {
        final File FINAL_OUTDIR = new File("/storage/emulated/0/Decompiled");
        try {
            FINAL_OUTDIR.mkdirs();
            
            java.util.zip.ZipFile zip = new java.util.zip.ZipFile(apkPath);
            java.util.Enumeration entries = zip.entries();
            while (entries.hasMoreElements()) {
                java.util.zip.ZipEntry e = (java.util.zip.ZipEntry) entries.nextElement();
                File f = new File(FINAL_OUTDIR, e.getName());
                if (e.isDirectory()) {
                    f.mkdirs();
                } else {
                    f.getParentFile().mkdirs();
                    FileOutputStream fos = new FileOutputStream(f);
                    java.io.InputStream is = zip.getInputStream(e);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = is.read(buf)) > 0) {
                        fos.write(buf, 0, len);
                    }
                    is.close();
                    fos.close();
                }
            }
            zip.close();
            
            runOnUiThread(new Runnable() {
                public void run() {
                    status.setText("Done: " + FINAL_OUTDIR.getAbsolutePath());
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
}