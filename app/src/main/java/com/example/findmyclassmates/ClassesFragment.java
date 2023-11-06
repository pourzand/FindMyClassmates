package com.example.findmyclassmates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

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

        // Handle the back button click
        ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

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
            InputStream inputStream = null;
            CSVReader csvReader = null;
            try {
                inputStream = getResources().getAssets().open("Course CSV - Sheet1.csv");
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                csvReader = new CSVReaderBuilder(inputStreamReader)
                        .withSkipLines(1) // Skip the first line which is the header
                        .build();

                String[] nextLine;
                while ((nextLine = csvReader.readNext()) != null) {
                    if (nextLine.length >= 9) { // Assumes you have at least 9 columns of data
                        ClassModel classModel = new ClassModel(
                                nextLine[0], // Department
                                nextLine[1], // etc.
                                nextLine[2],
                                nextLine[3],
                                nextLine[4],
                                nextLine[5],
                                nextLine[6],
                                nextLine[7],
                                nextLine[8]
                        );
                        classList.add(classModel);
                        departmentSet.add(nextLine[0]); // Assuming the department is in the first column
                    }
                }

                // This code will run on the UI thread
                if (isAdded()) { // Check if fragment is currently added to its activity
                    getActivity().runOnUiThread(() -> {
                        departmentAdapter.updateDepartmentList(new ArrayList<>(departmentSet));
                        departmentRecyclerView.setVisibility(View.VISIBLE);
                        classesRecyclerView.setVisibility(View.GONE);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (csvReader != null) {
                        csvReader.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    // Rest of your code for adapters and any additional functions...
}
