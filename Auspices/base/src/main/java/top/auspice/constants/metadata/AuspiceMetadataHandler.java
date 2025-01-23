package top.auspice.constants.metadata;

import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.dataprovider.SectionableDataGetter;
import top.auspice.key.NSKeyed;
import top.auspice.key.NSedKey;

import java.util.Objects;

public abstract class AuspiceMetadataHandler implements NSKeyed {

    protected final NSedKey NSedKey;

    protected AuspiceMetadataHandler(@NotNull NSedKey NSedKey) {
        this.NSedKey = Objects.requireNonNull(NSedKey);
    }

    public @NotNull NSedKey getNamespacedKey() {
        return this.NSedKey;
    }

    public abstract @NotNull AuspiceMetadata deserialize(@NotNull KeyedAuspiceObject<?, ?> var1, @NotNull SectionableDataGetter dataGetter);

    public int hashCode() {
        return this.NSedKey.hashCode();
    }

    public boolean equals(Object var1) {
        return var1 instanceof AuspiceMetadataHandler && this.NSedKey.equals(((AuspiceMetadataHandler) var1).NSedKey);
    }

    public String toString() {
        return this.getClass().getSimpleName() + '[' + this.NSedKey.asString() + ']';
    }
}
