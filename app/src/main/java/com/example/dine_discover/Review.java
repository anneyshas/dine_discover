/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Review implements Serializable {
    String comment;
    String userEmail;
    int rating;
    int time_stamp;
    List<Reply> replies;
    boolean visibleReplies;

    public Review() {
        replies= new ArrayList<Reply>();
    }

    public Review(String user, int rating, String comment,List<Reply> replies ){
        this.comment = comment;
        this.userEmail = user;
        this.rating = rating;
        this.replies = replies;
        this.visibleReplies = false;
    }
    public void add(Reply reply) {
        this.replies.add(reply);
        //notifyOtherCommenters(reply);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> reviewMap = new HashMap<>();
        reviewMap.put("comment", comment);
        reviewMap.put("userEmail", userEmail);
        reviewMap.put("rating", rating);
        reviewMap.put("time_stamp", time_stamp);
        reviewMap.put("visibleReplies", visibleReplies);
        if (replies != null && !replies.isEmpty()) {
            List<Map<String, Object>> repliesMapList = new ArrayList<>();
            for (Reply reply : replies) {
                repliesMapList.add(reply.toMap());
            }
            reviewMap.put("replies", repliesMapList);
        }

        return reviewMap;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTime_stamp(int time_stamp) {
        this.time_stamp = time_stamp;
    }
    public List<Reply> getReplies() {
        return replies;
    }

    public String getUser() {
        return userEmail;
    }

    public void setUser(String userEmail) {
        this.userEmail = userEmail;
    }

    //TODO SORRY VIVIAN it just can't use the User class
//    // notify everyone involved in the comment thread
//    public void notifyOtherCommenters(Reply newReply){
//        // updates the Head commenter
//        notifyUser(this.getUser(), newReply);
//
//        // updates other users who commented on Head comment
//        List<Reply> replies = this.getReplies();
//        for (Reply reply: replies) {
//            notifyUser(reply.getUser(), newReply);
//        }
//    }
//
//    // method that sends a comment to the user who is involved in the same comment thread

        public void notifyUser(User user, Reply reply){
        user.update("Hi "+ user.getUsername()+ ", " + "you have received a new reply! " +reply.toString());
    }

    public int getRating() {return rating;}
    public int getTime_stamp() {return time_stamp;}
    public String getComment() {return comment;}
    public boolean areRepliesVisible(){
        return visibleReplies;
    }
    public void setRepliesVisible(boolean bool){
        this.visibleReplies= bool;
    }

    /**
     *
     }
     * Posting the comments for the restaurant
     * @return
     */
    public void post(Place place, Review review) {
        Scanner obj = new Scanner(System.in);  // Create a Scanner object
        String comment = obj.nextLine();  // Read user input
        System.out.println(comment);
    }
}
