package threewks.framework.ref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ReferenceDataConfigBuilder {
    private List registeredClasses = new ArrayList<Class<? extends ReferenceData>>();
    private final Map<Class<? extends ReferenceData>, Function<? extends ReferenceData, ReferenceDataDto>> customTransformers = new HashMap<>();

    @SuppressWarnings("unchecked")
    public ReferenceDataConfigBuilder registerClasses(Class<? extends ReferenceData>... referenceDataClasses) {
        registeredClasses.addAll(Arrays.asList(referenceDataClasses));
        return this;
    }

    public <R extends ReferenceData> ReferenceDataConfigBuilder registerCustomTransformer(Class<R> referenceDataClass, Function<R, ReferenceDataDto> transformer) {
        customTransformers.put(referenceDataClass, transformer);
        return this;
    }

    @SuppressWarnings("unchecked")
    public ReferenceDataConfig create() {
        return new ReferenceDataConfig(registeredClasses, customTransformers);
    }
}
