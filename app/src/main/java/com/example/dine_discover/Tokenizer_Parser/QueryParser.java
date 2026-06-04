/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.Tokenizer_Parser;
import static java.lang.Double.parseDouble;
import com.example.dine_discover.DBRetrieve;
import com.example.dine_discover.Restaurant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class QueryParser {
    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }
    QueryTokenizer tokenizer;
    private List<QueryTokens> tokensList;
    private String suburb;
    private String cuisine;
    private String rating;
    private String name;
    private DBRetrieve dbRetrieve;
    private String suburb_value;
    private String name_value;
    private double rating_value;
    private String cuisine_value;

    /**
     * Parser class constructor
     * Sets the tokenizer field.
     *
     * @author Gauri Chopra
     */
    public QueryParser(QueryTokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.tokensList = tokenizer.getTokensList();
    }
    public void parse() {
        for (QueryTokens token : tokensList) {
            if (token.getType() == QueryTokens.Type.SUBURB) {
                suburb_value = token.getValue();
            } else if (token.getType() == QueryTokens.Type.CUISINE) {
                cuisine_value = token.getValue();
            } else if (token.getType() == QueryTokens.Type.RATING) {
                rating_value = parseDouble(token.getValue());
            } else if (token.getType() == QueryTokens.Type.NAME) {
                name_value = token.getValue();
            }
        }
    }
    public List<Restaurant> getFilteredRestaurants(String name, String location) {
        Map<String, Object> preferences = new HashMap<>();
        preferences.put("name", name_value);
        preferences.put("address_line_2", suburb_value);
        preferences.put("cuisine", cuisine_value);
        preferences.put("rating", rating_value);
        return dbRetrieve.filterRestaurants(preferences);
    }
    public String getSuburb() {
        return suburb_value;
    }
    public String getCuisine() {
        return cuisine_value;
    }
    public Double getRating() {
        return rating_value;
    }
    public String getName() {
        return name_value;
    }
}
