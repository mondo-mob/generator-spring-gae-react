package threewks.framework.opencsv;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class LocalDateConverterTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private LocalDateConverter converter;

    @Before
    public void before() {
        converter = new LocalDateConverter();
    }

    @Test
    public void convert() throws CsvDataTypeMismatchException {
        assertThat(converter.convert("01/12/2018"), equalTo(LocalDate.parse("2018-12-01")));
        assertThat(converter.convert("1/1/2018"), equalTo(LocalDate.parse("2018-01-01")));
        assertThat(converter.convert(null), nullValue());
    }

    @Test
    public void convert_willError_whenInvalidFormat() throws CsvDataTypeMismatchException {
        thrown.expect(CsvDataTypeMismatchException.class);
        thrown.expectMessage("2018-01-01 is not a valid date matching pattern: d/M/yyyy");

        converter.convert("2018-01-01");
    }

    @Test
    public void convert_customFormat() throws CsvDataTypeMismatchException {
        converter = new LocalDateConverter("yyyy-MM-dd");

        assertThat(converter.convert("2018-12-01"), equalTo(LocalDate.parse("2018-12-01")));
    }

}
