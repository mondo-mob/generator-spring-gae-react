package threewks.testinfra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import threewks.framework.usermanagement.Role;
import threewks.framework.usermanagement.dto.AuthUser;
import threewks.testinfra.rules.LocalServicesRule;

import java.io.IOException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Use this base test when you specifically want to test integration with spring security features. If you want to test
 * a standalone controller using {@link MockMvc} then extend {@link BaseControllerTest}.
 */
@ActiveProfiles("junit")
@SpringBootTest
public abstract class BaseControllerIntegrationTest {

    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();
    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();
    @Rule
    public LocalServicesRule localServicesRule = new LocalServicesRule();

    protected MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(wac)
            .apply(springSecurity())
            .alwaysDo(print())
            .build();
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

    protected RequestPostProcessor roles(Role... roles) {
        return user(authUser(roles));
    }

    protected AuthUser authUser(Role... roles) {
        return TestData.authUser(roles);
    }

}
