package com.example.filemanagerapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends BaseAdapter {
    private List<String> data;
    private FileInterface fileInterface;

    public FileAdapter(List<String> data, FileInterface fileInterface) {
        this.fileInterface = fileInterface;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.file_item, viewGroup, false);
            view.setTag(new ViewHolder((TextView) view.findViewById(R.id.file_item), (ImageView) view.findViewById(R.id.symbol)));
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        final String fileItem = getItem(i);
        viewHolder.info.setText(fileItem);
        if(fileInterface.isDirectory(i)){
            viewHolder.symbol.setImageResource(R.drawable.ic_folder);
        } else {
            viewHolder.symbol.setImageResource(R.drawable.ic_file);

        }
        return view;
    }

    class ViewHolder {
        TextView info;
        ImageView symbol;
        ViewHolder(TextView info, ImageView symbol) {
            this.symbol = symbol;
            this.info = info;
        }
    }

    interface FileInterface {
        public Boolean isDirectory(int index);
    }
}
