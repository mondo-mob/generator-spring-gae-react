package threewks.framework.opencsv;

import com.opencsv.exceptions.CsvDataTypeMismatchException;

import javax.validation.constraints.NotNull;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class YesNoConverter extends NullSafeConverter<Boolean> {

    private static final String YES = "Y";
    private static final String NO = "N";

    @Override
    protected Boolean convertValue(@NotNull String value) throws CsvDataTypeMismatchException {

        if (YES.equalsIgnoreCase(value.trim())) {
            return TRUE;
        }

        if (NO.equalsIgnoreCase(value.trim())) {
            return FALSE;
        }

        throw new CsvDataTypeMismatchException("Unknown Yes/No value specified");
    }
}
