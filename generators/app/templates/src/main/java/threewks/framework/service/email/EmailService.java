package threewks.framework.service.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

@Service
public class EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    private Configuration freemarkerConfig;
    private JavaMailSender mailSender;
    private String fromAddress;

    public EmailService(Configuration freemarkerConfig, JavaMailSender mailSender, @Value("${mailSender.fromAddress}") String fromAddress) {
        this.freemarkerConfig = freemarkerConfig;
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
            MimeMessageHelper helper = new MimeMessageHelper(message, email.isMultipart());

            try {
                Template t = freemarkerConfig.getTemplate(email.getTemplate());
                String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, email.getTemplateParams());

                helper.setTo(email.getTo());
                helper.setText(text, true);
                helper.setSubject(email.getSubject());
                helper.setFrom(fromAddress);
            } catch (IOException | TemplateException e) {
                throw new RuntimeException("Error processing email template", e);
            }

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
