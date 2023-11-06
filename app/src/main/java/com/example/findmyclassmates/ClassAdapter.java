package com.example.findmyclassmates;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private List<ClassModel> classList;
    private Context context;

    public ClassAdapter(Context context, List<ClassModel> classList) {
        this.context = context;
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
        holder.bind(classList.get(position), context);
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public void updateClassList(List<ClassModel> newClassList) {
        classList = newClassList;
        notifyDataSetChanged();
    }

    static class ClassViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.classNameTextView);
        }

        public void bind(final ClassModel classModel, Context context) {
            nameTextView.setText(classModel.getClassName());
            itemView.setOnClickListener(view -> {
                // Check if the context is an instance of FragmentActivity to safely cast it
                if (context instanceof FragmentActivity) {
                    FragmentActivity activity = (FragmentActivity) context;

                    ClassDetailsFragment classDetailsFragment = new ClassDetailsFragment();
                    Bundle args = new Bundle();
                    args.putParcelable("selectedClass", classModel);
                    classDetailsFragment.setArguments(args);

                    // Replace the current fragment with the detail fragment
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, classDetailsFragment) // Use the actual container ID here
                            .addToBackStack(null)  // Add transaction to the back stack if you want to navigate back
                            .commit();
                }
            });
        }

    }
}
