package threewks.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;

@Configuration
public class TemplateConfig {

    @Bean
    public SpringTemplateEngine templateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(emailTemplateResolver());
        templateEngine.addTemplateResolver(pdfTemplateResolver());
        templateEngine.addTemplateResolver(viewTemplateResolver());
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver emailTemplateResolver() {
        return htmlResolver("classpath:/email-templates/");
    }

    @Bean
    public SpringResourceTemplateResolver pdfTemplateResolver() {
        return htmlResolver("classpath:/pdf-templates/");
    }

    @Bean
    public SpringResourceTemplateResolver viewTemplateResolver() {
        return htmlResolver("/WEB-INF/th/");
    }

    private SpringResourceTemplateResolver htmlResolver(String prefix) {
        final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix(prefix);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCheckExistence(true);
        return templateResolver;
    }


}
