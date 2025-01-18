package top.auspice.constants.metadata;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import top.auspice.constants.base.KeyedAuspiceObject;
import top.auspice.key.NSedKey;
import top.auspice.data.database.dataprovider.SectionableDataGetter;

public class StandardAuspiceMetadataHandler extends AuspiceMetadataHandler {
    public StandardAuspiceMetadataHandler(NSedKey NSedKey) {
        super(NSedKey);
    }

    public @NonNull AuspiceMetadata deserialize(@NotNull KeyedAuspiceObject<?> var1, @NonNull SectionableDataGetter var2) {


        if (var2.getDataProvider() instanceof ObjectDataProvider) {
            return new StandardKingdomMetadata(((ObjectDataProvider)var2.getDataProvider()).getRawDataObject());
        } else {
            throw new IllegalArgumentException("Cannot provider standard metadata handler for: " + var2);
        }
    }
}
