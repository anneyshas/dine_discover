/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
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

public class SendEmail extends AsyncTask<Void, Void, Void> {

        private List<String> emails;
        private String emailBody;
        private Context context;

        public SendEmail(List<String> emails, String emailBody, Context context) {
            this.emails = emails;
            this.emailBody = emailBody;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            final String username = "DineDiscoverApp@gmail.com";
            final String password = "dinediscover123";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                for (String email : emails) {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));
                    message.setSubject("You have received a new reply in Dine Discover app!");
                    message.setText(emailBody);
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                    Transport.send(message);
                    }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }


    }
