package top.auspice.constants.metadata;

import net.aurika.data.database.dataprovider.SectionableDataGetter;
import net.aurika.namespace.NamespacedKeyContainer;
import net.aurika.namespace.NSedKey;
import org.jetbrains.annotations.NotNull;
import top.auspice.constants.base.KeyedAuspiceObject;

import java.util.Objects;

public abstract class AuspiceMetadataHandler implements NamespacedKeyContainer {

    protected final NSedKey id;

    protected AuspiceMetadataHandler(@NotNull NSedKey id) {
        Objects.requireNonNull(id, "id");
        this.id = id;
    }

    public @NotNull NSedKey getNamespacedKey() {
        return this.id;
    }

    public abstract @NotNull AuspiceMetadata deserialize(@NotNull KeyedAuspiceObject<?> var1, @NotNull SectionableDataGetter dataGetter);

    public int hashCode() {
        return this.id.hashCode();
    }

    public boolean equals(Object var1) {
        return var1 instanceof AuspiceMetadataHandler && this.id.equals(((AuspiceMetadataHandler) var1).id);
    }

    public String toString() {
        return this.getClass().getSimpleName() + '[' + this.id.asString() + ']';
    }
}
