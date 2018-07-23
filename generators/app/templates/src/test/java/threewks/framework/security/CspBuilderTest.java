package threewks.framework.security;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static threewks.framework.security.CspBuilder.CSP_SELF;
import static threewks.framework.security.CspBuilder.CSP_UNSAFE_INLINE;

public class CspBuilderTest {

    @Test
    public void add_addIfTrue_build() {
        String result = new CspBuilder()
            .add("default-src", CSP_SELF)
            .add("script-src", CSP_SELF, "https://cdn.polyfill.io")
            .addIfTrue("script-src", true, CSP_UNSAFE_INLINE, "https://code.getmdl.io")
            .addIfTrue("script-src", false, "https://www.google.com")
            .add("font-src", CSP_SELF, "https://fonts.gstatic.com")
            .build();

        assertThat(result, is("default-src 'self'; script-src 'self' https://cdn.polyfill.io 'unsafe-inline' https://code.getmdl.io; font-src 'self' https://fonts.gstatic.com"));
    }

}
