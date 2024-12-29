package ba.edu.ibu.inventorymanagementwebapp.core.service;

import ba.edu.ibu.inventorymanagementwebapp.core.api.mailsender.MailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final MailSender mailSender;

    public EmailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String body) {
        mailSender.sendEmail(to, subject, body);
    }
}
