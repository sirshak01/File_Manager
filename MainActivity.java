package com.ApkEditor.pro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import com.ApkEditor.pro.file.FileClipboard;
import com.ApkEditor.pro.file.FileOpener;
import com.ApkEditor.pro.file.FileUtils;
import com.ApkEditor.pro.file.NavigatorManager;
import com.ApkEditor.pro.file.FileActionMenu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity {

File currentDir;  
ArrayList<File> files;  
ArrayList<String> names;  

ListView listView;  
TextView pathText;  

boolean multiSelect = false;  
ArrayList<File> selected;  

NavigatorManager navigator;  

// ✅ ADDED: floating paste button  
Button pasteBtn;  

@Override  
protected void onCreate(Bundle savedInstanceState) {  
    super.onCreate(savedInstanceState);  

    requestPerm();  

    files = new ArrayList<File>();  
    names = new ArrayList<String>();  
    selected = new ArrayList<File>();  

    LinearLayout root = new LinearLayout(this);  
    root.setOrientation(LinearLayout.VERTICAL);  

    LinearLayout top = new LinearLayout(this);  
    top.setOrientation(LinearLayout.HORIZONTAL);  

    pathText = new TextView(this);  
    Button btnCreate = new Button(this);  
    btnCreate.setText("+");  

    listView = new ListView(this);  

    top.addView(pathText);  
    top.addView(btnCreate);  

    root.addView(top);  
    root.addView(listView);  

    setContentView(root);  

    currentDir = Environment.getExternalStorageDirectory();  

    navigator = new NavigatorManager(new NavigatorManager.OnChangeListener() {  
        @Override  
        public void onChange(File newDir) {  
            currentDir = newDir;  
            load();  
        }  
    });  

    load();  

    // =========================  
    // FLOATING PASTE BUTTON UI  
    // =========================  
    pasteBtn = new Button(this);  
    pasteBtn.setText("⬇");  
    pasteBtn.setTextSize(18f);  
    pasteBtn.setBackgroundColor(0xFF00C853); // green  
    pasteBtn.setTextColor(0xFFFFFFFF);  

    FrameLayout.LayoutParams params =  
            new FrameLayout.LayoutParams(  
                    FrameLayout.LayoutParams.WRAP_CONTENT,  
                    FrameLayout.LayoutParams.WRAP_CONTENT  
            );  

    params.gravity = Gravity.BOTTOM | Gravity.END;  
    params.setMargins(0, 0, 50, 50);  

    addContentView(pasteBtn, params);  

    pasteBtn.setVisibility(View.GONE);  

    pasteBtn.setOnClickListener(new View.OnClickListener() {  
        @Override  
        public void onClick(View v) {  

            FileActionMenu.paste(MainActivity.this, currentDir);  

            FileClipboard.clear();  

            updatePasteButton();  

            load();  
        }  
    });  

    updatePasteButton();  

    // CLICK  
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
        @Override  
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  

            File file = files.get(position);  

            if (multiSelect) {  
                if (selected.contains(file)) selected.remove(file);  
                else selected.add(file);  

                Toast.makeText(MainActivity.this,  
                        "Selected: " + selected.size(),  
                        Toast.LENGTH_SHORT).show();  
                return;  
            }  

            if (file.isDirectory()) {  
                navigator.open(file);  
            } else {  
                FileOpener.open(MainActivity.this, file);  
            }  
        }  
    });  

    // LONG CLICK  
    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {  
        @Override  
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {  

            final File file = files.get(position);  

            final String[] options = new String[]{  
                    "Info",  
                    "Delete",  
                    "Rename",  
                    "Multi-select ON",  
                    "Multi-select OFF"  
            };  

            new AlertDialog.Builder(MainActivity.this)  
                    .setTitle(file.getName())  
                    .setItems(options, new DialogInterface.OnClickListener() {  
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  

                            if (which == 0) showInfo(file);  

                            if (which == 1) {  
                                FileUtils.deleteFile(file);  
                                load();  
                            }  

                            if (which == 2) renameFile(file);  

                            if (which == 3) {  
                                multiSelect = true;  
                                selected.clear();  
                                Toast.makeText(MainActivity.this, "Multi-select ON", Toast.LENGTH_SHORT).show();  
                            }  

                            if (which == 4) {  
                                multiSelect = false;  
                                selected.clear();  
                                Toast.makeText(MainActivity.this, "Multi-select OFF", Toast.LENGTH_SHORT).show();  
                            }  
                        }  
                    })  
                    .show();  

            return true;  
        }  
    });  

    // CREATE BUTTON  
    btnCreate.setOnClickListener(new View.OnClickListener() {  
        @Override  
        public void onClick(View v) {  

            final String[] options = new String[]{"New File", "New Folder"};  

            new AlertDialog.Builder(MainActivity.this)  
                    .setTitle("Create")  
                    .setItems(options, new DialogInterface.OnClickListener() {  
                        @Override  
                        public void onClick(DialogInterface dialog, final int which) {  

                            final EditText input = new EditText(MainActivity.this);  

                            new AlertDialog.Builder(MainActivity.this)  
                                    .setTitle("Name")  
                                    .setView(input)  
                                    .setPositiveButton("Create", new DialogInterface.OnClickListener() {  
                                        @Override  
                                        public void onClick(DialogInterface d, int w) {  

                                            String name = input.getText().toString().trim();  

                                            if (name.length() == 0 || currentDir == null) return;  

                                            File f = new File(currentDir, name);  

                                            try {  
                                                if (which == 0) f.createNewFile();  
                                                else f.mkdir();  
                                            } catch (Exception e) {
                                                Toast.makeText(MainActivity.this, "Error creating file", Toast.LENGTH_SHORT).show();
                                            }  

                                            load();  
                                        }  
                                    })  
                                    .setNegativeButton("Cancel", null)  
                                    .show();  
                        }  
                    })  
                    .show();  
        }  
    });  
}  

