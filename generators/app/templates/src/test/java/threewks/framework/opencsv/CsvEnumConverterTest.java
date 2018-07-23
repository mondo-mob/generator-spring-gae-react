package threewks.framework.opencsv;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class CsvEnumConverterTest {
    private enum TestEnum {ONE, TWO}
    private class TestEnumConverter extends CsvEnumConverter<TestEnum> {
        public TestEnumConverter() {
            super(TestEnum.class);
        }
    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private TestEnumConverter converter;

    @Before
    public void before() {
        converter = new TestEnumConverter();
    }

    @Test
    public void convert() throws CsvDataTypeMismatchException {
        assertThat(converter.convert(null), nullValue());
        assertThat(converter.convert("ONE"), is(TestEnum.ONE));
        assertThat(converter.convert("TWO"), is(TestEnum.TWO));
    }

    @Test
    public void convert_willError_whenInvalidEnum() throws CsvDataTypeMismatchException {
        thrown.expect(CsvDataTypeMismatchException.class);
        thrown.expectMessage("Invalid TestEnum value: erroneous!");

        converter.convert("erroneous!");
    }

}
