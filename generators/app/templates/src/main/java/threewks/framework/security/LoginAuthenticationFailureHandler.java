package threewks.framework.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import threewks.framework.controller.advice.ResponseError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticationFailureHandler to return JSON error for login failure.
 */
public class LoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private ObjectMapper objectMapper;

    public LoginAuthenticationFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // By default returns generic error message. Customise as required - e.g. based on exception thrown.
        ResponseError responseError = new ResponseError("Login failure", "Invalid login");
        objectMapper.writeValue(response.getWriter(), responseError);
    }
}
