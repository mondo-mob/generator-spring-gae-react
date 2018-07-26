package threewks.framework.templating;

import com.google.common.collect.ImmutableMap;
import freemarker.template.TemplateException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FreemarkerTemplateProcessorTest {
    private FreemarkerTemplateProcessor processor;

    @Before
    public void before() throws IOException, TemplateException {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPaths(
            "classpath:/test-templates/"
        );
        freeMarkerConfigurer.afterPropertiesSet();
        processor = new FreemarkerTemplateProcessor(freeMarkerConfigurer.getConfiguration());
    }

    @Test
    public void processTemplateFile() {
        String result = processor.processTemplateFile("test.ftl", ImmutableMap.of("to", "Joe", "from", "Fred"));

        assertThat(result.trim(), is("Hi Joe, From Fred"));
    }

    @Test
    public void processTemplateString() {
        String result = processor.processTemplateString("Hi ${to}, From ${from}", ImmutableMap.of("to", "Joe", "from", "Fred"));
        assertThat(result, is("Hi Joe, From Fred"));
    }

}
