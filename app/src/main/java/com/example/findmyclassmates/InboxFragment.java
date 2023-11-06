package com.example.findmyclassmates;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class InboxFragment extends Fragment {

    private RecyclerView messagesRecyclerView;
    private MessageListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize your adapter with empty data (or you can directly pass the placeholder data)
        adapter = new MessageListAdapter(new ArrayList<>());

        // Update the adapter's data
//        adapter.setMessageList(Arrays.asList(
//                new Message("Alice", "10:42 AM", "Hey! How are you?"),
//                new Message("Bob", "Yesterday", "Let's meet tomorrow."),
//                new Message("Bob", "Yesterday", "Let's meet tomorrow."),
//                new Message("Bob", "Yesterday", "Let's meet tomorrow."),
//                new Message("Bob", "Yesterday", "Let's meet tomorrow."),
//                new Message("Bob", "Yesterday", "Let's meet tomorrow."),
//                new Message("Bob", "Yesterday", "Let's meet tomorrow."),
//                new Message("Bob", "Yesterday", "Let's meet tomorrow."),
//                new Message("Bob", "Yesterday", "Let's meet tomorrow.")
//                // ... add more placeholder messages
//        ));

        messagesRecyclerView.setAdapter(adapter);

        // Set up the FloatingActionButton for composing messages
        FloatingActionButton fabCompose = view.findViewById(R.id.fabCompose);
        fabCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fill in
            }
        });

        return view;
    }
}
