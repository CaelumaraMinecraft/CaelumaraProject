package net.aurika.auspice.constants.metadata;

import net.aurika.auspice.constants.base.KeyedAuspiceObject;
import net.aurika.common.key.Ident;
import net.aurika.common.key.Identified;
import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class AuspiceMetadataHandler implements Identified {

  protected final Ident id;

  protected AuspiceMetadataHandler(@NotNull Ident id) {
    Objects.requireNonNull(id, "id");
    this.id = id;
  }

  public final @NotNull Ident ident() {
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
