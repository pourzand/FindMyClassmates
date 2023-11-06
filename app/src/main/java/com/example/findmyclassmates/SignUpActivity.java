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
            Toast.makeText(SignUpActivity.this, "All fields must be filled", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!password.equals(repeatPassword)) {
            Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
