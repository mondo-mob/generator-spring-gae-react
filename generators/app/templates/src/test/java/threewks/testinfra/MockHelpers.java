package threewks.testinfra;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.contrib.gae.objectify.Refs;
import org.springframework.contrib.gae.objectify.repository.SaveRepository;
import org.springframework.data.repository.Repository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class MockHelpers {

    /**
     * Mocks the {@link Repository} behaviour of returning the same entity when a {@link SaveRepository#save(Object)}
     * method is called and also returning the same {@link Collection <T>} when a list is put.
     */
    @SuppressWarnings("unchecked")
    public static <T> void mockSave(SaveRepository<T, ?> repository, Class<T> entityType) {
        returnFirstArgument(repository.save(any(entityType)));
        returnFirstArgument(repository.save(any(Collection.class)));
    }

    /**
     * Convenience for returning the first argument of any method call on a mock.
     * Example usage
     * <code>
     *     returnFirstArgument(myMethod(param1));
     *     returnFirstArgument(myMethod(any(String.class));
     * </code>
     */
    public static <T> void returnFirstArgument(T methodCall) {
        returnArgument(methodCall, 0);
    }

    public static <T> void returnArgument(T methodCall, int argumentIndex) {
        when(methodCall).thenAnswer(invocation -> invocation.getArguments()[argumentIndex]);
    }

    /**
     * Create a {@link Ref <T>} spy internally using {@link #refSpy(Object)} and set the specified fieldName with
     * this ref on the target entity.
     *
     * @param entity Entity that requires a ref
     * @param fieldName Internal field name of the entity to store the ref on
     * @param expectedResult Expected result of {@link Ref#get()}
     * @param <E> Entity type
     * @param <T> Ref type
     *
     * @return Entity instance (entity is mutated, but instance is returned for convenience/chaining).
     */
    public static <E, T> E setRef(E entity, String fieldName, T expectedResult) {
        ReflectionTestUtils.setField(entity, fieldName, refSpy(expectedResult));
        return entity;
    }

    public static <E> String getRefName(E entity, String fieldName) {
        Ref ref = (Ref) ReflectionTestUtils.getField(entity, fieldName);
        Key key = Refs.key(ref);
        return key == null ? null : key.getName();
    }

    /**
     * Create a {@link Ref<T>} and spy on the object so that it always returns the expectedResult
     * @param expectedResult Expected result from {@link Ref#get()}.
     * @param <T> Type of expectedResult
     * @return {@link Ref<T>} instance.
     */
    public static <T> Ref<T> refSpy(T expectedResult) {
        Ref<T> ref = spy(Ref.create(expectedResult));
        when(ref.get()).thenReturn(expectedResult);
        return ref;
    }

}
