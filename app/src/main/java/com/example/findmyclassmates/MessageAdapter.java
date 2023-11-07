package com.example.findmyclassmates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages; // Message is a custom class that you'll need to define.

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.senderTextView.setText(message.getSender());
        holder.timestampTextView.setText(message.getTimestamp());
        holder.messageBodyTextView.setText(message.getBody());
        // Add click listener if needed
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView senderTextView;
        TextView timestampTextView;
        TextView messageBodyTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.senderTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            messageBodyTextView = itemView.findViewById(R.id.messageBodyTextView);
        }
    }
}
