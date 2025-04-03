package net.aurika.auspice.constants.metadata;

import net.aurika.auspice.constants.base.KeyedAuspiceObject;
import net.aurika.common.key.Ident;
import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;
import net.aurika.ecliptor.database.flatfile.ObjectDataProvider;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class StandardAuspiceMetadataHandler extends AuspiceMetadataHandler {

  public StandardAuspiceMetadataHandler(Ident id) {
    super(id);
  }

  public @NonNull AuspiceMetadata deserialize(@NotNull KeyedAuspiceObject<?> obj, @NonNull SectionableDataGetter var2) {


    if (var2 instanceof ObjectDataProvider) {
      return new StandardAuspiceMetadata(((ObjectDataProvider) var2).rawDataObject());
    } else {
      throw new IllegalArgumentException("Cannot provider standard metadata handler for: " + var2);
    }
  }

}
