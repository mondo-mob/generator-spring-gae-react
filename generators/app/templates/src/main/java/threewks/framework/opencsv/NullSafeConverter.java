package threewks.framework.opencsv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;

abstract class NullSafeConverter<T> extends AbstractBeanField {

    protected abstract T convertValue(@NotNull String value) throws CsvDataTypeMismatchException;

    /**
     * Allows sub-classes to specify value for nulls
     * @return default value when a {@code null} is encountered.
     */
    protected T defaultValue() {
        return null;
    }

    @Override
    protected T convert(String value) throws CsvDataTypeMismatchException {
        return StringUtils.isBlank(value) ? defaultValue() : convertValue(value);
    }

}
