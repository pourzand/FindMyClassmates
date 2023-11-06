package com.example.findmyclassmates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
        Button buttonEnrollToggle = view.findViewById(R.id.buttonEnrollToggle);

        // Set the TextViews to display the class details
        textViewClassID.setText(selectedClass.getClassID() + " -"); // Added dash for separation
        textViewClassName.setText(selectedClass.getClassName());
        textViewUnits.setText("Units: " + selectedClass.getUnits());
        textViewProfessor.setText("Professor: " + selectedClass.getProfessor());
        textViewDescription.setText("Description: " + selectedClass.getDescription());

        // Initially, we assume the user is not enrolled
        buttonEnrollToggle.setText("Enroll");

        // Handle click event for Enroll/Unenroll button
        buttonEnrollToggle.setOnClickListener(new View.OnClickListener() {
            boolean isEnrolled = false; // Initial state

            @Override
            public void onClick(View v) {
                // Toggle the enrollment state
                isEnrolled = !isEnrolled;

                // Update the button text based on the state
                buttonEnrollToggle.setText(isEnrolled ? "Unenroll" : "Enroll");

                // You can also change the button appearance here if needed
                buttonEnrollToggle.setBackgroundColor(getResources().getColor(isEnrolled ? android.R.color.holo_red_dark : android.R.color.holo_green_light, null));
            }
        });

        ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle what happens when the back button is clicked
                // Typically, we just pop the back stack
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        Button buttonRateClass = view.findViewById(R.id.buttonRateClass);
        buttonRateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of RateClassFragment
                RateClassFragment rateClassFragment = new RateClassFragment();

                // If you need to pass data to the RateClassFragment, you can set arguments like this:
                Bundle args = new Bundle();
                args.putParcelable("selectedClass", selectedClass); // Assuming RateClassFragment needs details of the selected class
                rateClassFragment.setArguments(args);

                // Replace the current fragment with the RateClassFragment
                if (getFragmentManager() != null) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(((ViewGroup)getView().getParent()).getId(), rateClassFragment)
                            .addToBackStack(null) // This adds the transaction to the back stack, enabling navigation back
                            .commit();
                }
            }
        });

        return view;
    }
}
