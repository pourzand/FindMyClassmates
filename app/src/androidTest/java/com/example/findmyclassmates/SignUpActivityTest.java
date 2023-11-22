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
public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> activityRule = new ActivityTestRule<>(SignUpActivity.class);

    @Before
    public void setUp() {
        // You can perform any setup here if needed
    }

    @After
    public void tearDown() {
        // Perform any necessary clean-up after each test
    }

    // Test for Empty Fields
    @Test
    public void testEmptyFields() {
        onView(withId(R.id.signUpButtonSignUpPage)).perform(ViewActions.click());

        // Check if the appropriate error message is displayed for usernameEditText
        onView(withId(R.id.usernameEditTextSignUp)).check(matches(hasErrorText("All fields must be filled")));

        // Check if the appropriate error message is displayed for passwordEditText
        onView(withId(R.id.passwordEditTextSignUp)).check(matches(hasErrorText("All fields must be filled")));

    }

//     Test for Password Mismatch
    @Test
    public void testPasswordMismatch() {
        // Enter values into the fields
        onView(withId(R.id.usernameEditTextSignUp)).perform(ViewActions.typeText("testuser"));
        onView(withId(R.id.passwordEditTextSignUp)).perform(ViewActions.typeText("password1"));
        onView(withId(R.id.repeatPasswordEditText)).perform(ViewActions.typeText("password2"));

        // Click on the sign-up button
        onView(withId(R.id.signUpButtonSignUpPage)).perform(ViewActions.click());

        // Check if the appropriate error message is displayed for passwordEditText
        onView(withId(R.id.passwordEditTextSignUp)).check(matches(hasErrorText("Passwords do not match")));

        // Check if the appropriate error message is displayed for repeatPasswordEditText
        onView(withId(R.id.repeatPasswordEditText)).check(matches(hasErrorText("Passwords do not match")));
    }

    // Add more test cases as needed based on your requirements
}
