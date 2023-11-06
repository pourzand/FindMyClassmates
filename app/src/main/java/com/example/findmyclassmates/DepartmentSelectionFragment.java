package com.example.findmyclassmates;

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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DepartmentSelectionFragment extends Fragment {

    private Spinner departmentSpinner;
    private Button nextButton;

    private List<ClassModel> classList;
    private ClassesFragment classesFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_department_selection, container, false);

        departmentSpinner = view.findViewById(R.id.departmentSpinner);
        nextButton = view.findViewById(R.id.nextButton);

        // Retrieve the classList from previous fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            classList = bundle.getParcelableArrayList("classList");
        } else {
            Log.e("DepartmentSelectionFrag", "Bundle is null");
        }

        if (classList != null) {
            Log.d("DepartmentSelectionFrag", "classList size: " + classList.size());
        } else {
            Log.e("DepartmentSelectionFrag", "classList is null");
        }

        // Initialize classesFragment
        classesFragment = new ClassesFragment();

        // Extract unique departments from classList
        Set<String> uniqueDepartments = new HashSet<>();
        for (ClassModel classModel : classList) {
            uniqueDepartments.add(classModel.getDepartment());
        }

        // Convert set to list for Spinner adapter
        List<String> departmentList = new ArrayList<>(uniqueDepartments);

        // Set up ArrayAdapter for Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, departmentList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(adapter);

        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDepartment = departmentList.get(position);
                classesFragment.filterClassesByDepartment(selectedDepartment);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle when nothing is selected (if needed)
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass selected department and classList to the next step (ClassesFragment)
                openClassesFragment();
            }
        });

        return view;
    }

    private void openClassesFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("classList", new ArrayList<>(classList));
        classesFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, classesFragment)
                .addToBackStack(null)
                .commit();
    }
}
