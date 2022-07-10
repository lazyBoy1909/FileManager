package com.example.filemanagerapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends BaseAdapter {
    private List<String> data;
    private boolean[] selection;

    public FileAdapter(List<String> data) {
        this.data = data;
    }

    public void setData(List<String> updateData) {
        if(updateData != null) {
            this.data.clear();
            if(updateData.size() > 0) {
                this.data.addAll(updateData);
                Log.v("TAG", ""+updateData.size());
            }
            notifyDataSetChanged();
        }
    }

    void setSelection(boolean[] selection) {
        if(selection != null) {
            this.selection = new boolean[selection.length];
            for(int i=0;i<selection.length;i++) {
                this.selection[i] = selection[i];
            }
            notifyDataSetChanged();
        }
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
            view.setTag(new ViewHolder((TextView) view.findViewById(R.id.file_item)));
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        final String fileItem = getItem(i);
        viewHolder.info.setText(fileItem);
        if(selection != null) {
            if(selection[i]){
                viewHolder.info.setBackgroundColor(R.color.purple_200);
                Log.v("TAG", "change color at "+i);
            }
            else {
                viewHolder.info.setBackgroundColor(R.color.teal_700);
            }
        }
        return view;
    }

    class ViewHolder {
        TextView info;
        ViewHolder(TextView info) {
            this.info = info;
        }
    }
}
