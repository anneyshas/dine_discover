/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.Card_Factory;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dine_discover.R;

public class Card1Activity extends AppCompatActivity {
/**
 * Called when the activity is first created.
 * <p>
 * This method sets the content view to the layout defined in
 * {@code activity_card}, retrieves the title and description passed
 * through the intent, and sets these values in the corresponding
 * {@link TextView} elements.
 * </p>
 *
 * @author Anneysha Sarkar
 **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        // Retrieve the passed data
        String title = getIntent().getStringExtra("cardTitle");
        String description = getIntent().getStringExtra("cardDescription");

        // Set these values in the UI
        TextView titleView = findViewById(R.id.titleView);
        TextView descriptionView = findViewById(R.id.descriptionView);
        titleView.setText(title);
        descriptionView.setText(description);
    }
}
