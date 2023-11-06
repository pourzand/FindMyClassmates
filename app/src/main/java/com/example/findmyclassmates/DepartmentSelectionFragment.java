package com.example.findmyclassmates;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DepartmentSelectionFragment extends Fragment {

    private Spinner departmentSpinner;
    private Button nextButton;
    private List<ClassModel> classList;
    private OnDepartmentSelectedListener departmentSelectedListener;

    // Define an interface for callback
    public interface OnDepartmentSelectedListener {
        void onDepartmentSelected(String department);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnDepartmentSelectedListener) {
            departmentSelectedListener = (OnDepartmentSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDepartmentSelectedListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_department_selection, container, false);

        departmentSpinner = view.findViewById(R.id.departmentSpinner);
        nextButton = view.findViewById(R.id.nextButton);

        // Retrieve the classList from the previous fragment or the activity
        Bundle bundle = getArguments();
        if (bundle != null) {
            classList = bundle.getParcelableArrayList("classList");
        } else {
            Log.e("DepartmentSelectionFrag", "Bundle is null or doesn't contain classList");
        }

        // Extract unique departments from classList
        Set<String> uniqueDepartments = new HashSet<>();
        for (ClassModel classModel : classList) {
            uniqueDepartments.add(classModel.getDepartment());
        }

        // Convert set to list for Spinner adapter
        List<String> departmentList = new ArrayList<>(uniqueDepartments);

        // Set up ArrayAdapter for Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, departmentList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(adapter);

        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDepartment = departmentList.get(position);
                departmentSelectedListener.onDepartmentSelected(selectedDepartment);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle the case where nothing is selected
            }
        });

        nextButton.setOnClickListener(v -> {
            // This may need to call onDepartmentSelected based on the current spinner selection
            // Or it could navigate to the next Fragment/Activity as per the app's flow
            String selectedDepartment = (String) departmentSpinner.getSelectedItem();
            departmentSelectedListener.onDepartmentSelected(selectedDepartment);
        });

        return view;
    }

    // Make sure to detach the listener when the fragment is detached from its activity
    @Override
    public void onDetach() {
        super.onDetach();
        departmentSelectedListener = null;
    }
}
