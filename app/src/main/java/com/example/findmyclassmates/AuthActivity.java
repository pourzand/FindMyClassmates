package com.example.findmyclassmates;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// firebase imports
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthActivity extends AppCompatActivity {

    Button signInButton, signUpButton;
    EditText usernameEditText, passwordEditText;
    BottomNavigationView bottomNavigationView;

    // firebase
    DatabaseReference dbReference;
    FirebaseDatabase fbRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

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

                signIn(username, password);
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
        Log.println(Log.INFO,"Inside signIn", "usernameGiven: " + username + ", and passwordGiven: " + password);

        // If input is valid:
        if (validateInput(username, password)) {
            boolean correctCredentials = false;
            fbRoot = FirebaseDatabase.getInstance();

            // need to do error checking to see what could go wrong if invalid username
            dbReference = fbRoot.getReference(username);
            // obtain user's password
            Task<com.google.firebase.database.DataSnapshot> retrievedPassObject = dbReference.get();
            String retrievedPass = retrievedPassObject.getResult().getValue().toString();
            Log.println(Log.INFO,"Inside signIn", "retrievedPass: " + retrievedPass);


            if(retrievedPass.equals(password)) {
                // continue with log in flow
                Log.println(Log.INFO,"Inside signIn comparison", "we should be good");

                correctCredentials = true;

                // destroy when done i guess
                fbRoot = null;
                dbReference = null;
            } else {
                // cannot proceed

            }

            if(correctCredentials) {
                Intent mainActivityIntent = new Intent(AuthActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
                finish(); // Call this to finish the current Activity and remove it from the back stack.
            } else {
                //indicate to user somehow the password is incorrect for that user
            }
        }
    }

    private void signUp() {
        // If sign-up is successful:
        Intent signUpActivityIntent = new Intent(AuthActivity.this, SignUpActivity.class);
        startActivity(signUpActivityIntent);
        finish(); // Call this to finish the current Activity and remove it from the back stack.
    }

}
