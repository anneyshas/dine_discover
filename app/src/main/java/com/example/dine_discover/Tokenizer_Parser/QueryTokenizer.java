/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.Tokenizer_Parser;
import android.content.Context;
import android.widget.Toast;
import com.example.dine_discover.CUISINE;
import com.example.dine_discover.SearchActivity;
import java.util.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Tokeniser class is responsible for tokenising the search
 * query string that is generated after the user inputs their
 * preferences. These tokens are then sent to parser. </p>
 *
 * @author Vivian Tran
 */

public class QueryTokenizer {
    private String search; // user's search from the search bar

    private List<QueryTokens> tokensList;
    public class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }
    /**
     * Tokenizer class constructor
     * The constructor extracts the tokens and save it to tokensList
     */
    public QueryTokenizer(String text) {
        this.search = text;// save input text (string)
        this.tokensList = new ArrayList<QueryTokens>();                 // extracts the first token.
        tokeniseSearchBar();
    }

    /**
     * This function will find and extract all words from the search bar
     * and tokenise using tokenise() method
     */
    public void tokeniseSearchBar() {
        if (search.isEmpty()) {
            return;
        }
        search = search.toLowerCase();
        while (!search.isEmpty()) {
            // comma index
            int commaIndex = search.indexOf(',');
            if (commaIndex != -1) {
                // go through the search, search for the comma and substring the words
                String cur = search.substring(0, commaIndex);
                // tokenise input
                tokeniseTerm(cur);
                // substring search
                search = search.substring(commaIndex + 1);
            } else {
                tokeniseTerm(search);
                return;
            }
        }
    }
    /**
     * This function acts as a helper function to tokenise the name and surburb tokens from the search bar
     * and save the token to tokensList.
     */
    public void tokeniseTerm(String string) {
        if (string.isEmpty()) {
            return;
        }
        // trim white spaces
        string = string.trim();
        char c = string.charAt(0);
        // check for '@' char,  if true then this is a suburb token
        if (c == '@') {
            string = string.substring(1);
            string = string.trim();
            // if there are more than one '@' then throw new exception
            if (string.charAt(0) == '@') throw new IllegalTokenException("you have entered too many '@");
            QueryTokens token = new QueryTokens(string, QueryTokens.Type.SUBURB);
            if (!tokensList.contains(token)) {
                tokensList.add(token);
            }
        }
        // tokenise the name
        else {
            //if (isDouble(string)) throw new IllegalTokenException("rating cannot be a double");;
            QueryTokens token;
            // check if string is a number
            if (isInteger(string)) token = new QueryTokens(string, QueryTokens.Type.RATING);
            else if (isValidCuisine(string)) token = new QueryTokens(string, QueryTokens.Type.CUISINE);
            else token = new QueryTokens(string, QueryTokens.Type.NAME);
            if (!tokensList.contains(token)) {
                tokensList.add(token);
            }
        }
    }
    // check if the given string is a number
    public boolean isInteger(String str) {
        if (str == null) return false;
        try{
            int num = Integer.parseInt(str);
            if (num>5 || num<1) throw new IllegalTokenException("the rating entered is not within 1 to 5");
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }
    // check if the cuisine entered is in the list of cuisine
    public boolean isValidCuisine(String str) {
        try {
            CUISINE.valueOf(str.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    public List<QueryTokens> getTokensList() {
        return tokensList;
    }
    // check if the string is a double
    public boolean isDouble(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void main(String[] args) {
        String search ="@Braddon, @Braddon, chicken, @Gunghalin";
        QueryTokenizer tok = new QueryTokenizer(search);
        System.out.println(tok.getTokensList());
    }
}




