package threewks.framework;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnSave;
import org.springframework.contrib.gae.datastore.entity.IndexAware;
import org.springframework.contrib.gae.search.SearchIndex;
import threewks.util.DateTimeUtils;

import java.time.Duration;
import java.time.OffsetDateTime;

/**
 * Created and updated timestamps set. On creation updated and created are the same.
 * <p>
 */
@SuppressWarnings("unchecked")
public class BaseEntityCore implements IndexAware {

    public static class Fields {
        public static final String updated = "updated";
        public static final String created = "created";
    }

    @Index
    @SearchIndex
    private OffsetDateTime created;

    @Index
    @SearchIndex
    private OffsetDateTime updated;

    @Ignore
    private transient boolean skipSettingAuditableFields = false;

    /**
     * Handy for data migrations where you don't want to overwrite last updated.
     */
    public <T extends BaseEntityCore> T skipSettingAuditableFields() {
        skipSettingAuditableFields = true;
        return (T) this;
    }

    /**
     * Called whenever a reindex is done. We skip setting auditable fields.
     */
    @Override
    public void onReindex() {
        skipSettingAuditableFields();
    }

    @JsonIgnore
    public boolean isSkipSettingAuditableFields() {
        return skipSettingAuditableFields;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public OffsetDateTime getUpdated() {
        return updated;
    }

    @JsonIgnore
    public Duration getDurationFromCreatedToUpdated() {
        if (created == null || updated == null) {
            throw new IllegalStateException("Duration can not be calculated on entity not yet persisted");
        }
        return Duration.between(created, updated);
    }

    /**
     * Optional hook for child entities
     */
    protected void onCreated() {
    }

    /**
     * Optional hook for child entities
     */
    protected void onUpdated() {
    }

    @OnSave
    private void setAuditableFieldsOnSave() {
        if (!skipSettingAuditableFields) {
            updated = DateTimeUtils.now();
            onUpdated();

            if (created == null) {
                onCreated();
                created = updated;
            }
        }
    }

    @JsonIgnore
    public boolean isNewEntity() {
        return created == null;
    }

}
