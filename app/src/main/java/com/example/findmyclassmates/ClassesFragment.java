package com.example.findmyclassmates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassesFragment extends Fragment {

    private List<ClassModel> classList = new ArrayList<>();
    private Set<String> departmentSet = new HashSet<>();
    private RecyclerView departmentRecyclerView;
    private RecyclerView classesRecyclerView;
    private DepartmentAdapter departmentAdapter;
    private ClassAdapter classAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up RecyclerView for departments
        departmentRecyclerView = view.findViewById(R.id.departmentRecyclerView); // Replace with your actual ID
        departmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        departmentAdapter = new DepartmentAdapter(new ArrayList<>(), this::onDepartmentSelected);
        departmentRecyclerView.setAdapter(departmentAdapter);

        // Set up RecyclerView for classes, initially invisible or gone
        classesRecyclerView = view.findViewById(R.id.classesRecyclerView);
        classesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        classAdapter = new ClassAdapter(new ArrayList<>());
        classesRecyclerView.setAdapter(classAdapter);

        // Parse CSV data and fill departments
        new Thread(this::parseCSVData).start();
    }

    private void onDepartmentSelected(String department) {
        // Filter classes by selected department
        filterClassesByDepartment(department);
        // Show the classes RecyclerView and hide the departments RecyclerView
        departmentRecyclerView.setVisibility(View.GONE);
        classesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void filterClassesByDepartment(String department) {
        List<ClassModel> filteredList = new ArrayList<>();
        for (ClassModel classModel : classList) {
            if (classModel.getDepartment().equals(department)) {
                filteredList.add(classModel);
            }
        }
        classAdapter.updateClassList(filteredList);
    }

    private void parseCSVData() {
        try {
            InputStream inputStream = getResources().getAssets().open("Course CSV - Sheet1.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] nextLine = line.split(",");
                if (nextLine.length >= 9) {
                    ClassModel classModel = new ClassModel(
                            nextLine[0],
                            nextLine[1],
                            nextLine[2],
                            nextLine[3],
                            nextLine[4],
                            nextLine[5],
                            nextLine[6],
                            nextLine[7],
                            nextLine[8]
                    );
                    classList.add(classModel);
                    departmentSet.add(nextLine[0]); // Assuming the first entry is the department
                }
            }

            reader.close();

            // Update the RecyclerViews on the main thread
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    departmentAdapter.updateDepartmentList(new ArrayList<>(departmentSet));
                    // Initially, we display only the departments
                    departmentRecyclerView.setVisibility(View.VISIBLE);
                    classesRecyclerView.setVisibility(View.GONE);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // The rest of your ClassesFragment code remains unchanged.
    // You would need to create a new DepartmentAdapter similar to ClassAdapter to display the departments.
    // And in your layout, you should have two RecyclerViews: one for departments and one for classes.
}
