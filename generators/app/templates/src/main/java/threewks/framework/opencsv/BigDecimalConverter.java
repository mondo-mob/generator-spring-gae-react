package threewks.framework.opencsv;

import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.math.BigDecimal;

/**
 * The standard {@link com.opencsv.bean.CsvNumber} annotation does not handle null values. This does.
 * Changed to use this across the board for consistency, even for required fields.
 */
@SuppressWarnings("WeakerAccess")
public class BigDecimalConverter extends NullSafeConverter<BigDecimal> {

    private final BigDecimal defaultValue;

    public BigDecimalConverter() {
        this.defaultValue = null;
    }


    public BigDecimalConverter(BigDecimal defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    protected BigDecimal defaultValue() {
        return defaultValue;
    }

    @Override
    protected BigDecimal convertValue(String value) throws CsvDataTypeMismatchException {
        try {
            return new BigDecimal(value);
        } catch (RuntimeException e) {
            throw new CsvDataTypeMismatchException("Invalid decimal value: " + value);
        }
    }

    public static class DefaultZero extends BigDecimalConverter {
        public DefaultZero() {
            super(BigDecimal.ZERO);
        }
    }

}
