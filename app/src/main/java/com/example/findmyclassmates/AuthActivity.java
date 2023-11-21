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

    boolean validateInput(String username, String password) {
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

    void signIn(String username, String password) {
        // If input is valid(which at the moment means not null):
        if (validateInput(username, password)) {
            fbRoot = FirebaseDatabase.getInstance();
            dbReference = fbRoot.getReference(username);

            dbReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        String retrievedPass = dataSnapshot.getValue(String.class);
                        Log.println(Log.INFO, "Inside signIn", "retrievedPass: " + retrievedPass);

                        if (retrievedPass.equals(password)) {
                            // Correct credentials, continue with login flow
                            Log.println(Log.INFO, "Inside signIn comparison", "Login successful");

                            UserSession.getInstance().setUsername(username);

                            Intent mainActivityIntent = new Intent(AuthActivity.this, MainActivity.class);
                            startActivity(mainActivityIntent);
                            finish();
                        } else {
                            // Incorrect password, indicate to the user
                            Toast.makeText(AuthActivity.this, "Incorrect password", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // Username not found, indicate to the user
                        Toast.makeText(AuthActivity.this, "Username not found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Handle any errors that occurred during the database fetch
                    Toast.makeText(AuthActivity.this, "An error occurred while fetching data", Toast.LENGTH_LONG).show();
                }

                // Always release resources when done
                fbRoot = null;
                dbReference = null;
            });
        }
    }


    private void signUp() {
        // If sign-up is successful:
        Intent signUpActivityIntent = new Intent(AuthActivity.this, SignUpActivity.class);
        startActivity(signUpActivityIntent);
        finish(); // Call this to finish the current Activity and remove it from the back stack.
    }

}