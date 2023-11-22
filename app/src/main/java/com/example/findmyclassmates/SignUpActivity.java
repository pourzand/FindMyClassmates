package com.example.findmyclassmates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText, repeatPasswordEditText;
    Button signUpButton;

    // Firebase
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = findViewById(R.id.usernameEditTextSignUp);
        passwordEditText = findViewById(R.id.passwordEditTextSignUp);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);
        signUpButton = findViewById(R.id.signUpButtonSignUpPage);

        // Initialize Firebase Database reference
        dbReference = FirebaseDatabase.getInstance().getReference(); // Adjust the path as needed

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String repeatPassword = repeatPasswordEditText.getText().toString();

                if (validateInput(username, password, repeatPassword)) {
                    // Check if the username already exists in the database
                    // Will be done in later iterations of this app

                    // If username is not already used, create a key value pair of the username and password
                    dbReference.child(username).setValue(password);

                    UserSession.getInstance().setUsername(username);

                    Intent mainActivityIntent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                    finish();
                }
            }
        });
    }

    private boolean validateInput(String username, String password, String repeatPassword) {
        if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
//            Toast.makeText(SignUpActivity.this, "All fields must be filled", Toast.LENGTH_LONG).show();
            passwordEditText.setError("All fields must be filled");
            usernameEditText.setError("All fields must be filled");
            return false;
        }
        if (!password.equals(repeatPassword)) {
            //Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            passwordEditText.setError("Passwords do not match");
            repeatPasswordEditText.setError("Passwords do not match");


            return false;
        }

        // Check password criteria
        if (!isLowerCasePresent(password)) {
            passwordEditText.setError("Password must contain at least one lowercase letter");
            return false;
        }

        if (!isUpperCasePresent(password)) {
            passwordEditText.setError("Password must contain at least one uppercase letter");
            return false;
        }

        if (!isDigitPresent(password)) {
            passwordEditText.setError("Password must contain at least one digit");
            return false;
        }

        if (!isSpecialCharacterPresent(password)) {
            passwordEditText.setError("Password must contain at least one special character");
            return false;
        }

        if (password.length() < 8) {
            passwordEditText.setError("Password must be at least 8 characters long");
            return false;
        }

        if (hasConsecutiveRepeats(password)) {
            passwordEditText.setError("Password cannot contain consecutive repeated characters");
            return false;
        }


        return true;
    }

    private boolean isLowerCasePresent(String password) {
        return !password.equals(password.toUpperCase());
    }

    private boolean isUpperCasePresent(String password) {
        return !password.equals(password.toLowerCase());
    }

    private boolean isDigitPresent(String password) {
        return password.matches(".*\\d.*");
    }

    private boolean isSpecialCharacterPresent(String password) {
        return !password.matches("[A-Za-z0-9 ]*");
    }

    private boolean hasConsecutiveRepeats(String password) {
        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i) == password.charAt(i + 1)) {
                return true;
            }
        }
        return false;
    }
}
