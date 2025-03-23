package net.aurika.auspice.constants.metadata;

import net.aurika.auspice.user.Auspice;
import net.aurika.common.key.registry.AbstractIdentifiedRegistry;
import net.aurika.common.key.registry.Lockable;
import net.aurika.ecliptor.api.KeyedDataObject;
import net.aurika.ecliptor.managers.base.KeyedDataManager;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class AuspiceMetadataRegistry extends AbstractIdentifiedRegistry<AuspiceMetadataHandler> implements Lockable {
    private static boolean unlocked = true;
    public static final AuspiceMetadataRegistry INSTANCE = new AuspiceMetadataRegistry();

    private AuspiceMetadataRegistry() {
        super();
    }

    public final void register(@NotNull AuspiceMetadataHandler metadataHandler) {
        if (Auspice.get().namespace().equals(metadataHandler.ident().namespace())) {
            throw new IllegalArgumentException("Cannot register metadata handlers as auspice namespace: " + metadataHandler);
        } else {
            super.register(metadataHandler);
        }
    }

    public final void lock() {
        if (!unlocked) {
            throw new IllegalAccessError("Registers are already closed");
        } else {
            unlocked = false;
        }
    }

    public static void removeMetadata(KeyedDataManager<?, ?> dataManager, Collection<AuspiceMetadataHandler> handlers) {
        for (KeyedDataObject.Impl<?> keyedAuspiceObject : dataManager.getLoadedData()) {
            handlers.forEach((it) -> (keyedAuspiceObject).getMetadata().remove(it));
        }
    }
}
