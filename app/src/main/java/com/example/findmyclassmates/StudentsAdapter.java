package com.example.findmyclassmates;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {

    private List<String> students;
    private String currentUserUsername;
    private Map<String, Boolean> blockedUsers; // Map to store block status of each user

    public StudentsAdapter(List<String> students) {
        this.students = students;
        this.currentUserUsername = UserSession.getInstance().getUsername();
        this.blockedUsers = new HashMap<>(); // Initialize the map
        // Initialize blockedUsers with false (not blocked) for each student
        for (String student : students) {
            blockedUsers.put(student, false);
        }
    }


    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        String studentName = students.get(position);
        holder.studentTextView.setText(studentName);

        // Update the visibility and text of the block button based on the blocked status
        if (studentName.equals(currentUserUsername)) {
            holder.sendMessageButton.setVisibility(View.GONE);
            holder.blockButton.setVisibility(View.GONE);
        } else {
            holder.sendMessageButton.setVisibility(View.VISIBLE);
            holder.sendMessageButton.setOnClickListener(v -> composeMessage(v.getContext(), studentName));

            holder.blockButton.setVisibility(View.VISIBLE);
            if (blockedUsers.get(studentName)) {
                holder.blockButton.setText("Unblock");
            } else {
                holder.blockButton.setText("Block");
            }

            holder.blockButton.setOnClickListener(v -> {
                boolean isBlocked = !blockedUsers.get(studentName); // Toggle the block status
                blockedUsers.put(studentName, isBlocked); // Update the map
                notifyItemChanged(position); // Notify to update the UI
                if (isBlocked) {
                    blockUser(studentName, v.getContext());
                } else {
                    unblockUser(studentName, v.getContext());
                }
            });
        }
    }

    private void blockUser(String studentName, Context context) {
        // Implement logic to block the user
        Toast.makeText(context, "Blocked " + studentName, Toast.LENGTH_SHORT).show();
    }

    private void unblockUser(String studentName, Context context) {
        // Implement logic to unblock the user
        Toast.makeText(context, "Unblocked " + studentName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    // Add Context as a parameter to composeMessage method
    private void composeMessage(Context context, String recipient) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Compose Message to " + recipient);

        final EditText inputMessage = new EditText(context);
        inputMessage.setHint("Your message");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputMessage);

        builder.setView(layout);

        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String message = inputMessage.getText().toString();
                // TODO: Implement the sending logic here
                sendMessage(recipient, message, context);
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

    // Update sendMessage method to accept Context parameter
    private void sendMessage(String recipient, String messageText, Context context) {
        String currentUsername = UserSession.getInstance().getUsername();

        // Check if the message is empty
        if (messageText.trim().isEmpty()) {
            // Display a Toast warning using the provided context
            Toast.makeText(context, "Message is empty. Try again", Toast.LENGTH_SHORT).show();
            return; // Exit the function since the message is empty
        }

        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference().child("messages");

        // Update the sender's message, optional*****
        messagesRef.child(currentUsername).child(recipient).setValue(messageText);

        // Update the recipient's message
        messagesRef.child(recipient).child(currentUsername).setValue(messageText);
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView studentTextView;
        public Button sendMessageButton;
        public Button blockButton; // New block button

        public StudentViewHolder(View itemView) {
            super(itemView);
            studentTextView = itemView.findViewById(R.id.studentTextView);
            sendMessageButton = itemView.findViewById(R.id.sendMessageButton);
            blockButton = itemView.findViewById(R.id.blockButton); // Initialize the block button
        }
    }
}