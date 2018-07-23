package threewks.testinfra.rules;

import org.junit.rules.ExternalResource;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextReset extends ExternalResource {

    @Override
    protected void after() {
        SecurityContextHolder.clearContext();
    }

}
