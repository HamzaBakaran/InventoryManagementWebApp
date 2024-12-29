package ba.edu.ibu.inventorymanagementwebapp.api.impl;

import ba.edu.ibu.inventorymanagementwebapp.core.api.mailsender.MailSender;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailgunEmailSender implements MailSender {

    @Value("${email.mailgun.domain}")
    private String mailgunDomain;

    @Value("${email.mailgun.from-email}")
    private String fromEmail;

    @Value("${email.mailgun.username}")
    private String apiKeyUsername;

    @Value("${email.mailgun.password}")
    private String apiKey;

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            HttpResponse<JsonNode> request = Unirest.post(mailgunDomain + "/messages")
                    .basicAuth(apiKeyUsername, apiKey)
                    .queryString("from", fromEmail)
                    .queryString("to", to)
                    .queryString("subject", subject)
                    .queryString("text", body)
                    .asJson();

            if (request.getStatus() >= 200 && request.getStatus() < 300) {
                System.out.println("Email sent successfully.");
            } else {
                throw new RuntimeException("Failed to send email: " + request.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error sending email: " + e.getMessage(), e);
        }
    }
}

