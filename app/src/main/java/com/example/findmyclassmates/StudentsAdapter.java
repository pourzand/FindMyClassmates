package com.example.findmyclassmates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        // Conditionally show or hide the sendMessageButton based on the username
        if (studentName.equals(currentUserUsername)) {
            holder.sendMessageButton.setVisibility(View.GONE); // Hide the button for the current user
        } else {
            holder.sendMessageButton.setVisibility(View.VISIBLE); // Show the button for other students
        }

        // Set an OnClickListener for the sendMessageButton
        holder.sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the action to send a message to the student
                // You can launch a messaging activity or any other action here
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
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