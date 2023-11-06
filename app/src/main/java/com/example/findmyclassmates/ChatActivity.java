package com.example.findmyclassmates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter; // Keep a reference to the adapter
    private EditText editTextChatBox;
    private Button buttonChatSend;
    private List<Message> chatMessages = new ArrayList<>(); // Initialize the list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        editTextChatBox = findViewById(R.id.editTextChatBox);
        buttonChatSend = findViewById(R.id.buttonChatSend);

        // Initialize your adapter with the chatMessages list
        chatAdapter = new ChatAdapter(chatMessages);
        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonChatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = editTextChatBox.getText().toString();
                if (!messageText.isEmpty()) {
                    // Create a new Message object and add it to your list
                    String currentUser = "YourName";
                    String timeStamp = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                    Message newMessage = new Message(currentUser, timeStamp, messageText);
                    chatMessages.add(newMessage);

                    // Notify the adapter that an item has been inserted
                    chatAdapter.notifyItemInserted(chatMessages.size() - 1);

                    // Scroll to the bottom to show latest message
                    chatRecyclerView.scrollToPosition(chatMessages.size() - 1);

                    // Clear the input field and close the keyboard
                    editTextChatBox.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextChatBox.getWindowToken(), 0);
                }
            }
        });
    }
}
