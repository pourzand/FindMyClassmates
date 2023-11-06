package com.example.findmyclassmates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder> {

    private List<String> departments; // Made non-final to allow updates
    private final OnDepartmentClickListener listener;

    public interface OnDepartmentClickListener {
        void onDepartmentClick(String department);
    }

    public DepartmentAdapter(List<String> departments, OnDepartmentClickListener listener) {
        this.departments = departments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department, parent, false);
        return new DepartmentViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentViewHolder holder, int position) {
        holder.bind(departments.get(position));
    }

    @Override
    public int getItemCount() {
        return departments.size();
    }

    // Update the list of departments
    public void updateDepartmentList(List<String> newDepartments) {
        departments = newDepartments;
        notifyDataSetChanged(); // Notify the adapter of the data change
    }

    public static class DepartmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView departmentTextView;
        private final OnDepartmentClickListener listener;

        public DepartmentViewHolder(@NonNull View itemView, OnDepartmentClickListener listener) {
            super(itemView);
            departmentTextView = itemView.findViewById(R.id.departmentTextView);
            this.listener = listener;
        }

        public void bind(final String departmentName) {
            departmentTextView.setText(departmentName);
            itemView.setOnClickListener(view -> listener.onDepartmentClick(departmentName));
        }
    }
}
