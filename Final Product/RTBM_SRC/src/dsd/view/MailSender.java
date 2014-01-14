package dsd.view;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailSender {
	
	/*
	public static void sendMail() {
		 
		final String username = "ratke89@gmail.com";
		final String password = "ilija0326_1";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ratke89@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("ratke89@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	*/
	
	

	public static boolean sendMail (final String from, final String password, String message, String to1, String to2){

		String host = "smtp.gmail.com";
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable",  "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.stmp.password", password);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		  });
		MimeMessage mimeMessage = new MimeMessage(session);
		try{
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(to1));
			mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(to2));
			
			
			mimeMessage.setSubject("Bogoforte Bridge in danger");
			
			mimeMessage.setText(message);
			
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, password);
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();
			return true;
		}catch(MessagingException me){
			me.printStackTrace();
		}
		return false;
		
	}  
	
}
