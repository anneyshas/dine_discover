package com.example.dine_discover;

import com.example.dine_discover.JSON_Interpreter.JSONParser;
import com.example.dine_discover.JSON_Interpreter.JSONToken;
import com.example.dine_discover.JSON_Interpreter.JSONTokenizer;

import java.util.List;
import java.util.Map;

public class JSONParserTest {

    public void testParseSimpleString() {
        String jsonString = "{\"name\": \"John\", \"age\": 30, \"married\": true, \"children\": [\"Ann\", \"Billy\"], \"address\": null}";

        // Tokenize the input
        List<JSONToken> tokens = JSONTokenizer.tokens(jsonString);

//        JSONParser parser = new JSONParser(tokens);
//        Map<String, Object> jsonObject = (Map<String, Object>) parser.parse();
//
//        for (String keys : jsonObject.keySet())
//        {
//            System.out.println(keys + ", " + jsonObject.get(keys));
//        }
    }
}
