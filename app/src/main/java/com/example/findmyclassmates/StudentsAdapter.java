package com.example.findmyclassmates;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
                    composeMessage(v, studentName);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    private void composeMessage(View view, String recipient) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Compose Message to " + recipient);

        final EditText inputMessage = new EditText(view.getContext());
        inputMessage.setHint("Your message");

        LinearLayout layout = new LinearLayout(view.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputMessage);

        builder.setView(layout);

        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
