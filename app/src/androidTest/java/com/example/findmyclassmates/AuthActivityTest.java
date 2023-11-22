package com.example.findmyclassmates;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
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
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class AuthActivityTest {

    @Rule
    public IntentsTestRule<AuthActivity> intentsTestRule =
            new IntentsTestRule<>(AuthActivity.class);

    // Test for Empty Username and Password
    @Test
    public void testEmptyUsernameAndPassword() {
        onView(withId(R.id.signInButton)).perform(click());
        onView(withId(R.id.usernameEditText))
                .check(matches(hasErrorText("Username cannot be empty")));
//        onView(withId(R.id.passwordEditText))
//                .check(matches(hasErrorText("Password cannot be empty")));
    }

//    public void testEmptyPassword() {
////        onView(withId(R.id.usernameEditText))
////                .check(matches(hasErrorText("Username cannot be empty")));
//
//        onView(withId(R.id.usernameEditText))
//                .perform(typeText("temp"), closeSoftKeyboard());
//
//        onView(withId(R.id.passwordEditText))
//                .check(matches(hasErrorText("Password cannot be empty")));
//        onView(withId(R.id.signInButton)).perform(click());
//    }

    // Test for Valid Login
    @Test
    public void testValidSignIn() {
        onView(withId(R.id.usernameEditText))
                .perform(typeText("tempUserAcct"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText))
                .perform(typeText("tempPass"), closeSoftKeyboard());
        onView(withId(R.id.signInButton)).perform(click());

        // If using Idling Resources, Espresso will wait for the async task to complete
        intended(hasComponent(MainActivity.class.getName()));
    }

    // Test for Invalid Login
    @Test
    public void testInvalidSignIn() {
        onView(withId(R.id.usernameEditText))
                .perform(typeText("invalidUsername"), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText))
                .perform(typeText("invalidPassword"), closeSoftKeyboard());
        onView(withId(R.id.signInButton)).perform(click());

        // Check for a toast message or error message that would appear on invalid login
        // This part of the test will depend on how your app handles invalid logins
    }

    // Test for Navigation to SignUp Activity
    @Test
    public void testNavigationToSignUp() {
        onView(withId(R.id.signUpButton)).perform(click());
        intended(hasComponent(SignUpActivity.class.getName()));
    }

    // Additional tests can be added here based on the requirements

}
