/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.JSON_Interpreter;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JSONToString {
    /**
     * JSONToString class is responsible for loading the JSON file
     * and converting it to string.
     *
     *@author  Sarah Palmer
     * @since   2024-10-17
     */

    public static String loadJSONFile(Context context, String fileName) {
        BufferedReader bufferedReader;
        String json = null;
        try {
            AssetManager assetManager = context.getAssets();

            bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            json = stringBuilder.toString();

            bufferedReader.close();
        } catch (IOException e) {
            Log.e("LoadJSONFile", "Error reading the JSON file: " + e.getMessage());
        }

        return json;
    }
}
