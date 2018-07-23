package threewks.framework.opencsv;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class YesNoConverterTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private YesNoConverter test;

    @Before
    public void setUp() {
        test = new YesNoConverter();
    }

    @Test
    public void convert_willReturnNull_whenNull() throws Exception {
        assertThat(test.convert(null), nullValue());
    }

    @Test
    public void convert_willReturnNull_whenEmpty() throws Exception {
        assertThat(test.convert(""), nullValue());
    }

    @Test
    public void convert_willReturnNull_whenWhitespaceOnly() throws Exception {
        assertThat(test.convert("     "), nullValue());
    }

    @Test
    public void convert_willReturnTrue_whenYesString() throws Exception {
        assertThat(test.convert("Y"), is(TRUE));
    }

    @Test
    public void convert_willReturnTrue_whenYesStringWithWhitespace() throws Exception {
        assertThat(test.convert(" Y  "), is(TRUE));
    }

    @Test
    public void convert_willReturnTrue_whenLowerYesStringWithWhitespace() throws Exception {
        assertThat(test.convert(" y  "), is(TRUE));
    }

    @Test
    public void convert_willReturnFalse_whenNoString() throws Exception {
        assertThat(test.convert("N"), is(FALSE));
    }

    @Test
    public void convert_willReturnFALSE_whenNStringWithWhitespace() throws Exception {
        assertThat(test.convert(" N  "), is(FALSE));
    }

    @Test
    public void convert_willReturnFalse_whenLowerNoStringWithWhitespace() throws Exception {
        assertThat(test.convert(" n  "), is(FALSE));
    }

    @Test
    public void convert_willThrow_whenInvalidNonEmptyString() throws Exception {
        thrown.expect(CsvDataTypeMismatchException.class);
        thrown.expectMessage("Unknown Yes/No value specified");

        assertThat(test.convert(" goat-man  "), is(FALSE));
    }
}
