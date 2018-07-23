package threewks.framework.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CspBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(CspBuilder.class);
    public static final String CSP_SELF = "'self'";
    public static final String CSP_UNSAFE_INLINE = "'unsafe-inline'";

    private final Map<String, Set<String>> directives = new LinkedHashMap<>();

    public CspBuilder add(String directive, String... entries) {
        return addIfTrue(directive, true, entries);
    }

    public CspBuilder addIfTrue(String directive, boolean condition,  String... entries) {
        if (condition) {
            directives.computeIfAbsent(directive, k -> new LinkedHashSet<>())
                .addAll(Arrays.asList(entries));
        }
        return this;
    }

    public String build() {
        String headerVal = directives.entrySet().stream()
            .map(e -> e.getValue().stream()
                .collect(Collectors.joining(" ", e.getKey() + " ", "")))
            .collect(Collectors.joining("; "));
        LOG.info("Generated CSP Header: {}", headerVal);
        return headerVal;
    }

}
