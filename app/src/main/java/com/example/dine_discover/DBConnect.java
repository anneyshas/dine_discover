/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dine_discover.LoginSystem.LoginState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DBConnect {
    /**
     * Another data retrieval file that would help with retrieving from firebase
     *
     * @author Anneysha Sarkar
     */
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void fetchDataAndSaveToFile(Context context) {
        db.collection("restaurants")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            JSONArray jsonArray = new JSONArray();  // JSON array to store documents

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    // convert each document to a JSONObject
                                    JSONObject jsonObject = new JSONObject(document.getData());
                                    jsonObject.put("id", document.getId());  // Include document ID
                                    jsonArray.put(jsonObject);  // Add the JSON object to the array
                                } catch (Exception e) {
                                    Log.d(TAG, "Error converting document to JSON: ", e);
                                }
                            }

                            // save data to a local file
                            saveJsonToFile(context, jsonArray);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void saveJsonToFile(Context context, JSONArray jsonArray) {
        // define the file path and name
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "restaurants_data.json");

        try (FileWriter writer = new FileWriter(file)) {
            // write the JSON array to the file
            writer.write(jsonArray.toString());
            Log.d(TAG, "Data saved to file: " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.d(TAG, "Error writing JSON file: ", e);
        }
    }
}
