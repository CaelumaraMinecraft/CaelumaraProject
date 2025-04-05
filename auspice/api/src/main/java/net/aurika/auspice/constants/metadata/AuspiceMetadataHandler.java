package net.aurika.auspice.constants.metadata;

import net.aurika.auspice.constants.base.KeyedAuspiceObject;
import net.aurika.common.key.Key;
import net.aurika.common.key.Keyed;
import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class AuspiceMetadataHandler implements Keyed {

  protected final Key id;

  protected AuspiceMetadataHandler(@NotNull Key id) {
    Objects.requireNonNull(id, "id");
    this.id = id;
  }

  public final @NotNull Key key() {
    return this.id;
  }

  public abstract @NotNull AuspiceMetadata deserialize(@NotNull KeyedAuspiceObject<?> obj, @NotNull SectionableDataGetter dataGetter);

  public final int hashCode() {
    return this.id.hashCode();
  }

  public final boolean equals(Object obj) {
    return obj instanceof AuspiceMetadataHandler && this.id.equals(((AuspiceMetadataHandler) obj).id);
  }

  public String toString() {
    return this.getClass().getSimpleName() + '[' + this.id.asDataString() + ']';
  }

}
