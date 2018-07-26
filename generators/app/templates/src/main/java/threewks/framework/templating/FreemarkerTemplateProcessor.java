package threewks.framework.templating;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

@Component
public class FreemarkerTemplateProcessor implements TemplateProcesor {
    private final Configuration freemarkerConfig;

    public FreemarkerTemplateProcessor(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public String processTemplateFile(String templateName, Map<String, Object> params) {
        return processTemplate(templateName, params, () -> freemarkerConfig.getTemplate(templateName));
    }

    @Override
    public String processTemplateString(String source, Map<String, Object> params) {
        return processTemplate("from string", params,
            () -> new Template(null, new StringReader(source), freemarkerConfig));

    }

    private String processTemplate(String templateName, Map<String, Object> params, TemplateRetriever templateRetriever) {
        try {
            return FreeMarkerTemplateUtils.processTemplateIntoString(templateRetriever.get(), params);
        } catch (IOException | TemplateException e) {
            throw new TemplateProcessingException("Error processing template: " + templateName, e);
        }
    }

    @FunctionalInterface
    private interface TemplateRetriever {
        Template get() throws IOException;
    }

}
