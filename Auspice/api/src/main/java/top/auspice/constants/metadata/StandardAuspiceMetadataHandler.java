package top.auspice.constants.metadata;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import net.aurika.ecliptor.api.KeyedDataObject;
import net.aurika.namespace.NSedKey;
import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;

public class StandardAuspiceMetadataHandler extends AuspiceMetadataHandler {
    public StandardAuspiceMetadataHandler(NSedKey NSedKey) {
        super(NSedKey);
    }

    public @NonNull AuspiceMetadata deserialize(@NotNull KeyedDataObject.Impl<?> var1, @NonNull SectionableDataGetter var2) {


        if (var2.getDataProvider() instanceof ObjectDataProvider) {
            return new StandardKingdomMetadata(((ObjectDataProvider)var2.getDataProvider()).getRawDataObject());
        } else {
            throw new IllegalArgumentException("Cannot provider standard metadata handler for: " + var2);
        }
    }
}
