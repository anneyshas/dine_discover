/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dine_discover.LoginSystem.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    /**
     * SignupActivity is responsible for handling the sign in page
     * and sending the user's email to database to create a new record
     * for the user.
     *
     * @Holly Jacob
     * @author Gauri Chopra
     */

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button signup_button = (Button) findViewById(R.id.signupPageButton);
        signup_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //get the email text
                EditText email_field = (EditText) findViewById(R.id.emailTextSignup);
                String email = email_field.getText().toString();
                email_field.setText("");
                //get the password text
                EditText password_field = (EditText) findViewById(R.id.passwordTextSignup);
                String password = password_field.getText().toString();
                password_field.setText("");

                // error checking
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter email!",
                                 Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter password!",
                                 Toast.LENGTH_SHORT).show();
                    return;
                }

                // register new user
                mAuth
                        .createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Registration successful!",
                                                 Toast.LENGTH_SHORT).show();

                                    String userId = mAuth.getCurrentUser().getUid();
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("email", email);

                                    // Store user data in Firestore under 'user' collection
                                    db.collection("user").document(userId)
                                            .set(user)
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                                                // Send intent to the user profile activity
                                                Intent intent = new Intent(SignupActivity.this, User.class);
                                                startActivity(intent);
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(getApplicationContext(), "Error adding user data to Firestore", Toast.LENGTH_SHORT).show();
                                            });
                                } else {
                                    // Registration failed
                                    Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                //send intent to the user profile activity

            }
        });


        /**
         * Author: Holly Jacob
         */
        // login button click
        Button login_button = findViewById(R.id.signupPageLoginButton);
        login_button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
        /**
         * Author: Holly Jacob
         */
        //set up the back button
        ImageView back = (ImageView) findViewById(R.id.loginBackButton);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}