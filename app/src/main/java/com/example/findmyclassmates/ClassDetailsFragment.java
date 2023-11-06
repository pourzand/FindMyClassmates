package com.example.findmyclassmates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ClassDetailsFragment extends Fragment {

    private ClassModel selectedClass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the selected class from the arguments
        Bundle args = getArguments();
        if (args != null) {
            selectedClass = args.getParcelable("selectedClass");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_details, container, false);

        // Initialize your TextViews
        TextView textViewClassID = view.findViewById(R.id.textViewClassID);
        TextView textViewClassName = view.findViewById(R.id.textViewClassName);
        TextView textViewUnits = view.findViewById(R.id.textViewUnits);
        TextView textViewProfessor = view.findViewById(R.id.textViewProfessor);
        TextView textViewDescription = view.findViewById(R.id.textViewDescription);
        // ... add other TextViews for each piece of class information you want to display

        // Set the TextViews to display the class details
        textViewClassID.setText(selectedClass.getClassID());
        textViewClassName.setText(selectedClass.getClassName());
        textViewUnits.setText(selectedClass.getUnits());
        textViewProfessor.setText(selectedClass.getProfessor());
        textViewDescription.setText(selectedClass.getDescription());
        // ... set other TextViews with data from the selectedClass

        return view;
    }
}
