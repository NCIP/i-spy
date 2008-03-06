package gov.columbia.c2b2.ispy.util;

 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

/**
 * Handles the sending of email.
 */
public class SMTPMailer {

    public static final String SMTP_HOST_PROPERTY = "mail.smtp.host";
    public static final String SMTP_PORT_PROPERTY = "mail.smtp.port";

    public static final String MIME_PLAIN = "text/plain";
    public static final String MIME_HTML = "text/html";
    public static final String PART_TYPE_ALTERNATIVE = "alternative";

    Log log = LogFactory.getLog(this.getClass());

    private Session session;
    private String recipientOverrideAddress;
    private String emailFromSystem;
    private boolean testOnly = false;

    public SMTPMailer() {
        Properties mailProperties = new Properties();
        String smtpHost = Config.getConfig().getSmtpHost();
        if ((smtpHost != null) && (smtpHost.length() > 0)) {
            mailProperties.setProperty(SMTP_HOST_PROPERTY, smtpHost);
            String port = Config.getConfig().getSmtpPort();
            if (!Util.isEmpty(port)) {
                mailProperties.setProperty(SMTP_PORT_PROPERTY, port);
            }
            log.info("Create mail service with SMTP server at: " + smtpHost + ".");
            session = Session.getInstance(mailProperties);
            recipientOverrideAddress = Config.getConfig().getEmailOverrideAddress();
            if (recipientOverrideAddress != null) {
                log.info("Note: all mail will be delivered to override recipient: " + recipientOverrideAddress + ".");
            }
            emailFromSystem = Config.getConfig().getEmailFromSystem();
        } else {
            testOnly = true;
            log.info("No SMTP Server specified. Creating a test mail service only. No actual emails will be sent.");
        }
    }

    /**
     * Sends an email.
     *
     * @param subject  the subject line of the email.
     * @param body     the text-only body of the email.
     * @param htmlBody the html-formatted body of the email.
     * @param from     the email address of the sender.
     * @param to       the email address of the recipient.
     * @throws Exception
     */
    public void sendEmail(String subject,
                          String body,
                          String htmlBody,
                          String from,
                          String to) throws Exception {
        sendEmail(subject, body, htmlBody, from, to, null);
    }

    /**
     * Sends an email.
     *
     * @param subject  the subject line of the email.
     * @param body     the text-only body of the email.
     * @param htmlBody the html-formatted body of the email.
     * @param from     the email address of the sender.
     * @param to       the email address of the recipient.
     * @param bcc      the email address to be BCCed. Can be null.
     * @throws Exception
     */
    public void sendEmail(String subject,
                          String body,
                          String htmlBody,
                          String from,
                          String to,
                          String bcc)
            throws Exception {
        if (from == null) {
            from = Config.getConfig().getEmailFromSystem();
        }

        log.info("Sending an email from '" + from + "' to '" + to + "' regarding '" + subject + "'.");

        MimeMessage mail = new MimeMessage(session);
        mail.setSubject(subject);
        // Override recipient, if necessary
        if (recipientOverrideAddress != null) {
            to = recipientOverrideAddress;
        }
        InternetAddress fromAddress = new InternetAddress(from);
        InternetAddress toAddress = new InternetAddress(to);
        mail.setFrom(fromAddress);
        mail.setRecipient(Message.RecipientType.TO, toAddress);
        if (bcc != null) {
            log.debug("BCCing: " + bcc);
            InternetAddress bccAddress = new InternetAddress(bcc);
            mail.addRecipient(Message.RecipientType.CC, bccAddress);
        }
        if (htmlBody == null) {
            if (body != null) {
                mail.setContent(body, MIME_PLAIN);
            }
        } else {
            // Set up as a multipart
            // Multi-part alternative message
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setContent(body, MIME_PLAIN);
            MimeBodyPart mbp2 = new MimeBodyPart();
            mbp2.setContent(htmlBody, MIME_HTML);
            mbp2.setDataHandler(new DataHandler(new ByteArrayDataSource(htmlBody, MIME_HTML)));
            MimeMultipart mp = new MimeMultipart();
            mp.setSubType(PART_TYPE_ALTERNATIVE);
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);
            mail.setContent(mp);
        }
        if (!testOnly) {
            Transport.send(mail);
            log.info("Email sent.");
        } else {
            log.info("Test mail service -- no email sent.");
        }
    }

    public static void main(String[] args) {
        try {
            String smtpServer = args[0];
            String subject = args[1];
            String plainFile = args[2];
            String htmlFile = args[3];
            String from = args[4];
            String to = args[5];
            BufferedReader in = new BufferedReader(new FileReader(plainFile));
            String text = "";
            String line = in.readLine();
            while (line != null) {
                text = text + line + '\n';
                line = in.readLine();
            }
            in.close();
            in = new BufferedReader(new FileReader(htmlFile));
            String html = "";
            line = in.readLine();
            while (line != null) {
                html = html + line + '\n';
                line = in.readLine();
            }
            in.close();
            SMTPMailer service = new SMTPMailer();
            service.sendEmail(subject, text, html, from, to);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
