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

        // Initialize RecyclerView for departments
        departmentRecyclerView = view.findViewById(R.id.departmentRecyclerView);
        departmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        departmentAdapter = new DepartmentAdapter(new ArrayList<>(), this::onDepartmentSelected);
        departmentRecyclerView.setAdapter(departmentAdapter);

        // Initialize RecyclerView for classes
        classesRecyclerView = view.findViewById(R.id.classesRecyclerView);
        classesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Pass the context along with an empty list of ClassModel
        classAdapter = new ClassAdapter(getContext(), new ArrayList<ClassModel>());
        classesRecyclerView.setAdapter(classAdapter);

        // Parse CSV data and update the UI
        parseCSVData();
    }

    private void onDepartmentSelected(String department) {
        // Filter classes by selected department
        List<ClassModel> filteredList = new ArrayList<>();
        for (ClassModel classModel : classList) {
            if (classModel.getDepartment().equals(department)) {
                filteredList.add(classModel);
            }
        }
        classAdapter.updateClassList(filteredList);
        departmentRecyclerView.setVisibility(View.GONE);
        classesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void parseCSVData() {
        new Thread(() -> {
            try {
                InputStream inputStream = getResources().getAssets().open("Course CSV - Sheet1.csv");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

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
                        departmentSet.add(nextLine[0]);
                    }
                }
                reader.close();

                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        departmentAdapter.updateDepartmentList(new ArrayList<>(departmentSet));
                        departmentRecyclerView.setVisibility(View.VISIBLE);
                        classesRecyclerView.setVisibility(View.GONE);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Rest of your code for adapters and any additional functions...
}
