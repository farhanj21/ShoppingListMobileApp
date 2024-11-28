package com.example.smdassignment4;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button loginButton, registerButton, forgotPasswordButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Link UI elements
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);

        // Add Register and Forgot Password Buttons (optional)
        registerButton = new Button(this);
        registerButton.setText("Register");
        forgotPasswordButton = new Button(this);
        forgotPasswordButton.setText("Forgot Password?");

        // Login Button OnClick
        loginButton.setOnClickListener(view -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Login successful
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                            // Correct Intent to navigate to ShoppingListActivity
                            Intent intent = new Intent(LoginActivity.this, ShoppingListActivity.class);
                            startActivity(intent);
                            finish();  // Finish LoginActivity to prevent going back to it
                        } else {
                            // Login failed
                            Toast.makeText(this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        });

        // Navigate to Register Screen
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });


        // Forgot Password
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        forgotPasswordButton.setOnClickListener(view -> showForgotPasswordDialog());

    }

    private void showForgotPasswordDialog() {
        // Create an AlertDialog to get the user's email
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");
        builder.setMessage("Enter your email to reset your password");

        // Add an EditText to the dialog for email input
        final EditText inputEmail = new EditText(this);
        inputEmail.setHint("Email");
        builder.setView(inputEmail);

        // Set up the dialog buttons
        builder.setPositiveButton("Send Reset Email", (dialog, which) -> {
            String email = inputEmail.getText().toString().trim();

            // Validate email
            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(LoginActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            // Send password reset email using Firebase
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        builder.create().show();
    }
}

