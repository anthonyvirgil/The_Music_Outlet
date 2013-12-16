// Venelin Koulaxazov
// 1032744
// EmailSender.java
package util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Venelin Koulaxazov
 * @version 1.1
 */
public class EmailSender {

	// smtp port 25
	// pop port 110
	private final static String SENDER = "g01w13@waldo2.dawsoncollege.qc.ca";

	/**
	 * Sends an email using the smtp server
	 * 
	 * @param receiver
	 * @param subject
	 * @param message
	 * @return whether the message was sent
	 */
	public boolean sendMail(String receiver, String subject, String message) {
		boolean successfulSend = true;
		Session session = null;

		try {
			// Create a properties object
			Properties smtpProps = new Properties();

			// Add mail configuration to the properties
			smtpProps.put("mail.transport.protocol", "smtp");

			smtpProps.put("mail.smtp.host", "waldo2.dawsoncollege.qc.ca");
			smtpProps.put("mail.smtp.port", 25);

			smtpProps.put("mail.smtp.auth", "true");
			Authenticator auth = new SMTPAuthenticator();
			session = Session.getInstance(smtpProps, auth);

			// Create a new message
			MimeMessage msg = new MimeMessage(session);

			// Set the single from field
			msg.setFrom(new InternetAddress(SENDER));

			// Set the To, CC, and BCC from their ArrayLists
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					receiver, false));

			// Set the subject
			msg.setSubject(subject);

			// Set the message body
			msg.setText(message);

			// Set some other header information
			msg.setSentDate(new Date());

			Transport transport = session.getTransport();

			transport.connect();
			transport.sendMessage(msg,
					msg.getRecipients(Message.RecipientType.TO));
			transport.close();

		} catch (NoSuchProviderException e) {
			successfulSend = false;
			e.printStackTrace();
		} catch (AddressException e) {
			successfulSend = false;
			e.printStackTrace();
		} catch (MessagingException e) {
			successfulSend = false;
			e.printStackTrace();
		} catch (Exception e) {
			successfulSend = false;
			e.printStackTrace();
		}
		return successfulSend;
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = "g01w13";
			String password = "mars8cake";
			return new PasswordAuthentication(username, password);
		}
	}
}
