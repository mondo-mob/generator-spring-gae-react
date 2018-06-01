package threewks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {QuartzAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class Application {
    /**
     * BEWARE - this is not used unless you start spring from command-line. See {@link ServletInitializer} instead.
     */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
