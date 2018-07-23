package threewks;

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
            .profiles(profiles.toArray(new String[profiles.size()]));
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        // This will set to use COOKIE only which prevents spring from adding jsessionid to redirect urls locally
        servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
    }
}
