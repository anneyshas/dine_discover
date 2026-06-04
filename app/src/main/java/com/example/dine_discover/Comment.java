/**
 * Author: Vivian Tran
 */
/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * <p>
 * Comment class is responsible for handling getter and setter
 * methods of comments, as well as fields. </p>
 *
 */
public class Comment {
    String comment;
    User user;
    List<Comment> replies;

    // Constructor
    public Comment(User user, String comment) {
        this.user = user;
        this.comment = comment;
        this.replies = new ArrayList<>();
    }

    public String getComment() {
        return comment;
    }

    // Add a reply
    public void addReply(Comment reply) {
        replies.add(reply);
        notifyOtherCommenters(reply);
    }

    public User getUser() {
        return user;
    }

    // notify everyone involved in the comment thread
    public void notifyOtherCommenters(Comment newReply){
        // updates the Head commenter
        notifyUser(this.getUser(), newReply);

        // updates other users who commented on Head comment
        List<Comment> replies = this.getReplies();
        for (Comment reply: replies) {
            notifyUser(reply.getUser(), newReply);
        }
    }

    // method that sends a comment to the user who is involved in the same comment thread
    public void notifyUser(User user, Comment reply){
        user.update("Hi "+ user.getUsername()+ ", " + "you have received a new comment! " +reply.toString());
    }

    // Get all replies
    public List<Comment> getReplies() {
        return replies;
    }

    /**
     * Prints out the user comment
     * @return string containing the information of the user's comment
     */
    @Override
    public String toString() {
        return "User " + user.getUsername() +
                " comments: " + comment;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comObj = (Comment) o;
        return user.equals(comObj.user) && comment.equals(comObj.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comment, user);
    }

}

