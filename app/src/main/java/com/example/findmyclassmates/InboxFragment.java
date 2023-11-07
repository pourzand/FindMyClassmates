package com.example.findmyclassmates;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class InboxFragment extends Fragment {

    private RecyclerView messagesRecyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView);
        messageList = createPlaceholderMessages();
        messageAdapter = new MessageAdapter(messageList);
        messagesRecyclerView.setAdapter(messageAdapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fabCompose = view.findViewById(R.id.fabCompose);
        fabCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openComposeMessageFragment();
            }
        });

        return view;
    }

    private List<Message> createPlaceholderMessages() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("Alice", "Bob", "Hi User, how are you?", "10:42 AM"));
        messages.add(new Message("Bob", "Alice", "Hey User, I'm in the same class as you!", "10:45 AM"));
        return messages;
    }

    private void openComposeMessageFragment() {
        // Navigate to the ComposeMessageFragment
        // Here's a simple transaction, replace with your navigation logic if you use something like NavController
        Fragment composeFragment = new ComposeMessageFragment(); // Assuming you have this fragment created
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, composeFragment) // R.id.container should be the ID of the container in your layout
                .addToBackStack(null) // Add this transaction to the back stack
                .commit();
    }
}
