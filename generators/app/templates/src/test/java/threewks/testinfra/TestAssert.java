package threewks.testinfra;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiConsumer;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public interface TestAssert {

    static <ENTITY, DTO> void assertCollectionsMatch(Collection<ENTITY> entities, Collection<DTO> dtos, BiConsumer<ENTITY, DTO> assertFunction) {
        assertThat(dtos, hasSize(entities.size()));
        Iterator<ENTITY> entityIterator = entities.iterator();

        // All entries in same order
        dtos.forEach(dto -> assertFunction.accept(entityIterator.next(), dto));
    }

}
