package com.example.findmyclassmates;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FindClassmatesFragment extends Fragment {

    private RecyclerView recyclerView;
    private EnrolledClassesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_classmates, container, false);

        recyclerView = view.findViewById(R.id.classListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetch the list of enrolled classes and their students from Firebase
        String currentUsername = UserSession.getInstance().getUsername();
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("profiles")
                .child(currentUsername)
                .child("classes");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ClassData> enrolledClasses = new ArrayList<>();
                for (DataSnapshot classSnapshot : dataSnapshot.getChildren()) {
                    String className = classSnapshot.getKey();
                    DatabaseReference rosterReference = FirebaseDatabase.getInstance().getReference("classes")
                            .child(className)
                            .child("roster");

                    rosterReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot rosterSnapshot) {
                            List<String> students = new ArrayList<>();
                            for (DataSnapshot studentSnapshot : rosterSnapshot.getChildren()) {
                                students.add(studentSnapshot.getKey());
                            }

                            enrolledClasses.add(new ClassData(className, students));
                            adapter = new EnrolledClassesAdapter(enrolledClasses);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle the error, if any
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any
            }
        });

        return view;
    }
}