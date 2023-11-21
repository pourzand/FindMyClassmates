package com.example.findmyclassmates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentAdapterUnitTest {

    @Mock
    private DepartmentAdapter.OnDepartmentClickListener mockListener;
    @Mock
    private ViewGroup mockParent;
    @Mock
    private TextView mockTextView;

    private DepartmentAdapter adapter;
    private List<String> departments = Arrays.asList("Mathematics", "Physics", "Chemistry");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        adapter = new DepartmentAdapter(departments, mockListener);
    }

    @Test
    public void testOnDepartmentClick() {
        // Mock the LayoutInflater and View to avoid using ApplicationProvider
        View mockView = Mockito.mock(View.class);
        Mockito.when(mockView.findViewById(R.id.departmentTextView)).thenReturn(mockTextView);
        Mockito.when(mockParent.getContext()).thenReturn(Mockito.mock(android.content.Context.class));
        Mockito.when(LayoutInflater.from(mockParent.getContext()).inflate(R.layout.item_department, mockParent, false)).thenReturn(mockView);

        // Create and bind the ViewHolder
        DepartmentAdapter.DepartmentViewHolder holder = adapter.onCreateViewHolder(mockParent, 0);
        adapter.onBindViewHolder(holder, 0);

        // Simulate item click
        holder.itemView.performClick();

        // Verify if the listener's onDepartmentClick method is called with the correct department name
        verify(mockListener).onDepartmentClick(departments.get(0));
    }

    @Test
    public void testItemCount() {
        assertEquals("Item count should match the number of departments", departments.size(), adapter.getItemCount());
    }

    // Additional test cases...
}
