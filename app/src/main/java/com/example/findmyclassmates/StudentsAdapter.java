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

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {

    private List<String> students;
    private String currentUserUsername; // Store the currently logged-in user's username

    public StudentsAdapter(List<String> students) {
        this.students = students;
        this.currentUserUsername = UserSession.getInstance().getUsername(); //get username
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

        if (studentName.equals(currentUserUsername)) {
            holder.sendMessageButton.setVisibility(View.GONE);
        } else {
            holder.sendMessageButton.setVisibility(View.VISIBLE);
            holder.sendMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    composeMessage(v.getContext(), studentName);
                }
            });
        }
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

        public StudentViewHolder(View itemView) {
            super(itemView);
            studentTextView = itemView.findViewById(R.id.studentTextView);
            sendMessageButton = itemView.findViewById(R.id.sendMessageButton);
        }
    }
}
