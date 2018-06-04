package threewks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import threewks.framework.ref.ReferenceDataConfigBuilder;
import threewks.framework.ref.ReferenceDataService;
import threewks.framework.usermanagement.Role;

@Configuration
public class ReferenceDataConfig {

    @SuppressWarnings("unchecked")
    @Bean
    public ReferenceDataService referenceDataService() {
        ReferenceDataConfigBuilder configBuilder = new ReferenceDataConfigBuilder()
            .registerClasses(Role.class);

        return new ReferenceDataService(configBuilder.create());
    }
}
