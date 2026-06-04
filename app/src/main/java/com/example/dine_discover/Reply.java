/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Reply {
    String userEmail;
    String reply;

    // Constructor
    public Reply(){
    }

    public Reply(String user, String comment) {
        this.reply = comment;
        this.userEmail = user;
    }

    // Add a reply

    public Map<String, Object> toMap() {
        Map<String, Object> replyMap = new HashMap<>();
        replyMap.put("userEmail", userEmail);
        replyMap.put("reply", reply);
        return replyMap;
    }

    public String getComment() {
        return reply;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setComment(String comment) {
        this.reply = comment;
    }

    public String getUser() {
        return userEmail;
    }


    // Get all replies


    /**
     * Prints out the user comment
     * @return string containing the information of the user's comment
     */
    @Override
    public String toString() {
        return "User " + userEmail +
                " comments: " + reply;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reply comObj = (Reply) o;
        return userEmail.equals(comObj.userEmail) && reply.equals(comObj.reply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reply, userEmail);
    }

}

