package ba.edu.ibu.inventorymanagementwebapp.core.api.mailsender;


public interface MailSender {
    void sendEmail(String to, String subject, String body);
}
