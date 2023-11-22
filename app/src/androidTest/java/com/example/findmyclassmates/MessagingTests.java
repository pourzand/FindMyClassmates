package com.example.findmyclassmates;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.widget.EditText;
import android.widget.LinearLayout;

@RunWith(AndroidJUnit4.class)
public class MessagingTests {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        // Navigate to the InboxFragment before running each test
        activityRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new InboxFragment())
                .commit();

        // Wait for the fragment to be fully loaded
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        // Perform any necessary clean-up after each test
    }

    // Test for Empty Recipient and Message
    @Test
    public void testEmptyRecipient() {
        setUp();

        Espresso.onView(ViewMatchers.withId(R.id.fabCompose)).perform(ViewActions.click());

        // Check if the appropriate error messages are displayed
        Espresso.onView(ViewMatchers.withTagValue(Matchers.is("inputRecipient")))
                .check(matches(ViewMatchers.isAssignableFrom(EditText.class))) // Check the individual EditText
                .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(isDisplayed()))
                .check(matches(hasErrorText("Recipient is empty. Try again")));
    }


    // Additional tests can be added for valid input, sending messages, etc.
}
