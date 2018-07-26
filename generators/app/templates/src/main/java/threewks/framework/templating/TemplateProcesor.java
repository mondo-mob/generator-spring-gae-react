package threewks.framework.templating;

import java.util.Map;

public interface TemplateProcesor {

    String processTemplateFile(String templateName, Map<String, Object> params);

    String processTemplateString(String source, Map<String, Object> params);

}
