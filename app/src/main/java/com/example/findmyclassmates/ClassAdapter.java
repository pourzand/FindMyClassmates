package com.example.findmyclassmates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// Assuming ClassModel is a model class you have in your project that contains the class details.
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

    // Call this method when you need to update the list of classes in the adapter.
    public void updateClassList(List<ClassModel> newClassList) {
        this.classList = newClassList;
        notifyDataSetChanged();
    }

    // Static inner ViewHolder class.
    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        private TextView classNameTextView;
        // You may also want to add other TextViews or views for additional class details.

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            classNameTextView = itemView.findViewById(R.id.classNameTextView);
            // Initialize other views here.
        }

        public void bind(ClassModel classModel) {
            classNameTextView.setText(classModel.getClassName());
            // Bind other class details to their respective views here.
        }
    }
}
