package threewks.framework.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import threewks.framework.templating.TemplateProcesor;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    private final TemplateProcesor templateProcesor;
    private final JavaMailSender mailSender;
    private final String fromAddress;

    public EmailService(TemplateProcesor templateProcesor, JavaMailSender mailSender, @Value("${mailSender.fromAddress}") String fromAddress) {
        this.templateProcesor = templateProcesor;
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
    }

    public void send(String type, String to, Map<String, Object> params) {
        EmailBuilder emailBuilder = EmailBuilder.email(to, (String) params.get("subject"), type, params);
        send(emailBuilder.createEmail());
    }

    public void send(Email email) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            String text = templateProcesor.processTemplateFile(email.getTemplate(), email.getTemplateParams());

            MimeMessageHelper helper = new MimeMessageHelper(message, email.isMultipart());
            helper.setTo(email.getTo());
            helper.setText(text, true);
            helper.setSubject(email.getSubject());
            helper.setFrom(fromAddress);

            for (MailAttachment attachment : email.getAttachments()) {
                helper.addAttachment(attachment.getName(), attachment.getInputStreamSource());
            }

            LOG.info("Sending email to: " + email.getTo());
            mailSender.send(message);
            LOG.info("Email sent");
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email", e);
        }
    }
}
