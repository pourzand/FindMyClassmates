package com.example.findmyclassmates;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EnrolledClassesAdapterTest {

    private List<ClassData> classDataList;
    private EnrolledClassesAdapter adapter;
    private ClassData classData;
    private String expectedClassName;
    private List<String> expectedStudents;

    @Before
    public void setUp() {
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
}

