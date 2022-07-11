/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author smileymask
 */
// File Name SendEmail.java

import java.util.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;


// pchfjkvrjrqcvyzx

public class SendEmail {
    public String status;
    private String fromEmail;

    public SendEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }
    
    
    
    public boolean sendMail(String toemail, String subject,String user ,String message) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.googlemail.com");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        boolean test = false;
        try {
            final String fromemail = fromEmail;
            final String password = "pchfjkvrjrqcvyzx";
            Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                @Override
                protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new jakarta.mail.PasswordAuthentication(fromemail, password);
                }
            });

            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromemail));
            InternetAddress[] toAddresses = {new InternetAddress(toemail)};
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);

            String htmlContent = " <h3> Hi " + user + " ! </h3>\n"
                    + "<h2>Your OTP code is:<mark style='color:blue;'>"+ message + "</mark></h4>";
            
            msg.setContent(htmlContent, "text/html");
            Transport.send(msg);
            test = true;
            status="Send Email Succsess";
            return test;
        } catch (Exception e) {
            status=e.getMessage();
        }
        return test;

    }

//    public static void main(String[] args) throws MessagingException {
//       
//        SendEmail se = new SendEmail();
//        boolean test = se.sendMail("manhpdhe150883@fpt.edu.vn", "TOKEN FOR RESETPASSWORD","check15", "1508");
//        System.out.println(test);
//    }
    
}