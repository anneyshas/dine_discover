/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.JSON_Interpreter;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class JSON Tokeniser takes in the JSON string sets the characters of the string into
 * the relevant tokens as set in the class JSONTokenType.
 *
 * @author  Sarah Palmer
 * @since   2024-10-17
 */

// tokenizer for JSON files
public class JSONTokenizer {

    private PushbackReader reader;

    public JSONTokenizer(Reader reader) {
        this.reader = new PushbackReader(reader);
    }

    public static List<JSONToken> tokens(String input) {
        ArrayList<JSONToken> result = new ArrayList<JSONToken>();

        Reader reader = new StringReader(input);
        JSONTokenizer JSONTokenizer = new JSONTokenizer(reader);

        JSONToken tok;
        while((tok = JSONTokenizer.nextToken()) != JSONToken.EOF) {
            result.add(tok);
        }
        result.add(tok);

        return Collections.unmodifiableList(result);
    }

    public JSONToken nextToken() {

        removeWhiteSpace();

        int ch = next();

        switch (ch) {
            case -1 : return JSONToken.EOF;
            case '[': return JSONToken.ARRAY_START;
            case ']': return JSONToken.ARRAY_END;
            case '{': return JSONToken.OBJECT_START;
            case '}': return JSONToken.OBJECT_END;
            case ',': return JSONToken.COMMA;
            case ':': return JSONToken.COLON;
            case '"': readBack(ch); return expectString();
            case 't': readBack(ch); return expectToken("true", JSONToken.TRUE);
            case 'f': readBack(ch); return expectToken("false", JSONToken.FALSE);
            case 'n': readBack(ch); return expectToken("null", JSONToken.NULL);
            case '-':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': readBack(ch); return expectNumber();
            default: throw new JSONToken.IllegalTokenException("Unexpected Symbol Error");
        }


    }

    // remove the space
    private void removeWhiteSpace() {
        int ch;
        while (true) {
            ch = next();
            switch (ch) {
                case ' ': continue;
                case '\t': continue;
                case '\f': continue;
                case '\n': continue;
                default:
                    readBack(ch);
                    return;
            }
        }
    }

    // read the next character in the JSON file
    public int next() {
        try {
            return reader.read();
        } catch(IOException e) {
            throw new JSONToken.IllegalTokenException("Unexpected read error");
        }
    }

    // go back a single character and copy it to the front of the pushback buffer.
    public void readBack(int value) {
        if (value == -1) return;
        try {
            reader.unread(value);
        } catch(IOException e) {
            throw new JSONToken.IllegalTokenException("Unexpected unread error");
        }
    }

    // return a newly created Token of the string provided
    public JSONToken expectString() {
        StringBuilder aString = new StringBuilder();

        boolean done = false;
        int ch = next();

        while(true) {
            ch = next();

            if (ch == '"') {
                done = true;
            }

            if(done)
                break;
            else {
                aString.append((char)ch);
            }
        }

        return JSONToken.createStringToken(String.valueOf(aString));
    }

    private JSONToken expectNumber() {
        StringBuilder aString = new StringBuilder();

        boolean done = false;
        int ch;

        while(true) {
            ch = next();

            if (!Character.isDigit((char) ch) && ch != '.' && ch != '-') {
                break;
            }
            else {
                aString.append((char)ch);
            }
        }

        return JSONToken.createNumberToken(Double.valueOf(String.valueOf(aString)));
    }

    private JSONToken expectToken(CharSequence seq, JSONToken returnValue) {
        int i = 0;
        int r = seq.length() - 1;

        while(r > 0) {
            int readValue = next();
            if (readValue == seq.charAt(i)) {
                r--;
                i++;
            } else throw new RuntimeException("Unexpected Symbol error");
        }

        int ch = next();

        return returnValue;
    }

}