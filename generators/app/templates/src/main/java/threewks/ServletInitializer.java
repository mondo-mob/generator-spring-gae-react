package threewks;

import com.google.appengine.api.utils.SystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.contrib.gae.config.helper.ProfileExtractors;
import org.springframework.contrib.gae.config.helper.ProfileResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ServletInitializer extends SpringBootServletInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(ServletInitializer.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        List<String> profiles = new ProfileResolver()
            // Set a profile that allows us to identify all gae environments
            .setAdditionalProfileExtractor(ProfileExtractors.staticValue("gae"))
            // Allow us to reference "dev", "uat" without full app id
            .setAdditionalProfileExtractor(ProfileExtractors.AFTER_LAST_DASH)
            .getProfiles();
        LOG.info("Setting profiles: {}", profiles);

        return application.sources(Application.class)
            .profiles(profiles.toArray(new String[0]));
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        // This will set to use COOKIE only which prevents spring from adding jsessionid to redirect urls locally
        servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
        if (isProduction()) {
            LOG.info("Setting session cookie config secure flag to true");
            servletContext.getSessionCookieConfig().setSecure(true);
        }
    }

    private boolean isProduction() {
        return Objects.equals(SystemProperty.Environment.Value.Production.name(), SystemProperty.Environment.environment.get());
    }

}
