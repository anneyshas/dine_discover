/**
 * Author: Vivian Tran
 */
/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dine_discover.SearchTreeFolder.SearchTree;
import com.example.dine_discover.SearchTreeFolder.SearchTreeManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
/**
 * <p>
 * CommentsAdapter implements the adapter design pattern </p>
 *
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private List<Review> commentsList;
    private Context context;
    Restaurant restaurant;

    SearchTree tree;

    public CommentsAdapter(Context context, List<Review> commentsList, Restaurant restaurant) {
        this.context = context;
        this.commentsList = commentsList;
        this.restaurant = restaurant;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_reply, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, int position) {
        final Review comment = commentsList.get(position);
        holder.commentText.setText(comment.getComment());

        // Handle the replies RecyclerView
        holder.viewMoreReplies.setText(comment.areRepliesVisible() ? "Hide replies" : "View more replies");

        // Show/hide replies based on the comment's visibility state
        if (comment.areRepliesVisible()) {
            holder.repliesLayout.setVisibility(View.VISIBLE);
            // Add replies
            holder.repliesLayout.removeAllViews();
            List<Reply> replyList = comment.getReplies();
            for (Reply replyObj : replyList) {
                String reply = replyObj.getComment();
                TextView replyTextView = new TextView(context);
                replyTextView.setText(reply);
                replyTextView.setTextSize(14);
                replyTextView.setPadding(16, 8, 0, 8);
                holder.repliesLayout.addView(replyTextView);
            }
            holder.repliesLayout.addView(holder.editText);
            holder.repliesLayout.addView(holder.button);


        } else {
            holder.repliesLayout.setVisibility(View.GONE);
            holder.editText.setVisibility(View.GONE);  // Hide EditText
            holder.button.setVisibility(View.GONE);
        }

        // Toggle replies visibility when "View more replies" is clicked
        holder.viewMoreReplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment.areRepliesVisible()) {
                    comment.setRepliesVisible(false);
                    holder.repliesLayout.setVisibility(View.GONE);
                    holder.editText.setVisibility(View.GONE);
                    holder.button.setVisibility(View.GONE);
                    holder.viewMoreReplies.setText("View more replies");
                } else {
                    comment.setRepliesVisible(true);
                    holder.repliesLayout.setVisibility(View.VISIBLE);
                    holder.editText.setVisibility(View.VISIBLE);
                    holder.button.setVisibility(View.VISIBLE);
                    holder.viewMoreReplies.setText("Hide replies");
                }
//                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String replyText = holder.editText.getText().toString();
                if (!replyText.isEmpty()) {
                    User user = new User(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    Reply newReply =new Reply(user.getUsername(),replyText);
                    comment.getReplies().add(newReply);
                    notifyItemChanged(holder.getAdapterPosition());  // Refresh the item
                    holder.editText.setText("");
                    sendEmail(comment,  newReply);

                    // instantiate
                    tree = SearchTreeManager.getSearchTree();
                    tree.resetTreesNewRestaurant(restaurant);
                }
            }
        });

    }

    private TextView createReplyTextView(String replyText) {
        TextView replyTextView = new TextView(context);  // Create a new TextView
        replyTextView.setText(replyText);  // Set the reply text
        replyTextView.setTextSize(14);  // Set a reasonable text size for replies
        replyTextView.setPadding(16, 8, 0, 8);  // Add some padding to the reply TextView
        return replyTextView;
    }

    private void sendEmail(Review review, Reply newReply) {
        ArrayList<String> emails = new ArrayList<>();
        String reviewerEmail = review.getUser();
        emails.add(reviewerEmail);
        String emailbody = newReply.getUser() + " has sent a new reply: \"" + newReply.getComment() + "\"";

        // send email to the reviewer
        User user = new User(review.getUser());
        review.notifyUser(user, newReply);

        // send email to the repliers
        List<Reply> replies = review.getReplies();
        for (Reply reply : replies) {
            User replyUser = new User(reply.getUser(), "pass");
            review.notifyUser(replyUser, newReply);
            emails.add(reply.getUser());
        }
        new SendEmail(emails, emailbody, context).execute();
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    // ViewHolder for each comment item
    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView commentText;
        public TextView viewMoreReplies;
        public LinearLayout repliesLayout;

        public Button button;
        public EditText editText;
        public TextView reply;
        public Button makeCommentBtn;
        public ImageView backButton;

        public CommentViewHolder(View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.comment_text);
            viewMoreReplies = itemView.findViewById(R.id.view_more_replies);
            repliesLayout = itemView.findViewById(R.id.replies_layout);
            button = itemView.findViewById(R.id.reply_button);
            editText = itemView.findViewById(R.id.reply_edit_text);
            reply = itemView.findViewById(R.id.reply_text);
            //makeCommentBtn = itemView.findViewById(R.id.makeCommentButton);
            //TODO
            //backButton= (ImageView) itemView.findViewById(R.id.commentBackButton);



        }
    }
}
