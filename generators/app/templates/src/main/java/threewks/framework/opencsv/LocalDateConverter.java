package threewks.framework.opencsv;

import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter extends NullSafeConverter<LocalDate> {

    private final DateTimeFormatter formatter;
    private final String format;

    public LocalDateConverter() {
        this("d/M/yyyy");
    }

    // Subclass and call this if you want a different format
    public LocalDateConverter(String format) {
        this.format = format;
        this.formatter = DateTimeFormatter.ofPattern(format);
    }

    @Override
    protected LocalDate convertValue(String value) throws CsvDataTypeMismatchException {
        try {
            return LocalDate.parse(value, formatter);
        } catch (RuntimeException e) {
            throw new CsvDataTypeMismatchException(String.format("%s is not a valid date matching pattern: %s", value, format));
        }
    }
}
