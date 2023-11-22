package com.example.findmyclassmates;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InboxFragment extends Fragment {

    RecyclerView recyclerViewMessages;
    private FloatingActionButton fabCompose;
    MessageAdapter adapter;
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

    void loadMessages() {
        String currentUsername = UserSession.getInstance().getUsername();

        if(currentUsername != null) {
            // Assuming your Firebase Realtime Database reference is properly set up
            DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference().child("messages").child(currentUsername);

            // Add a ValueEventListener to fetch data from Firebase
            messagesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    messages.clear(); // Clear the existing list before adding new messages

                    // Iterate through the messages for the current user
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        String sender = messageSnapshot.getKey(); // Get the sender's user ID
                        String messageText = messageSnapshot.getValue(String.class); // Get the message text

                        // Create a Message object and add it to the messages list
                        Message message = new Message(sender, messageText);
                        messages.add(message);
                    }

                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors here
                }
            });
        } else {
            Log.println(Log.INFO, "inboxFrag", "username not provided since in testcase rn");
        }
    }



    private void composeMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Compose Message");

        // Set up the input fields
        final EditText inputRecipient = new EditText(getContext());
        inputRecipient.setHint("Recipient");
        inputRecipient.setTag("inputRecipient"); // Set a tag to identify this view later

        final EditText inputMessage = new EditText(getContext());
        inputMessage.setHint("Your message");
        inputMessage.setTag("inputMessage"); // Set a tag to identify this view later


        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputRecipient);
        layout.addView(inputMessage);

        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String recipient = inputRecipient.getText().toString();
                final String message = inputMessage.getText().toString();

                // Check if the recipient is a valid user in the database
                checkRecipientValidity(recipient, message, inputRecipient, inputMessage);
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

    private void checkRecipientValidity(final String recipient, final String message, EditText inputRecipient, EditText inputMessage) {
        if (recipient.trim().isEmpty()) {
            inputRecipient.setError("Recipient is empty. Try again");

//            Toast.makeText(getContext(), "Recipient is empty. Try again", Toast.LENGTH_SHORT).show();
            return; // Exit the function since the message is empty
        }

        if (message.trim().isEmpty()) {
            inputMessage.setError("Message is empty. Try again");

            return;
        }

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference();

        usersRef.child(recipient).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Recipient is a valid user in the database, proceed to send the message
                    sendMessage(recipient, message);
                } else {
                    inputRecipient.setError("Invalid recipient. User not found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }


    void sendMessage(final String recipient, final String messageText) {
        String currentUsername = UserSession.getInstance().getUsername();

        if (messageText.trim().isEmpty()) {
            // Display a Toast warning using the provided context
            Toast.makeText(getContext(), "Message is empty. Try again", Toast.LENGTH_SHORT).show();
            return; // Exit the function since the message is empty
        }

        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference().child("messages");

        // Update the sender's message, optional*****
        messagesRef.child(currentUsername).child(recipient).setValue(messageText);

        // Update the recipient's message
        messagesRef.child(recipient).child(currentUsername).setValue(messageText);
    }


}
