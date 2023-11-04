package com.example.findmyclassmates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText, repeatPasswordEditText;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = findViewById(R.id.usernameEditTextSignUp);
        passwordEditText = findViewById(R.id.passwordEditTextSignUp);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);

        signUpButton = findViewById(R.id.signUpButtonSignUpPage);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String repeatPassword = repeatPasswordEditText.getText().toString();

                if (validateInput(username, password, repeatPassword)) {
                    // Proceed with the sign-up process if the passwords match
                    // Here you would handle the sign-up logic
                    signUpNewUser(username, password);
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

    private void signUpNewUser(String username, String password) {
        Intent mainActivityIntent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
        finish(); // Call this to finish the current Activity and remove it from the back stack.
    }
}
