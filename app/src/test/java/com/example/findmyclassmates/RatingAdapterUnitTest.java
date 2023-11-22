package com.example.findmyclassmates;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.view.ViewGroup;
import android.widget.TextView;

@RunWith(MockitoJUnitRunner.class)
public class RatingAdapterUnitTest {

    private RatingAdapter adapter;
    @Mock
    RatingAdapter.ViewHolder mockViewHolder;


    @Mock
    ViewGroup mockParent; // Mocked for onCreateViewHolder test

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        adapter = new RatingAdapter();


        // Initialize mocks for TextViews in ViewHolder
        mockViewHolder.ratingTextView = Mockito.mock(TextView.class);
        mockViewHolder.usernameTextView = Mockito.mock(TextView.class);
    }

    @Test
    public void testSetRatings() {
        List<RatingData> testRatings = Arrays.asList(/* Sample RatingData instances */);
        adapter.setRatings(testRatings);
        assertEquals(testRatings.size(), adapter.getItemCount());
    }

    @Test
    public void testOnCreateViewHolder() {
        // Note: Requires Robolectric setup for proper context and layout inflater
        RatingAdapter.ViewHolder viewHolder = adapter.onCreateViewHolder(mockParent, 0);
        assertNotNull(viewHolder);
        // Additional assertions can be added here
    }

    @Test
    public void testOnBindViewHolder() {
        List<RatingData> testRatings = Arrays.asList(new RatingData("User1", "5 stars"), new RatingData("User2", "4 stars"));
        adapter.setRatings(testRatings);

        // Simulate onBindViewHolder for the first item
        adapter.onBindViewHolder(mockViewHolder, 0);

        // Verify the text set on the TextViews
        assertEquals("5 stars", mockViewHolder.ratingTextView.getText().toString());
        assertEquals("User: User1", mockViewHolder.usernameTextView.getText().toString());

        // Simulate onBindViewHolder for the second item
        adapter.onBindViewHolder(mockViewHolder, 1);

        // Verify the text set on the TextViews for the second item
        assertEquals("4 stars", mockViewHolder.ratingTextView.getText().toString());
        assertEquals("User: User2", mockViewHolder.usernameTextView.getText().toString());
    }

    @Test
    public void testGetItemCount() {
        assertEquals(0, adapter.getItemCount()); // Assuming no ratings set initially
    }

    // Additional test cases...
}