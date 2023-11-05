package com.example.findmyclassmates;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button signInButton, signUpButton;
    EditText usernameEditText, passwordEditText;

    // firebase

    DatabaseReference dbReference;
    FirebaseDatabase fbRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the buttons
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);

        // Initialize the EditTexts
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Capture user input from EditTexts
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // firebase
                fbRoot = FirebaseDatabase.getInstance();

                // need to do error checking to see what could go wrong if invalid username
                dbReference = fbRoot.getReference(username);
                // obtain password
                String retrievedPass = dbReference.getKey();

                if(retrievedPass.equals(password)) {
                    // continue with log in flow


                    // destroy when done i guess
                    fbRoot = null;
                    dbReference = null;

                } else {
                    // do not proceed, try again


                }




//                // Validate input
//                if (validateInput(username, password)) {
//                    // Here you would have your sign-in logic
//                    signIn(username, password);
//                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to your sign-up activity or logic
                signUp();
            }
        });
    }

    private boolean validateInput(String username, String password) {
        // Here you would check for validity, e.g., non-empty fields
        if (username.isEmpty()) {
            usernameEditText.setError("Username cannot be empty");
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Password cannot be empty");
            return false;
        }
        // Add other validation checks as necessary
        return true;
    }

    private void signIn(String username, String password) {
        // Replace this with your actual sign-in logic.
        // Currently, just showing a Toast message.
        Toast.makeText(MainActivity.this, "Signing in with Username: " + username, Toast.LENGTH_LONG).show();
        // Intent to another activity could go here if sign in is successful
    }

    private void signUp() {
        // Replace this with your actual sign-up navigation or logic.
        // Currently, just showing a Toast message.
        Toast.makeText(MainActivity.this, "Navigate to Sign Up", Toast.LENGTH_LONG).show();
        // Intent to another activity could go here to handle sign up
    }
}
