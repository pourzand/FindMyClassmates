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
import java.util.List;

public class ClassesFragment extends Fragment {

    private List<ClassModel> classList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classes, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.classesRecyclerView);

        // Initialize RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Parse CSV data and populate classList
        parseCSVData();

        DepartmentSelectionFragment departmentSelectionFragment = new DepartmentSelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("classList", new ArrayList<>(classList));
        departmentSelectionFragment.setArguments(bundle);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, departmentSelectionFragment)
                .commit();
    }

    public void filterClassesByDepartment(String department) {
        List<ClassModel> filteredList = new ArrayList<>();
        for (ClassModel classModel : classList) {
            if (classModel.getDepartment().equals(department)) {
                filteredList.add(classModel);
            }
        }

        // Update RecyclerView with the filtered list
        ClassAdapter adapter = new ClassAdapter(filteredList);
        recyclerView.setAdapter(adapter);
    }

    private void parseCSVData() {
        try {
            InputStream inputStream = getResources().getAssets().open("Course CSV - Sheet1.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String[] nextLine;
            while ((nextLine = customReadNext(reader)) != null) {
                if (nextLine.length >= 9) {
                    String department = nextLine[0];
                    String className = nextLine[1];
                    String description = nextLine[2];
                    String units = nextLine[3];
                    String time = nextLine[4];
                    String days = nextLine[5];
                    String instructor = nextLine[6];
                    String location = nextLine[7];
                    String classId = nextLine[8];

                    ClassModel classModel = new ClassModel(department, className, description, units, time, days, instructor, location, classId);
                    classList.add(classModel);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] customReadNext(BufferedReader reader) {
        try {
            String line = reader.readLine();
            if (line != null) {
                return line.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
