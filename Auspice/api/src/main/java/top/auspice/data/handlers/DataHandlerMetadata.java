package top.auspice.data.handlers;

import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;
import net.aurika.ecliptor.database.dataprovider.SectionableDataSetter;
import net.aurika.namespace.NSedKey;
import top.auspice.api.user.AuspiceUser;
import top.auspice.constants.base.KeyedAuspiceObject;
import top.auspice.constants.metadata.AuspiceMetadata;
import top.auspice.constants.metadata.AuspiceMetadataHandler;
import top.auspice.constants.metadata.StandardAuspiceMetadataHandler;

import java.util.HashMap;
import java.util.Map;

public final class DataHandlerMetadata {

    public static void deserializeMetadata(SectionableDataGetter dataGetter, KeyedAuspiceObject<?> auspiceObject) {
        Map<AuspiceMetadataHandler, AuspiceMetadata> var2 = dataGetter.get("metadata").asMap(new HashMap<>(), (var1x, var2x, subSexc) -> {
            String var8 = var2x.asString(() -> {
                throw new UnsupportedOperationException();
            });
            NSedKey key = NSedKey.fromConfigString(var8);
            AuspiceMetadataHandler handler = AuspiceUser.getMetadataRegistry().getRegistered(key);
            if (handler == null) {
                handler = new StandardAuspiceMetadataHandler(key);
            }

            try {
                AuspiceMetadata metadata = handler.deserialize(auspiceObject, subSexc);
                var1x.put(handler, metadata);
            } catch (Throwable throwable) {
                throw new RuntimeException("Error while deserializing metadata with namespace: " + var8 + " -> " + key + " -> " + handler, throwable);
            }
        });
        auspiceObject.setMetadata(var2);
    }

    public static void serializeMetadata(SectionableDataSetter dataSetter, KeyedAuspiceObject<?> auspiceObject) {
        dataSetter.get("metadata").setMap(auspiceObject.getMetadata(), (metaHandler, var2, metadata) -> {
            if (metadata.shouldSave(auspiceObject)) {
                var2.setString(metaHandler.getNamespacedKey().asDataString());

                try {
                    metadata.serialize(auspiceObject, var2.getValueProvider());
                } catch (Throwable var4) {
                    throw new RuntimeException("Error while serializing metadata with namespace: " + metaHandler.getNamespacedKey(), var4);
                }
            }
        });
    }
}
