package com.example.findmyclassmates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EnrolledClassesAdapter extends RecyclerView.Adapter<EnrolledClassesAdapter.ClassViewHolder> {

    private List<ClassData> enrolledClasses;

    public EnrolledClassesAdapter(List<ClassData> enrolledClasses) {
        this.enrolledClasses = enrolledClasses;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enrolled_class, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassData classData = enrolledClasses.get(position);
        holder.classTextView.setText(classData.getClassName());

        // Create an adapter for the students RecyclerView
        StudentsAdapter studentsAdapter = new StudentsAdapter(classData.getStudents());
        holder.studentsRecyclerView.setAdapter(studentsAdapter);
        holder.studentsRecyclerView.setLayoutManager(new LinearLayoutManager(holder.studentsRecyclerView.getContext()));
    }

    @Override
    public int getItemCount() {
        return enrolledClasses.size();
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        public TextView classTextView;
        public RecyclerView studentsRecyclerView;

        public ClassViewHolder(View itemView) {
            super(itemView);
            classTextView = itemView.findViewById(R.id.classTextView);
            studentsRecyclerView = itemView.findViewById(R.id.studentsRecyclerView);
        }
    }
}
