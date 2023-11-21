package com.example.findmyclassmates;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class MessagingTests {

    // Using MainActivity which hosts your InboxFragment
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

//    @Test
//    public void testMessageDisplayFunctionality() {
//        // Add logic here to simulate sending messages
//        // Check if the RecyclerView is displaying the messages
//        onView(withId(R.id.recyclerViewMessages))
//                .check(matches(hasDescendant(withId(R.id.senderTextView))))
//                .check(matches(hasDescendant(withId(R.id.contentTextView))));
//    }

//    @Test
//    public void testMessageSendingFunctionality() {
//        onView(withId(R.id.fabCompose)).perform(click());
//        // Assuming that the EditTexts for recipient and message have unique IDs assigned
//        onView(withHint("Recipient")) // Replace with actual ID if available
//                .perform(typeText("Recipient Name"), closeSoftKeyboard());
//        onView(withHint("Your message")) // Replace with actual ID if available
//                .perform(typeText("Your message"), closeSoftKeyboard());
//        onView(withText("Send")).perform(click());
//        // Add assertions or intended actions if necessary
//    }

    @Test
    public void testEmptyMessageHandling() {
        onView(withId(R.id.fabCompose)).perform(click());

        // Enter an empty recipient and message
        onView(withId(R.id.senderTextView))
                .perform(typeText("Recipient Name"), closeSoftKeyboard());
        onView(withId(R.id.contentTextView))
                .perform(typeText("Your message"), closeSoftKeyboard());

        // Click on the Send button
        onView(withText("Send")).perform(click());

        // Check for Toast message - replace with actual Toast text
        onView(withText("Recipient is empty. Try again"))
                .inRoot(withDecorView(not(is(activityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(isDisplayed()));

        // You can add similar checks for other Toast messages if needed
    }

//    @Test
//    public void testMessageRefresh() {
//        // This test might require mocking the backend or using a testing database
//        // Assume a new message is sent to the current user
//        // Check if the new message appears in the RecyclerView
//        onView(withId(R.id.recyclerViewMessages))
//                .check(matches(hasDescendant(withText("New Message Content"))));
//    }

//    @Test
//    public void testMessageSortingAndOrder() {
//        // Send and receive messages in a specific order
//        // Check if messages are displayed in correct order
//        onView(withId(R.id.recyclerViewMessages))
//                .check(matches(isDisplayed())); // Replace with logic to verify order
//    }
}
