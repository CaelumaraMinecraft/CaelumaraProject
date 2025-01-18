package top.auspice.data.handlers;

import top.auspice.constants.base.KeyedAuspiceObject;
import top.auspice.constants.metadata.AuspiceMetadata;
import top.auspice.constants.metadata.AuspiceMetadataHandler;
import top.auspice.constants.metadata.StandardAuspiceMetadataHandler;
import top.auspice.key.NSedKey;
import top.auspice.data.database.dataprovider.SectionableDataGetter;
import top.auspice.data.database.dataprovider.SectionableDataSetter;
import top.auspice.api.user.AuspiceUser;

import java.util.HashMap;
import java.util.Map;

public final class DataHandlerMetadata {

    public static void deserializeMetadata(SectionableDataGetter var0, KeyedAuspiceObject<?> auspiceObject) {
        Map<AuspiceMetadataHandler, AuspiceMetadata> var2 = var0.get("metadata").asMap(new HashMap<>(), (var1x, var2x, subSexc) -> {
            String var8 = var2x.asString(() -> {
                throw new UnsupportedOperationException();
            });
            NSedKey NSedKey = NSedKey.fromConfigString(var8);
            AuspiceMetadataHandler handler = AuspiceUser.getMetadataRegistry().getRegistered(NSedKey);
            if (handler == null) {
                handler = new StandardAuspiceMetadataHandler(NSedKey);
            }

            try {
                AuspiceMetadata metadata = handler.deserialize(auspiceObject, subSexc);
                var1x.put(handler, metadata);
            } catch (Throwable throwable) {
                throw new RuntimeException("Error while deserializing metadata with namespace: " + var8 + " -> " + NSedKey + " -> " + handler, throwable);
            }
        });
        auspiceObject.setMetadata(var2);
    }

    public static void serializeMetadata(SectionableDataSetter var0, KeyedAuspiceObject<?> var1) {
        var0.get("metadata").setMap((var1).getMetadata(), (var1x, var2, var3) -> {
            if (var3.shouldSave(var1)) {
                var2.setString(var1x.getNamespacedKey().asDataString());

                try {
                    var3.serialize(var1, var2.getValueProvider());
                } catch (Throwable var4) {
                    throw new RuntimeException("Error while serializing metadata with namespace: " + var1x.getNamespacedKey(), var4);
                }
            }
        });
    }
}
