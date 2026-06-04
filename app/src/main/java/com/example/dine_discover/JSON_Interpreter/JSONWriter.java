/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.JSON_Interpreter;

import android.util.Log;

import com.example.dine_discover.SearchTreeFolder.GenericsAVLTree;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {
    /**
     * JSONWriter class is responsible for writing to JSON file.
     */

    public static void writeToJSON(GenericsAVLTree<Double> tree, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(tree.toArrayList(), writer);  // Writes the list of nodes to JSON file
        } catch (IOException e) {
            Log.e("writeToJSON", "Error writing the JSON file: " + e.getMessage());
        }
    }
}
