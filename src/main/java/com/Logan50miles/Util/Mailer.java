package com.Logan50miles.Util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class Mailer {
	Properties properties;
    Session session;
    MimeMessage mimeMessage;

    public void sendMail(String EmailSubject, String EmailBody, String ToAddress,String frommail,String password) {
    	String USERNAME = frommail;
    	String PASSWORD =password;
    	String HOSTNAME = "smtp.office365.com";
    	String STARTTLS_PORT = "587";
    	boolean STARTTLS = true;
    	boolean AUTH = true;
    	String FromAddress=frommail;
    	try {
    		properties = new Properties();
    		properties.put("mail.smtp.host", HOSTNAME);
    		properties.put("mail.smtp.port", STARTTLS_PORT);
    		properties.put("mail.smtp.auth", AUTH);
    		properties.put("mail.smtp.starttls.enable", STARTTLS);

    		Authenticator auth = new Authenticator() {
    			protected PasswordAuthentication getPasswordAuthentication() {
    				return new PasswordAuthentication(USERNAME, PASSWORD);
    			}
    		};

    	// creating session
    	session = Session.getInstance(properties, auth);
    	
    	// create mimemessage
    	mimeMessage = new MimeMessage(session);
    	
    	//from address should exist in the domain
    	mimeMessage.setFrom(new InternetAddress(FromAddress));
    	mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(ToAddress));
    	mimeMessage.setSubject(EmailSubject);
    	mimeMessage.setContent(EmailBody,"text/html" );
    	Transport.send(mimeMessage);
    	} 
    	catch (Exception e) {
    		  e.printStackTrace();
    	}                

    }
}
