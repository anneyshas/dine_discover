/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.Tokenizer_Parser;

import java.util.Objects;

/**
 * <p>
 * QueryTokens contains the tokens usable using tokeniser </p>
 *
 */

public class QueryTokens {
    public QueryTokens(String token, Type type) {
        this.token = token;
        this.type = type;
    }
    public String getValue() {
        return token;
    }
    public enum Type {
        NAME,
        SUBURB, // shown as outcode in json
        RATING,
        CUISINE, // shown as type_of_food in json
    }
    /**
     * The following exception should be thrown if a tokenizer attempts to tokenize something that is not of one
     * of the types of tokens.
     */
    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }
    // Fields of the class Token.
    private final String token; // Token representation in String form.
    private final Type type;    // Type of the token.
    public String getToken() {
        return token;
    }
    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true; // Same hashcode.
        if (!(other instanceof QueryTokens)) return false; // Null or not the same type.
        return this.type == ((QueryTokens) other).getType() && this.token.equals(((QueryTokens) other).getToken());
    }
    @Override
    public String toString() {
        return type.toString() + ": "+ token;
    }
    @Override
    public int hashCode() {
        return Objects.hash(token, type);
    }
}