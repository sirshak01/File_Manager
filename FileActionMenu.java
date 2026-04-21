package com.ApkEditor.pro.file;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;
import com.ApkEditor.pro.file.FileClipboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class FileActionMenu {

    public static void show(final Context context, final File file) {

        if (!(context instanceof Activity)) return;

        final Activity activity = (Activity) context;

        final String[] items = new String[]{
                "Info",
                "Copy",
                "Cut",
                "Paste",
                "Delete",
                "Rename",
                "Permissions"
        };

        new AlertDialog.Builder(activity)
                .setTitle(file.getName())
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {

                            case 0:
                                toast(context,
                                        "Size: " + file.length() +
                                        "\nRead: " + file.canRead() +
                                        "\nWrite: " + file.canWrite());
                                break;

                            case 1:
                                FileClipboard.setSingle(file, FileClipboard.COPY);
                                break;

                            case 2:
                                FileClipboard.setSingle(file, FileClipboard.CUT);
                                break;

                            case 3:
                                paste(context, file);
                                break;

                            case 4:
                                delete(file);
                                break;

                            case 5:
                                rename(activity, file);
                                break;

                            case 6:
                                toast(context,
                                        "R: " + file.canRead() +
                                        " W: " + file.canWrite());
                                break;
                        }
                    }
                })
                .show();
    }

    // ✅ THIS IS THE IMPORTANT FIX (your error comes from here usage)
    public static void paste(final Context context, final File target) {

        if (!FileClipboard.hasData()) return;

        ArrayList<File> items = FileClipboard.getItems();

        for (File src : items) {

            File destDir = target.isDirectory()
                    ? target
                    : target.getParentFile();

            if (destDir == null) {
                toast(context, "Invalid path");
                return;
            }

            File dest = new File(destDir, src.getName());

            copyFile(src, dest);

            if (FileClipboard.getMode() == FileClipboard.CUT) {
                delete(src);
            }
        }

        FileClipboard.clear();
        toast(context, "Pasted");
    }

    static void rename(final Activity activity, final File file) {

        final EditText input = new EditText(activity);
        input.setText(file.getName());

        new AlertDialog.Builder(activity)
                .setTitle("Rename")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String name = input.getText().toString().trim();
                        if (name.length() == 0) return;

                        File newFile = new File(file.getParent(), name);
                        file.renameTo(newFile);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private static void copyFile(File src, File dest) {
        try {
            FileInputStream in = new FileInputStream(src);
            FileOutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[4096];
            int len;

            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

            in.close();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) delete(f);
            }
        }
        file.delete();
    }

    static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}