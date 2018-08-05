package com.codesmack.jpa.entity.callback;

/**
 * Created by Aniruddha on 05-08-2018.
 */
public class EmailSender {
    private static String emailContent;
    public static void sendEmail(final String emailContent) {
        EmailSender.emailContent = emailContent;
    }
    public static String getEmailContent() {
        return emailContent;
    }
}
