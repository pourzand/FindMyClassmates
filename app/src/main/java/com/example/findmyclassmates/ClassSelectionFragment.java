package com.example.findmyclassmates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ClassSelectionFragment extends Fragment {

    private List<ClassModel> classList = new ArrayList<>();
    private List<ClassModel> filteredClassList = new ArrayList<>();
    private ClassSelectionAdapter adapter;
    private String selectedDepartment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_selection, container, false);

        // Get the selected department from arguments
        selectedDepartment = getArguments().getString("selectedDepartment");

        // Assuming you have a ListView in your layout with the id listViewClassSelection
        ListView listView = view.findViewById(R.id.listViewClassSelection);

        filterClasses();

        adapter = new ClassSelectionAdapter(getContext(), filteredClassList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassModel selectedClass = filteredClassList.get(position);
                // Handle the selected class (e.g., open a new activity)
            }
        });

        return view;
    }

    private void filterClasses() {
        filteredClassList.clear();
        for (ClassModel classModel : classList) {
            if (classModel.getDepartment().equals(selectedDepartment)) {
                filteredClassList.add(classModel);
            }
        }
    }
}
