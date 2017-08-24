package threewks.framework.ref;

import com.google.common.base.Function;

/**
 * Interface to allow an enum to be marked as reference data with simple name and description for UI.
 */
public interface ReferenceData {

    /**
     * Will already be implemented by enum. Just returns the enum name.
     */
    String name();

    /**
     * Description to use for business/ui.
     */
    String getDescription();

    /**
     * Transform the enum to a map which can be used in a JSON representation of the data.
        */
    Function<ReferenceData, ReferenceDataDto> TO_DTO_TRANSFORMER = new Function<ReferenceData, ReferenceDataDto>() {
        @Override
        public ReferenceDataDto apply(ReferenceData referenceData) {
            return referenceData == null ? null : new ReferenceDataDto(referenceData.name(), referenceData.getDescription());
        }
    };
}
