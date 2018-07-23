package threewks.framework.opencsv;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import threewks.framework.opencsv.BigDecimalConverter.DefaultZero;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static threewks.testinfra.matcher.BigDecimalMatchers.bigDecimalValue;

public class BigDecimalConverterTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private BigDecimalConverter converter;

    @Before
    public void before() {
        converter = new BigDecimalConverter();
    }

    @Test
    public void convert() throws CsvDataTypeMismatchException {
        assertThat(converter.convert("0"), bigDecimalValue("0"));
        assertThat(converter.convert("-10"), bigDecimalValue("-10"));
        assertThat(converter.convert("0.123456789123"), bigDecimalValue("0.123456789123"));
    }

    @Test
    public void convert_willReturnDefault_whenNull() throws CsvDataTypeMismatchException {
        assertThat(converter.convert(null), nullValue());
    }

    @Test
    public void convert_willReturnCustomDefault_whenNull() throws CsvDataTypeMismatchException {
        converter = new BigDecimalConverter(BigDecimal.ONE);

        assertThat(converter.convert(null), bigDecimalValue("1"));
    }

    @Test
    public void convert_willError_whenInvalidNumber() throws CsvDataTypeMismatchException {
        thrown.expect(CsvDataTypeMismatchException.class);
        thrown.expectMessage("Invalid decimal value: abc");

        converter.convert("abc");
    }

    @Test
    public void defaultZero_convert_willReturnDefaultZero_whenNull() throws CsvDataTypeMismatchException {
        assertThat(new DefaultZero().convert(null), bigDecimalValue("0"));
    }


}
