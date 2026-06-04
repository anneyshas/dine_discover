/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.JSON_Interpreter;

import com.example.dine_discover.Reply;
import com.example.dine_discover.Review;
import com.example.dine_discover.SearchTreeFolder.GenericsAVLTree;

import com.example.dine_discover.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class JSON Parser takes in the tokenised information from the JSON file and parses the information
 * into relevant objects. The JSON parser will set information into restaurants, reply, review, genericAVL trees, hashmaps,
 * and arrays accordingly.
 *
 * @author  Sarah Palmer
 * @since   2024-10-17
 */

public class JSONParser {

    private final List<JSONToken> JSONTokens;
    private int pos = 0;
    GenericsAVLTree<String> cuisineTree = new GenericsAVLTree<>();
    GenericsAVLTree<String> suburbTree = new GenericsAVLTree<>();
    GenericsAVLTree<String> nameTree = new GenericsAVLTree<>();
    GenericsAVLTree<Double> ratingTree = new GenericsAVLTree<>();


    public JSONParser(List<JSONToken> JSONTokens) {
        this.JSONTokens = JSONTokens;
    }

    public Object parse() {
        return parseValue();
    }


    private Object parseValue() {
        JSONToken tok = JSONTokens.get(pos);

        switch (tok.getType()) {
            case L_BRACE:
                return parseObject();
            case L_BRACKET:
                return parseToDataStructure();
            case STRING:
                pos++;
                return tok.getValue();
            case NUMBER:
                pos++;
                return Double.parseDouble(String.valueOf(tok.getValue()));
            case TRUE:
            case FALSE:
                pos++;
                return Boolean.parseBoolean(String.valueOf(tok.getValue()));
            case NULL:
            case EOF:
                pos++;
                return null;
            default:
                throw new RuntimeException("Unexpected token error");
        }
    }

    private Object parseObject() {
        pos++; // skip '{'

        if (JSONTokens.get(pos).getValue().equals("URL")) {
            return parseToRestaurant();
        }
        if (JSONTokens.get(pos).getValue().equals("comment")) {
            return parseToReview();
        }
        if (JSONTokens.get(pos).getValue().equals("userEmail")) {
            return parseToReply();
        }
        return parseToMap();
    }

    private Reply parseToReply() {
        Reply reply = new Reply();
        // pos++;  // skip '['

        while (JSONTokens.get(pos).getType() != JSONTokenType.R_BRACE) {
            String key = (String) parseValue(); // Get field
            pos++;  // skip ':'
            Object value = parseValue(); // Get the information for the relevant field

            switch (key) {
                case "reply": reply.setComment((String) value); break;
                case "userEmail": reply.setUserEmail((String) value); break;
            }
            if (JSONTokens.get(pos).getType() == JSONTokenType.COMMA) {
                pos++;  // skip ','
            }
        }

        pos++;  // skip ']'
        return reply;
    }

    private Review parseToReview() {
        Review review = new Review();
        // pos++;  // skip '['

        while (JSONTokens.get(pos).getType() != JSONTokenType.R_BRACE) {
            String key = (String) parseValue(); // Get field
            pos++;  // skip ':'
            Object value = parseValue(); // Get the information for the relevant field

            switch (key) {
                case "userEmail": review.setUser((String) value); break;
                case "comment": review.setComment((String) value); break;
                case "replies": review.setReplies((List<Reply>) value);
                    //case "rating": review.setRating((Integer) value); break;
                    //case "time_stamp": review.setTime_stamp((Integer) value); break;
            }
            if (JSONTokens.get(pos).getType() == JSONTokenType.COMMA) {
                pos++;  // skip ','
            }
        }

        pos++;  // skip ']'
        return review;

    }

