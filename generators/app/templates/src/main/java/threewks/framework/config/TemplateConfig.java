package threewks.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
public class TemplateConfig {

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPaths(
            "classpath:/email-templates/",
            "classpath:/pdf-templates/",
            "/WEB-INF/ftl/"
        );
        return freeMarkerConfigurer;
    }

    @Bean
    public FreeMarkerViewResolver freemarkerHtmlViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".html");
        resolver.setContentType("text/html");
        return resolver;
    }
}
