package com.example.findmyclassmates;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class InboxFragment extends Fragment {

    private RecyclerView recyclerViewMessages;
    private FloatingActionButton fabCompose;
    private MessageAdapter adapter;
    private List<Message> messages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        recyclerViewMessages = view.findViewById(R.id.recyclerViewMessages);
        fabCompose = view.findViewById(R.id.fabCompose);

        // Initialize your message list
        messages = new ArrayList<>();
        // TODO: Replace this with real data fetching
        loadMessages();

        // Setup RecyclerView
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MessageAdapter(messages);
        recyclerViewMessages.setAdapter(adapter);

        // Set click listener for fabCompose to open a new fragment or activity for composing messages
        fabCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Implement the logic to open compose message screen
                composeMessage();
            }
        });

        return view;
    }

    private void loadMessages() {
        // TODO: Fetch messages from your data source and update the adapter
        // Example:
        messages.add(new Message("User1", "Hello!"));
        messages.add(new Message("User2", "How are you?"));
//        adapter.notifyDataSetChanged();
    }

    private void composeMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Compose Message");

        // Set up the input fields
        final EditText inputRecipient = new EditText(getContext());
        inputRecipient.setHint("Recipient");
        final EditText inputMessage = new EditText(getContext());
        inputMessage.setHint("Your message");

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputRecipient);
        layout.addView(inputMessage);

        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String recipient = inputRecipient.getText().toString();
                String message = inputMessage.getText().toString();
                // TODO: Implement the sending logic here
                sendMessage(recipient, message);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void sendMessage(String recipient, String messageText) {
        // TODO: Implement your message sending logic here
        // This might involve sending data to your backend server or updating a local database
    }


    // Add any additional code here, like methods to fetch messages from your database
}
