/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.Tokenizer_Parser;

//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions;


import java.util.List;

public class QueryTokenizerTest {
//
//    @Test
//    public void testTokenizeSingleName() {
//        String input = "RestaurantName";
//        QueryTokenizer tokenizer = new QueryTokenizer(input);
//
//        List<QueryTokens> tokens = tokenizer.getTokensList();
//        Assertions.assertEquals(1, tokens.size());
//        Assertions.assertEquals("RestaurantName", tokens.get(0).getValue());
//        Assertions.assertEquals(QueryTokens.Type.NAME, tokens.get(0).getType());
//    }
//
//    @Test
//    public void testTokenizeSingleCuisine() {
//        String input = "italian";
//        QueryTokenizer tokenizer = new QueryTokenizer(input);
//
//        List<QueryTokens> tokens = tokenizer.getTokensList();
//        Assertions.assertEquals(1, tokens.size());
//        Assertions.assertEquals("italian", tokens.get(0).getValue());
//        Assertions.assertEquals(QueryTokens.Type.CUISINE, tokens.get(0).getType());
//    }
//
//    @Test
//    public void testTokenizeSingleRating() {
//        String input = "4";
//        QueryTokenizer tokenizer = new QueryTokenizer(input);
//
//        List<QueryTokens> tokens = tokenizer.getTokensList();
//        Assertions.assertEquals(1, tokens.size());
//        Assertions.assertEquals("4", tokens.get(0).getValue());
//        Assertions.assertEquals(QueryTokens.Type.RATING, tokens.get(0).getType());
//    }
//
//    @Test
//    public void testTokenizeSuburb() {
//        String input = "@Braddon";
//        QueryTokenizer tokenizer = new QueryTokenizer(input);
//
//        List<QueryTokens> tokens = tokenizer.getTokensList();
//        Assertions.assertEquals(1, tokens.size());
//        Assertions.assertEquals("Braddon", tokens.get(0).getValue());
//        Assertions.assertEquals(QueryTokens.Type.SUBURB, tokens.get(0).getType());
//    }
//
//    @Test
//    public void testTokenizeMultipleTokens() {
//        String input = "@braddon, Italian, 4";
//        QueryTokenizer tokenizer = new QueryTokenizer(input);
//
//        List<QueryTokens> tokens = tokenizer.getTokensList();
//        Assertions.assertEquals(4, tokens.size());
//        Assertions.assertEquals("braddon", tokens.get(0).getValue());
//        Assertions.assertEquals(QueryTokens.Type.SUBURB, tokens.get(0).getType());
//        Assertions.assertEquals("Italian", tokens.get(1).getValue());
//        Assertions.assertEquals(QueryTokens.Type.CUISINE, tokens.get(1).getType());
//        Assertions.assertEquals("4", tokens.get(2).getValue());
//        Assertions.assertEquals(QueryTokens.Type.RATING, tokens.get(2).getType());
//        Assertions.assertEquals("Chicken", tokens.get(3).getValue());
//        Assertions.assertEquals(QueryTokens.Type.NAME, tokens.get(3).getType());
//    }
//
//
//
//    @Test
//    public void testIllegalTokenExceptionInvalidRating() {
//        String input = "6"; // Invalid rating
//        QueryTokenizer tokenizer = new QueryTokenizer(input);
//
//        Exception exception = Assertions.assertThrows(QueryTokenizer.IllegalTokenException.class, () -> {
//            tokenizer.tokeniseSearchBar();
//        });
//        Assertions.assertEquals("the rating entered is not within 1 to 5", exception.getMessage());
//    }
//
//
//    @Test
//    public void testEmptyInput() {
//        String input = "";
//        QueryTokenizer tokenizer = new QueryTokenizer(input);
//
//        List<QueryTokens> tokens = tokenizer.getTokensList();
//        Assertions.assertTrue(tokens.isEmpty());
//    }
}
