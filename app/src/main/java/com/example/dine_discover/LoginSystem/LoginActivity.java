/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.LoginSystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dine_discover.CurrentUser;
import com.example.dine_discover.MainActivity;
import com.example.dine_discover.R;
import com.example.dine_discover.SignupActivity;
import com.example.dine_discover.User;
import com.example.dine_discover.UserProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    /**
     * LoginActivity is responsible for handling the login page
     * and capturing the current logged in email, which can then be
     * used to retrieve the current user's data from database.
     *
     * @author Holly Jacob, Gauri Chopra, Anneysha Sarkar
     */


    // firebase authentication
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private User user;
    private EditText emailField, passwordField;
    public String currentEmail;
    private LoginState state;
    public static String loggedInEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailText);
        passwordField = findViewById(R.id.passwordText);
        Button loginButton = findViewById(R.id.loginPageButton);
        Button signupButton = findViewById(R.id.loginPageSignupButton);

        // Assuming that the user is initially logged out
        user = new User("", "");

        //set up the back button
        ImageView back = (ImageView) findViewById(R.id.loginBackButton);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        // Handle Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Handle Sign Up redirection
        signupButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // sign up button click
        Button signup_button = findViewById(R.id.loginPageSignupButton);
        signup_button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
            //Toast.makeText(LoginActivity.this,"Hi",Toast.LENGTH_SHORT).show();
        });

        // Display current user state
//        displayState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is already signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, go directly to UserProfileActivity
            startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
            finish();
        }
    }

    /**
     * Attempts to log in the user using the provided email and password.
     * <p>
     * If the email or password fields are empty, a toast message is displayed
     * prompting the user to enter the required information. If the login is
     * successful, the user is navigated to the UserProfileActivity. If the login
     * fails, an error message is displayed.
     * </p>
     *
     * @author Anneysha Sarkar
     */
    private void loginUser() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Please enter email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        loggedInEmail = emailField.getText().toString().trim();


                        // SignIn is successful
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        user.setState(new LoggedInState());
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//                        displayState();
                        // Navigate to Logged in Activity
                        startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
                        finish();
                    } else {
                        // sign in failed
                        Toast.makeText(LoginActivity.this, "Authentication failed. " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public static String getCurrentEmail(){
        return loggedInEmail;
    }


    public interface OnUserRetrievedListener {
        void onUserRetrieved(CurrentUser currentUser);
    }

    public void getCurrentUserInstance(OnUserRetrievedListener listener) {
        db = FirebaseFirestore.getInstance();
        String loggedInEmail = getCurrentEmail();

        db.collection("user")
                .whereEqualTo("email", loggedInEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                            CurrentUser currentUser = new CurrentUser(document.getData());
                            listener.onUserRetrieved(currentUser);
                        } else {
                            listener.onUserRetrieved(null);
                        }
                    } else {
                        Log.w("Firestore", "Error querying user by email", task.getException());
                        listener.onUserRetrieved(null);
                    }
                });
    }
}
