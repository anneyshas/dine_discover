/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */


package com.example.dine_discover.JSON_Interpreter;

import java.util.Objects;

public class JSONToken {
    /**
     * JSONToken class contains code relating to the various token types
     * that are used when tokenising json files.
     */

    public static final JSONToken OBJECT_START = createToken(JSONTokenType.L_BRACE, '{');

    public static final JSONToken OBJECT_END = createToken(JSONTokenType.R_BRACE, '}');

    public static final JSONToken ARRAY_START = createToken(JSONTokenType.L_BRACKET, '[');

    public static final JSONToken ARRAY_END = createToken(JSONTokenType.R_BRACKET, ']');

    public static final JSONToken TRUE = createToken(JSONTokenType.TRUE, Boolean.TRUE);

    public static final JSONToken FALSE = createToken(JSONTokenType.FALSE, Boolean.FALSE);

    public static final JSONToken NULL = createToken(JSONTokenType.NULL);

    public static final JSONToken COMMA = createToken(JSONTokenType.COMMA, ',');

    public static final JSONToken COLON = createToken(JSONTokenType.COLON, ':');

    public static final JSONToken EOF = createToken(JSONTokenType.EOF);

    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }

    // Fields of the class Token.
    private Object value;  // Token representation in String or Number form.
    private final JSONTokenType type;    // Type of the token.

    private JSONToken(JSONTokenType type) {
        this.type = type;
    }

    private JSONToken(JSONTokenType type, Object value) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public JSONTokenType getType() {return type;}

    private static JSONToken createToken(JSONTokenType type) {
        return new JSONToken(type);
    }

    private static JSONToken createToken(JSONTokenType type, Object token) {
        return new JSONToken(type, token);
    }

    public static JSONToken createStringToken(String aString) {
        return new JSONToken(JSONTokenType.STRING, aString);
    }

    public static JSONToken createNumberToken(Double aNumber) {
        return new JSONToken(JSONTokenType.NUMBER, aNumber);
    }

    public boolean isNumber() {
        return type == JSONTokenType.NUMBER;
    }

    public boolean isString() {
        return type == JSONTokenType.STRING;
    }

    public boolean booleanValue() {
        return (Boolean) value;
    }

    public String stringValue() {
        return (String) value;
    }

    public Double numericValue() {
        return (Double) value;
    }


    @Override
    public String toString() {
        if (type == JSONTokenType.NUMBER) {
            return "NUM(" + value + ")";
        } else {
            return type + "";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true; // Same hashcode.
        if (!(other instanceof JSONToken)) return false; // Null or not the same type.
        return this.type == ((JSONToken) other).getType() && this.value.equals(((JSONToken) other).getValue()); // Values are the same.
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}