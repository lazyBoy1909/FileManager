package com.example.mailapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class MailItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<MailModel> items;
    ItemClickInterface itemClickInterface;
    public MailItemAdapter(List<MailModel> items, ItemClickInterface itemClickInterface)
    {
        this.items = items;
        this.itemClickInterface = itemClickInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MailModel item = items.get(position);
        ((MyViewHolder)holder).senderEmailTextView.setText(item.getSenderMail());
        ((MyViewHolder)holder).contentTextView.setText(item.getContent());
        ((MyViewHolder)holder).timeTextView.setText(item.getTime());
        ((MyViewHolder)holder).titleTextView.setText(item.getTitle());
        ((MyViewHolder)holder).avatarTextView.setText(""+item.getSenderMail().toUpperCase(Locale.ROOT).charAt(0));
        if(item.getInterested() == true) {
            ((MyViewHolder) holder).interestImageView.setImageResource(R.drawable.star_fill);
        }
        else
        {
            ((MyViewHolder) holder).interestImageView.setImageResource(R.drawable.star);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView senderEmailTextView;
        TextView timeTextView;
        TextView titleTextView;
        TextView contentTextView;
        ImageView interestImageView;
        TextView avatarTextView;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            senderEmailTextView = itemView.findViewById(R.id.sender_mail);
            timeTextView = itemView.findViewById(R.id.time);
            titleTextView = itemView.findViewById(R.id.title);
            contentTextView = itemView.findViewById(R.id.content);
            interestImageView = itemView.findViewById(R.id.interest);
            avatarTextView = itemView.findViewById(R.id.avatar);
            interestImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (itemClickInterface != null)
                    {
                        int pos = getAdapterPosition();
                        if(items.get(pos).getInterested() == true)
                        {
                            items.get(pos).setInterested(false);
                            interestImageView.setImageResource(R.drawable.star);
                            itemClickInterface.OnInterestClick(pos, false);
                        }
                        else
                        {
                            items.get(pos).setInterested(true);
                            interestImageView.setImageResource(R.drawable.star_fill);
                            itemClickInterface.OnInterestClick(pos, true);
                        }
                    }
                }
            });
        }
    }

    public interface ItemClickInterface {
       public void OnInterestClick(int position, Boolean state);
    }
}