// =========================  
// PASTE BUTTON CONTROL  
// =========================  
void updatePasteButton() {  
    if (FileClipboard.hasData()) {  
        pasteBtn.setVisibility(View.VISIBLE);  
    } else {  
        pasteBtn.setVisibility(View.GONE);  
    }  
}  

@Override  
public void onBackPressed() {  
    if (navigator != null && navigator.back()) return;  
    super.onBackPressed();  
}  

void requestPerm() {  
    if (Build.VERSION.SDK_INT >= 30) {  
        if (!Environment.isExternalStorageManager()) {  
            try {  
                startActivity(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION));  
            } catch (Exception e) {  
                startActivity(new Intent(Settings.ACTION_SETTINGS));  
            }  
        }  
    }  
}  

void load() {  

    files.clear();  
    names.clear();  

    if (currentDir == null || !currentDir.exists()) {  
        currentDir = Environment.getExternalStorageDirectory();  
    }  

    pathText.setText(currentDir.getAbsolutePath());  

    File[] list = currentDir.listFiles();  

    if (list != null) {  
        for (File file : list) {  

            files.add(file);  

            String icon;  

            if (file.isDirectory()) icon = "📁 ";  
            else if (FileUtils.isImage(file)) icon = "🖼 ";  
            else icon = "📄 ";  

            String info = icon + file.getName();  

            if (!file.isDirectory()) {  
                info += "\nSize: " + FileUtils.getSize(file);  

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                Date date = new Date(file.lastModified());  

                info += "\nDate: " + sdf.format(date);  
            }  

            names.add(info);  
        }  
    }  

    ArrayAdapter<String> adapter =  
            new ArrayAdapter<String>(  
                    this,  
                    android.R.layout.simple_list_item_1,  
                    names  
            );  

    listView.setAdapter(adapter);  
}  

void renameFile(final File file) {  

    final EditText input = new EditText(this);  
    input.setText(file.getName());  

    new AlertDialog.Builder(this)  
            .setTitle("Rename")  
            .setView(input)  
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {  
                @Override  
                public void onClick(DialogInterface d, int w) {  

                    String newName = input.getText().toString().trim();  
                    if (newName.length() == 0) return;  

                    File newFile = new File(file.getParent(), newName);  
                    file.renameTo(newFile);  

                    load();  
                }  
            })  
            .setNegativeButton("Cancel", null)  
            .show();  
}  

void showInfo(File file) {  

    String msg =  
            "Name: " + file.getName() +  
                    "\nPath: " + file.getAbsolutePath() +  
                    "\nSize: " + FileUtils.getSize(file) +  
                    "\nLast Modified: " + new Date(file.lastModified());  

    new AlertDialog.Builder(this)  
            .setTitle("Info")  
            .setMessage(msg)  
            .setPositiveButton("OK", null)  
            .show();  
}

}