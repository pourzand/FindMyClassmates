package com.example.findmyclassmates;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RatingFormTest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        activityRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new RatingFormFragment("your_class_id"))
                .commit();

        // Wait for the fragment to be fully loaded (you might need to customize the delay)
        try {
            Thread.sleep(1000); // Adjust the delay based on your app's loading time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        // Perform any necessary clean-up after each test
    }

    // Test for Empty Fields
    @Test
    public void testEmptyFields() {
        setUp();
        onView(withId(R.id.submitButton)).perform(ViewActions.click());

        // Check if the appropriate error message is displayed for prompt1EditText
        onView(withId(R.id.prompt1EditText)).check(matches(hasErrorText("Fields 1-4 cannot be empty")));
    }
}
