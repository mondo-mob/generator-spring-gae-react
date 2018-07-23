package threewks.testinfra;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.data.geo.GeoModule;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import threewks.controller.dto.SimpleRequest;
import threewks.framework.controller.advice.ExceptionHandlerAdvice;
import threewks.testinfra.rules.LocalServicesRule;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Test controller via {@link MockMvc} without requiring a full container load - faster. If you want to test
 * Spring Security, use {@link BaseControllerIntegrationTest} which sets up web application context.
 */
public abstract class BaseControllerTest extends BaseTest {

    @Rule
    public LocalServicesRule localServicesRule = new LocalServicesRule();

    protected MockMvc mvc;

    protected abstract Object controller();

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        // Try to set this up the same as we have it in Spring
        objectMapper = new ObjectMapper()
            .registerModules(
                new JavaTimeModule(),
                new JsonComponentModule(),
                new GeoModule(),
                new JodaModule()
            )
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        mvc = MockMvcBuilders
            .standaloneSetup(controller())
            .setMessageConverters(converter)
            .setControllerAdvice(new ExceptionHandlerAdvice())
            .alwaysDo(print())
            .build();
    }

    protected String simpleRequest(Object object) {
        return asString(new SimpleRequest<>(object));
    }

    protected String asString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Exception converting object to string for test", e);
        }
    }

    protected MockMultipartFile file(String name, String path) {
        try {
            return new MockMultipartFile(name, getClass().getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + path, e);
        }
    }

}
