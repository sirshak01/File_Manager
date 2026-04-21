package com.ApkEditor.pro;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileViewerActivity extends Activity {

    EditText editor;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editor = new EditText(this);
        setContentView(editor);

        path = getIntent().getStringExtra("path");

        load();
    }

    void load() {
        try {
            File file = new File(path);

            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[fis.available()];

            fis.read(data);
            fis.close();

            editor.setText(new String(data));

        } catch (Exception e) {
            editor.setText("Cannot open file");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Save");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            File file = new File(path);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(editor.getText().toString().getBytes());
            fos.close();

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}