package com.example.findmyclassmates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messages;

    // Constructor
    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    // ViewHolder class
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        // Define the View elements
        public TextView senderTextView;
        public TextView contentTextView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.senderTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
        }
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        // Set item views based on the data model
        Message message = messages.get(position);
        holder.senderTextView.setText(message.getSender());
        holder.contentTextView.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // Method to update the message list in the adapter
    public void updateMessages(List<Message> newMessages) {
        messages = newMessages;
        notifyDataSetChanged();
    }
}
