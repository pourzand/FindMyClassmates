package com.example.findmyclassmates;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public IntentsTestRule<SignUpActivity> intentsTestRule =
            new IntentsTestRule<>(SignUpActivity.class);

    // Test for All Fields Empty
    @Test
    public void testAllFieldsEmpty() {
        onView(withId(R.id.signUpButtonSignUpPage)).perform(click());
        onView(withText("All fields must be filled"))
                .check(matches(isDisplayed()));
    }

    // Test for Only Username Filled
    @Test
    public void testOnlyUsernameFilled() {
        onView(withId(R.id.usernameEditTextSignUp)).perform(typeText("testUser"), closeSoftKeyboard());
        onView(withId(R.id.signUpButtonSignUpPage)).perform(click());
        onView(withText("All fields must be filled")).check(matches(isDisplayed()));
    }

    // Test for Only Password Filled
    @Test
    public void testOnlyPasswordFilled() {
        onView(withId(R.id.passwordEditTextSignUp))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.signUpButtonSignUpPage)).perform(click());
        onView(withText("All fields must be filled")).check(matches(isDisplayed()));
    }

    // Test for Password and Repeat Password Do Not Match
    @Test
    public void testPasswordsDoNotMatch() {
        onView(withId(R.id.usernameEditTextSignUp))
                .perform(typeText("testUser"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditTextSignUp))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.repeatPasswordEditText))
                .perform(typeText("different"), closeSoftKeyboard());
        onView(withId(R.id.signUpButtonSignUpPage)).perform(click());
        onView(withText("Passwords do not match"))
                .check(matches(isDisplayed()));
    }

    // Test for Valid Registration
    @Test
    public void testValidRegistration() {
        onView(withId(R.id.usernameEditTextSignUp))
                .perform(typeText("newUser"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditTextSignUp))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.repeatPasswordEditText))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.signUpButtonSignUpPage)).perform(click());

        // Replace MainActivity with the actual activity that is expected to open
        intended(hasComponent(MainActivity.class.getName()));
    }

    // Additional tests for other scenarios can be added here

}

