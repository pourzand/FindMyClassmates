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


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClassDetailsFragment extends Fragment {

    private ClassModel selectedClass;
    DatabaseReference dbReference;
    FirebaseDatabase fbRoot;

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

        // work to check if the user is enrolled.
        String currentUsername = UserSession.getInstance().getUsername(); //get username
        final boolean[] currentlyEnrolled = {false};
        fbRoot = FirebaseDatabase.getInstance();
        String currentClassID = selectedClass.getClassID();
        dbReference = fbRoot.getReference("classes").child(currentClassID).child("roster");
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(currentUsername)) {
                    // The user is enrolled in this class
                    currentlyEnrolled[0] = true;
                    buttonEnrollToggle.setText("Unenroll");
                } else {
                    // The user is not enrolled in this class
                    currentlyEnrolled[0] = false;
                    // If the user is not enrolled, we set the text to Enroll
                    // we must check the firebase to see if the username is a part of the roster for the class we are looking at
                    buttonEnrollToggle.setText("Enroll");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error, if any
            }
        });

        DatabaseReference currUserClassReference = fbRoot.getReference("profiles").child(currentUsername).child("classes");


        // Handle click event for Enroll/Unenroll button
        buttonEnrollToggle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (currentlyEnrolled[0]) {
                    // User is currently enrolled, the clicked again meaning they want to unenroll

                    // Unenroll the user from the class
                    dbReference.child(currentUsername).removeValue(); // Remove from class roster
                    currUserClassReference.child(currentClassID).removeValue(); // Remove from user classes

                } else {
                    // User is not enrolled, click to enroll

                    // Enroll the user in the class
                    dbReference.child(currentUsername).setValue(true); // add to class roster
                    currUserClassReference.child(currentClassID).setValue(true); // add to user classes

                }
                // Toggle the enrollment state
                currentlyEnrolled[0] = !currentlyEnrolled[0];

                // Update the button text based on the state
                buttonEnrollToggle.setText(currentlyEnrolled[0] ? "Unenroll" : "Enroll");

                // You can also change the button appearance here if needed
                buttonEnrollToggle.setBackgroundColor(getResources().getColor(currentlyEnrolled[0] ? android.R.color.holo_red_dark : android.R.color.holo_green_light, null));
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

        return view;
    }
}
