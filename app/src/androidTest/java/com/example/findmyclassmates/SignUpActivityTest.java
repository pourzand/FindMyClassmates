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
    public void testEmptyUsername() {
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

    @Test
    public void testPasswordCriteriaLowerCase() {
        // Enter values into the fields
        onView(withId(R.id.usernameEditTextSignUp)).perform(ViewActions.typeText("testuser"));
        onView(withId(R.id.passwordEditTextSignUp)).perform(ViewActions.typeText("PASSWORD1"));
        onView(withId(R.id.repeatPasswordEditText)).perform(ViewActions.typeText("PASSWORD1"));

        // Click on the sign-up button
        onView(withId(R.id.signUpButtonSignUpPage)).perform(ViewActions.click());

        // Check if the appropriate error message is displayed for passwordEditText
        onView(withId(R.id.passwordEditTextSignUp)).check(matches(hasErrorText("Password must contain at least one lowercase letter")));
    }

    // Test for Password Criteria: At least one uppercase letter
    @Test
    public void testPasswordCriteriaUpperCase() {
        // Enter values into the fields
        onView(withId(R.id.usernameEditTextSignUp)).perform(ViewActions.typeText("testuser"));
        onView(withId(R.id.passwordEditTextSignUp)).perform(ViewActions.typeText("password1"));
        onView(withId(R.id.repeatPasswordEditText)).perform(ViewActions.typeText("password1"));

        // Click on the sign-up button
        onView(withId(R.id.signUpButtonSignUpPage)).perform(ViewActions.click());

        // Check if the appropriate error message is displayed for passwordEditText
        onView(withId(R.id.passwordEditTextSignUp)).check(matches(hasErrorText("Password must contain at least one uppercase letter")));
    }

    // Test for Password Criteria: At least one digit
    @Test
    public void testPasswordCriteriaDigit() {
        // Enter values into the fields
        onView(withId(R.id.usernameEditTextSignUp)).perform(ViewActions.typeText("testuser"));
        onView(withId(R.id.passwordEditTextSignUp)).perform(ViewActions.typeText("Password"));
        onView(withId(R.id.repeatPasswordEditText)).perform(ViewActions.typeText("Password"));

        // Click on the sign-up button
        onView(withId(R.id.signUpButtonSignUpPage)).perform(ViewActions.click());

        // Check if the appropriate error message is displayed for passwordEditText
        onView(withId(R.id.passwordEditTextSignUp)).check(matches(hasErrorText("Password must contain at least one digit")));
    }

    // Test for Password Criteria: At least one special character
    @Test
    public void testPasswordCriteriaSpecialCharacter() {
        // Enter values into the fields
        onView(withId(R.id.usernameEditTextSignUp)).perform(ViewActions.typeText("testuser"));
        onView(withId(R.id.passwordEditTextSignUp)).perform(ViewActions.typeText("Password1"));
        onView(withId(R.id.repeatPasswordEditText)).perform(ViewActions.typeText("Password1"));

        // Click on the sign-up button
        onView(withId(R.id.signUpButtonSignUpPage)).perform(ViewActions.click());

        // Check if the appropriate error message is displayed for passwordEditText
        onView(withId(R.id.passwordEditTextSignUp)).check(matches(hasErrorText("Password must contain at least one special character")));
    }

    // Test for Password Criteria: Minimum length of 8 characters
    @Test
    public void testPasswordCriteriaMinLength() {
        // Enter values into the fields
        onView(withId(R.id.usernameEditTextSignUp)).perform(ViewActions.typeText("testuser"));
        onView(withId(R.id.passwordEditTextSignUp)).perform(ViewActions.typeText("Pass?1"));
        onView(withId(R.id.repeatPasswordEditText)).perform(ViewActions.typeText("Pass?1"));

        // Click on the sign-up button
        onView(withId(R.id.signUpButtonSignUpPage)).perform(ViewActions.click());

        // Check if the appropriate error message is displayed for passwordEditText
        onView(withId(R.id.passwordEditTextSignUp)).check(matches(hasErrorText("Password must be at least 8 characters long")));
    }

    // Test for Password Criteria: No consecutive repeated characters
    @Test
    public void testPasswordCriteriaNoConsecutiveRepeats() {
        // Enter values into the fields
        onView(withId(R.id.usernameEditTextSignUp)).perform(ViewActions.typeText("testuser"));
        onView(withId(R.id.passwordEditTextSignUp)).perform(ViewActions.typeText("Password?11"));
        onView(withId(R.id.repeatPasswordEditText)).perform(ViewActions.typeText("Password?11"));

        // Click on the sign-up button
        onView(withId(R.id.signUpButtonSignUpPage)).perform(ViewActions.click());

        // Check if the appropriate error message is displayed for passwordEditText
        onView(withId(R.id.passwordEditTextSignUp)).check(matches(hasErrorText("Password cannot contain consecutive repeated characters")));
    }


    // Add more test cases as needed based on your requirements
}

