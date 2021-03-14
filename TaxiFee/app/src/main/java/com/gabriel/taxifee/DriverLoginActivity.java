package com.gabriel.taxifee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * DriverLoginActivity is responsible for authentication of user or adding a new one
 */
public class DriverLoginActivity extends AppCompatActivity {



    // Initialization of objects for Driver Login
    private Button loginButton, registrationButton;
    private EditText emailText, passwordText;

    // Initialization of FireBase
    private FirebaseAuth authentication;
    private FirebaseAuth.AuthStateListener fireBaseOfListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        // Implementing FireBase authentication
        authentication = FirebaseAuth.getInstance();
        fireBaseOfListener = firebaseAuth -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(DriverLoginActivity.this, DriverMapActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        };



        // Buttons of DriverActivity
        loginButton = (Button) findViewById(R.id.login);
        registrationButton = (Button) findViewById(R.id.register);

        // EditText for DriverActivity
        emailText = (EditText) findViewById(R.id.email_address);
        passwordText = (EditText) findViewById(R.id.password);

        // Adding functionality to register button
        registrationButton.setOnClickListener(v -> {
            final String email = emailText.getText().toString();
            final String password = passwordText.getText().toString();
            if (email.isEmpty()) {
                Log.d("DriverActivity.java", "Email is null");
                Toast.makeText(DriverLoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            }
            if (password.isEmpty()) {
                Log.d("DriverActivity.java", "Email is null");
                Toast.makeText(DriverLoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            }

            authentication.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(DriverLoginActivity.this, task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(DriverLoginActivity.this, "Sign up error", Toast.LENGTH_SHORT).show();
                        } else {
                            String userID = authentication.getCurrentUser().getUid();
                            DatabaseReference currentUserDB = FirebaseDatabase.
                                    getInstance()
                                    .getReference()
                                    .child("Users")
                                    .child("Driver")
                                    .child(userID);
                            currentUserDB.setValue(true);
                        }
                    });
        });

        loginButton.setOnClickListener(v -> {
            final String email = emailText.getText().toString();
            final String password = passwordText.getText().toString();

            authentication.signInWithEmailAndPassword(email, password).addOnCompleteListener(DriverLoginActivity.this, task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(DriverLoginActivity.this, "Sign in error", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        authentication.addAuthStateListener(fireBaseOfListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        authentication.removeAuthStateListener(fireBaseOfListener);
    }
}