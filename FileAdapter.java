package com.ApkEditor.pro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class FileAdapter extends BaseAdapter {

    private List<File> files;
    private Click click;
    private LongClick longClick;

    public interface Click {
        void onClick(File file);
    }

    public interface LongClick {
        void onLong(File file);
    }

    public FileAdapter(List<File> files, Click click, LongClick longClick) {
        this.files = files;
        this.click = click;
        this.longClick = longClick;
    }

    @Override
    public int getCount() {
        return (files == null) ? 0 : files.size();
    }

    @Override
    public Object getItem(int position) {
        return (files == null || position < 0 || position >= files.size())
                ? null
                : files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class Holder {
        TextView name;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);

            holder = new Holder();
            holder.name = (TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }

        final File file = (files != null && position < files.size())
                ? files.get(position)
                : null;

        if (file == null) {
            holder.name.setText("Unknown");
            return convertView;
        }

        holder.name.setText(file.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null && file != null) {
                    click.onClick(file);
                }
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClick != null && file != null) {
                    longClick.onLong(file);
                }
                return true;
            }
        });

        return convertView;
    }

    public void update(List<File> newFiles) {
        this.files = newFiles;
        notifyDataSetChanged();
    }
}