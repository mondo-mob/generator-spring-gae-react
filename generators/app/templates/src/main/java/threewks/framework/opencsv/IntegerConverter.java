package threewks.framework.opencsv;

import com.opencsv.exceptions.CsvDataTypeMismatchException;

/**
 * The standard {@link com.opencsv.bean.CsvNumber} annotation does not handle null values. This does.
 * Changed to use this across the board for consistency, even for required fields.
 */
@SuppressWarnings("WeakerAccess")
public class IntegerConverter extends NullSafeConverter<Integer> {

    private final Integer defaultValue;

    public IntegerConverter() {
        this.defaultValue = null;
    }


    public IntegerConverter(Integer defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    protected Integer defaultValue() {
        return defaultValue;
    }

    @Override
    protected Integer convertValue(String value) throws CsvDataTypeMismatchException {
        try {
            return Integer.valueOf(value);
        } catch (RuntimeException e) {
            throw new CsvDataTypeMismatchException("Invalid integer value: " + value);
        }
    }

    public static class DefaultZero extends IntegerConverter {
        public DefaultZero() {
            super(0);
        }
    }

}
