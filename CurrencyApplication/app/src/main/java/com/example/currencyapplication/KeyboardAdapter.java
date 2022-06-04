package com.example.currencyapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KeyboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<String> items;
    ItemClickInterface itemClickInterface;

    public KeyboardAdapter(List<String> items, ItemClickInterface clickInterface)
    {
        this.items = items;
        this.itemClickInterface = clickInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String item = items.get(position);
        ((MyViewHolder)holder).itemValue.setText(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemValue;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemValue = itemView.findViewById(R.id.item_value);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (itemClickInterface != null)
                        itemClickInterface.OnItemClick(pos);
                }
            });
        }
    }

    public interface ItemClickInterface {
        void OnItemClick(int position);
    }
}
