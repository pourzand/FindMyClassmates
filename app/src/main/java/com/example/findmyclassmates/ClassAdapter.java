package com.example.findmyclassmates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// com.example.findmyclassmates.ClassAdapter.java
public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {
    private List<ClassModel> classList;

    public ClassAdapter(List<ClassModel> classList) {
        this.classList = classList;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassModel classModel = classList.get(position);
        holder.bind(classModel);
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        private TextView classNameTextView;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            classNameTextView = itemView.findViewById(R.id.classNameTextView);
        }

        public void bind(ClassModel classModel) {
            classNameTextView.setText(classModel.getClassName());
        }
    }
}
