package com.example.findmyclassmates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_selection, container, false);

        // Get the selected department from arguments
        Bundle args = getArguments();
        if (args != null) {
            selectedDepartment = args.getString("selectedDepartment");
        }

        // Assuming you have a ListView in your layout with the id listViewClassSelection
        ListView listView = view.findViewById(R.id.listViewClassSelection);

        filterClasses();

        adapter = new ClassSelectionAdapter(getContext(), filteredClassList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassModel selectedClass = filteredClassList.get(position);

                // Create a new Fragment instance
                ClassDetailsFragment classDetailsFragment = new ClassDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("selectedClass", selectedClass); // Make sure your ClassModel is Parcelable
                classDetailsFragment.setArguments(bundle);

                // Replace the current fragment with the detail fragment
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), classDetailsFragment)
                        .addToBackStack(null) // Add transaction to the back stack if you want to navigate back
                        .commit();
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
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
