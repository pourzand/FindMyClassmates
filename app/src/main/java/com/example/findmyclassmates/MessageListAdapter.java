package com.example.findmyclassmates;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    private List<Message> messageList;

    public MessageListAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messageList != null ? messageList.size() : 0;
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView senderTextView, timestampTextView, messageBodyTextView;

        MessageViewHolder(View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.senderTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            messageBodyTextView = itemView.findViewById(R.id.messageBodyTextView);

            // Set the OnClickListener to the whole message item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // When an item is clicked, pass the sender's name or another identifier to the ChatActivity
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Message message = messageList.get(position);
                        Intent intent = new Intent(view.getContext(), ChatActivity.class);
                        intent.putExtra("USER_NAME", message.getSender());
                        view.getContext().startActivity(intent);
                    }
                }
            });
        }

        void bind(Message message) {
            senderTextView.setText(message.getSender());
            timestampTextView.setText(message.getTimestamp());
            messageBodyTextView.setText(message.getBody());
        }
    }
}


// Placeholder Message class
class Message {
    private String sender;
    private String timestamp;
    private String body;

    public Message(String sender, String timestamp, String body) {
        this.sender = sender;
        this.timestamp = timestamp;
        this.body = body;
    }

    public String getSender() { return sender; }
    public String getTimestamp() { return timestamp; }
    public String getBody() { return body; }
}
