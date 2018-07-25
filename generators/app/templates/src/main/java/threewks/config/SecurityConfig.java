package threewks.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.contrib.gae.security.config.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import threewks.framework.security.CspBuilder;
import threewks.framework.security.LoginAuthenticationFailureHandler;
import threewks.framework.usermanagement.model.User;
import threewks.framework.usermanagement.model.UserAdapterGae;
import threewks.framework.usermanagement.service.UserService;

import static org.springframework.http.HttpMethod.GET;
import static threewks.framework.security.CspBuilder.CSP_SELF;
import static threewks.framework.security.CspBuilder.CSP_UNSAFE_INLINE;

@Configuration
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProvider daoAuthenticationProvider;
    @Autowired
    private RememberMeServices rememberMeServices;
    @Autowired
    private AuthenticationSuccessHandler restAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler loginAuthenticationFailureHandler;
    @Autowired
    private LogoutSuccessHandler restLogoutSuccessHandler;
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf()
                .ignoringAntMatchers("/system/**", "/task/**", "/cron/**", "/_ah/**")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
                .exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), new AntPathRequestMatcher("/api/**"))
            .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices)
                .key(securityProperties.getRememberMe().getKey())
            .and()
                .formLogin()
                .loginPage("/login") // Stop spring from generating its own login page
                .loginProcessingUrl("/api/login")
                .successHandler(restAuthenticationSuccessHandler)
                .failureHandler(loginAuthenticationFailureHandler)
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(restLogoutSuccessHandler)
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers("/_ah/**", "/system/**", "/task/**", "/cron/**").permitAll()  // protected by security-constraint in web.xml which delegates to GCP's IAM
                .antMatchers("/api/login", "/api/users/me", "/api/users/invite/*", "/api/error/**").permitAll()
                .antMatchers(GET, "/api/reference-data").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/**").permitAll()
            .and()
                .headers()
                .contentSecurityPolicy(new CspBuilder()
                    .add("default-src", CSP_SELF)
                    .add("script-src", CSP_SELF, "https://cdn.polyfill.io")
                    .add("img-src", CSP_SELF, "https:", "data:")
                    .add("frame-src", CSP_SELF, "https://calendar.google.com")
                    .add("style-src", CSP_SELF, CSP_UNSAFE_INLINE, "https://fonts.googleapis.com", "blob:")
                    .add("font-src", CSP_SELF, "https://fonts.gstatic.com")
                    .build());
        // @formatter:on
    }

    @Bean
    public AuthenticationFailureHandler loginAuthenticationFailureHandler(ObjectMapper objectMapper) {
        return new LoginAuthenticationFailureHandler(objectMapper);
    }

    @Bean
    public Class<User> gaeUserClass() {
        return User.class;
    }

    @Bean
    public UserAdapterGae gaeUserHelper(UserService userService) {
        return UserAdapterGae.byEmail(userService);
    }
}
