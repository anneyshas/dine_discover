/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.JSON_Interpreter;

public enum JSONTokenType {
    /**
     * JSONTokenizer class is an enum class containing the token types.
     */
    L_BRACE,
    R_BRACE,
    L_BRACKET,
    R_BRACKET,
    NUMBER,
    STRING,
    TRUE,
    FALSE,
    NULL,
    COMMA,
    COLON,
    EOF
}