    private Map<String, Object> parseToMap() {
        Map<String, Object> jsonMap = new HashMap<>();
        //pos++;// skip '{'

        while (JSONTokens.get(pos).getType() != JSONTokenType.R_BRACE) {
            String key = (String) parseValue();
            pos++;  // skip ':'
            Object value = parseValue();
            jsonMap.put(key, value);

            if (JSONTokens.get(pos).getType() == JSONTokenType.COMMA) {
                pos++;  // skip ','
            }
        }

        pos++;  // skip '}'
        return jsonMap;
    }

    private Restaurant parseToRestaurant() {
        Restaurant restaurant = new Restaurant();
        //pos++;// skip '{'

        while (JSONTokens.get(pos).getType() != JSONTokenType.R_BRACE) {
            String key = (String) parseValue(); // Get field, e.g "name", "rating"
            pos++;  // skip ':'
            Object value = parseValue(); // Get the information for the relevant field

            switch (key) {
                case "URL": restaurant.setURL((String) value); break;
                case "rating": if (value != null) {
                    restaurant.setRating((Double) value);
                } break;
                case "postcode": restaurant.setPostcode((String) value); break;
                case "name": restaurant.setName((String) value); break;
                case "outcode": restaurant.setOutcode((String) value); break;
                case "reviews":
                    if (value instanceof ArrayList) {
                        try {
                            @SuppressWarnings("unchecked")
                            List<Review> comments = (List<Review>) value;
                            restaurant.setReviews(comments);
                        } catch (ClassCastException e) {
                            System.err.println("Invalid type for comments: " + e.getMessage());
                        }
                    } else {
                        System.err.println("Value is not an Arraylist of Strings");
                    }
                    break;
                case "address": restaurant.setAddress((String) value); break;
                case "address_line_2": restaurant.setAddressLine2((String) value); break;
                case "cuisine": restaurant.setCuisine((String) value); break;
                case "num_reviews" : restaurant.setNum_reviews((Double) value); break;
            }

            if (JSONTokens.get(pos).getType() == JSONTokenType.COMMA) {
                pos++;  // skip ','
            }
        }

        pos++;  // skip '}'
        return restaurant;
    }

    private Object parseToDataStructure() {
        pos++; // skip '['
        pos++; // skip '{'

        if (JSONTokens.get(pos).getValue().equals("URL")) {
            pos--; // go back to' '{'
            return parseToAVL();
        }
        else {
            pos--; // go back to' '{'
            return parseToArray();
        }
    }

    private List<Object> parseToArray() {
        List<Object> jsonArray = new ArrayList<>();
        // pos++;  // skip '['

        while (JSONTokens.get(pos).getType() != JSONTokenType.R_BRACKET) {

            Object a = JSONTokens.get(pos).getValue();
            jsonArray.add(parseValue());
            Object b = JSONTokens.get(pos).getValue();

            if (JSONTokens.get(pos).getType() == JSONTokenType.COMMA) {
                pos++;  // skip ','
            }
        }

        pos++;  // skip ']'
        return jsonArray;
    }

    private GenericsAVLTree<String> parseToAVL() {
        //pos++;  // skip '['

        while (JSONTokens.get(pos).getType() != JSONTokenType.R_BRACKET) {
            Restaurant element = (Restaurant) parseValue();
            assert element != null;
            cuisineTree.insertNode(element, element.getCuisine());
            suburbTree.insertNode(element, element.getAddressLine2());
            nameTree.insertNode(element, element.getName());
            ratingTree.insertNode(element, element.getRating());

            if (JSONTokens.get(pos).getType() == JSONTokenType.COMMA) {
                pos++;  // skip ','
            }
        }
        pos++;  // skip ']'
        return cuisineTree;
    }

    public GenericsAVLTree<String> getCuisineTree() {
        return cuisineTree;
    }

    public GenericsAVLTree<String> getSuburbTree() {
        return suburbTree;
    }

    public GenericsAVLTree<String> getNameTree() {
        return nameTree;
    }

    public GenericsAVLTree<Double> getRatingTree() {
        return ratingTree;
    }
}