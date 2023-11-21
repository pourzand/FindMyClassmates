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
        // Simulating each department click
        for (int i = 0; i < departments.size(); i++) {
            // Assuming you have a way to simulate the ViewHolder creation and item click
            // If you need to simulate item clicks, you might need additional setup here

            // Simulate item click
            mockListener.onDepartmentClick(departments.get(i));

            // Verify if the listener's onDepartmentClick method is called with the correct department name
            verify(mockListener, times(1)).onDepartmentClick(departments.get(i));
        }
    }

    @Test
    public void testItemCount() {
        // Assert that getItemCount() returns the correct count
        assertEquals("Item count should match the number of departments", departments.size(), adapter.getItemCount());
    }

    // You can add more test cases here to cover other aspects of your adapter...
}
