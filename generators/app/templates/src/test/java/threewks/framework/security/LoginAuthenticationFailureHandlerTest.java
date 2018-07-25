package threewks.framework.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LoginAuthenticationFailureHandlerTest {

    private HttpServletRequest request;

    private MockHttpServletResponse response;

    @Mock
    private AuthenticationException exception;

    private LoginAuthenticationFailureHandler handler;

    @Before
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        handler = new LoginAuthenticationFailureHandler(objectMapper);
    }

    @Test
    public void onAuthenticationFailure() throws Exception {
        handler.onAuthenticationFailure(request, response, exception);

        assertThat(response.getStatus(), is(401));
        assertThat(response.getContentType(), is("application/json"));
        Map<String, Object> json = parseJson(response.getContentAsString());
        assertThat(json.get("message"), is("Invalid login"));
    }

    private Map<String, Object> parseJson(String json) throws Exception {
        return new ObjectMapper().readValue(json, new TypeReference<Map<String, Object>>() {
        });
    }
}
