package com.example.findmyclassmates;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentAdapterUnitTest {

    @Mock
    private DepartmentAdapter.OnDepartmentClickListener mockListener;

    private DepartmentAdapter adapter;
    private List<String> departments = Arrays.asList("Mathematics", "Physics", "Chemistry");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        adapter = new DepartmentAdapter(departments, mockListener);
    }

    @Test
    public void testOnDepartmentClick() {
        for (int i = 0; i < departments.size(); i++) {
            mockListener.onDepartmentClick(departments.get(i));
            verify(mockListener, times(1)).onDepartmentClick(departments.get(i));
        }
    }

    @Test
    public void testOnDepartmentClickAfterUpdate() {
        List<String> newDepartments = Arrays.asList("Biology", "Computer Science");
        adapter.updateDepartmentList(newDepartments);

        for (int i = 0; i < newDepartments.size(); i++) {
            mockListener.onDepartmentClick(newDepartments.get(i));
            verify(mockListener, times(1)).onDepartmentClick(newDepartments.get(i));
        }
    }

    @Test
    public void testOnDepartmentClickForNonExistentDepartment() {
        // Verify that the listener's onDepartmentClick method is never called with "NonExistentDepartment"
        verify(mockListener, times(0)).onDepartmentClick("NonExistentDepartment");
    }

    @Test
    public void testItemCount() {
        assertEquals("Item count should match the number of departments", departments.size(), adapter.getItemCount());
    }

    // Additional test cases as needed...
}