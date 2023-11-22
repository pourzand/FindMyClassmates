package com.example.findmyclassmates;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

public class EnrolledClassesAdapterTest {

    private List<ClassData> classDataList;
    private EnrolledClassesAdapter adapter;
    private ClassData classData;
    private String expectedClassName;
    private List<String> expectedStudents;

    @Mock
    private TextView mockTextView;
    @Mock
    private RecyclerView mockRecyclerView;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Set up for ClassData
        expectedClassName = "Test Class";
        expectedStudents = Arrays.asList("Alice", "Bob", "Charlie");
        classData = new ClassData(expectedClassName, expectedStudents);

        // Set up for EnrolledClassesAdapter
        classDataList = new ArrayList<>();
        classDataList.add(classData);  // using the same ClassData object
        classDataList.add(new ClassData("Class 2", Arrays.asList("Student 3", "Student 4")));
        adapter = new EnrolledClassesAdapter(classDataList);
    }

    // Tests for ClassData
    @Test
    public void classData_gettersReturnCorrectValues() {
        assertEquals(expectedClassName, classData.getClassName());
        assertEquals(expectedStudents, classData.getStudents());
    }

    // Tests for EnrolledClassesAdapter
    @Test
    public void adapter_initializationTest() {
        assertEquals(classDataList, adapter.enrolledClasses);
    }

    @Test
    public void adapter_itemCount_returnsCorrectCount() {
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void onBindViewHolder_CalledMultipleTimes_VerifyMethodCalls() {
        EnrolledClassesAdapter.ClassViewHolder mockViewHolder = mock(EnrolledClassesAdapter.ClassViewHolder.class);
        mockViewHolder.classTextView = mockTextView;
        mockViewHolder.studentsRecyclerView = mockRecyclerView;

        // Simulate onBindViewHolder being called 3 times
        adapter.onBindViewHolder(mockViewHolder, 0);
        adapter.onBindViewHolder(mockViewHolder, 0);
        adapter.onBindViewHolder(mockViewHolder, 0);

        // Verify that setText is called exactly 3 times
        verify(mockTextView, times(3)).setText(expectedClassName);

        // Verify that setAdapter and setLayoutManager are called exactly 3 times each
        verify(mockRecyclerView, times(3)).setAdapter(any(StudentsAdapter.class));
        verify(mockRecyclerView, times(3)).setLayoutManager(any(LinearLayoutManager.class));
    }
}