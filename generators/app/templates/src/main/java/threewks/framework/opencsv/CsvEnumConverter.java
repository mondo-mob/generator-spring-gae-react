package threewks.framework.opencsv;

import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class CsvEnumConverter<E extends Enum<E>> extends NullSafeConverter<E> {

    private final Class<E> enumType;

    public CsvEnumConverter(Class<E> enumType) {
        this.enumType = enumType;
    }

    @Override
    protected E convertValue(String value) throws CsvDataTypeMismatchException {
        try {
            return value == null ? null : Enum.valueOf(enumType, value);
        } catch (IllegalArgumentException e) {
            throw new CsvDataTypeMismatchException(String.format("Invalid %s value: %s", enumType.getSimpleName(), value));
        }
    }

}
