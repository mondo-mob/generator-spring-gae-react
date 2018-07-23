package threewks.framework.opencsv;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import threewks.framework.opencsv.IntegerConverter.DefaultZero;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class IntegerConverterTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private IntegerConverter converter;

    @Before
    public void before() {
        converter = new IntegerConverter();
    }

    @Test
    public void convert() throws CsvDataTypeMismatchException {
        assertThat(converter.convert("100000000"), is(100000000));
        assertThat(converter.convert("0"), is(0));
        assertThat(converter.convert("-100000000"), is(-100000000));
    }

    @Test
    public void convert_willReturnDefault_whenNull() throws CsvDataTypeMismatchException {
        assertThat(converter.convert(null), nullValue());
    }

    @Test
    public void convert_willError_whenInvalidNumber() throws CsvDataTypeMismatchException {
        thrown.expect(CsvDataTypeMismatchException.class);
        thrown.expectMessage("Invalid integer value: 1.234");

        converter.convert("1.234");
    }

    @Test
    public void defaultZero_convert_willReturnDefaultZero_whenNull() throws CsvDataTypeMismatchException {
        assertThat(new DefaultZero().convert(null), is(0));
    }

}
