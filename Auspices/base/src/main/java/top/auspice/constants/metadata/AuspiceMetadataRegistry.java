package top.auspice.constants.metadata;

import org.jetbrains.annotations.NotNull;
import net.aurika.data.object.KeyedDataObject;
import net.aurika.namespace.Lockable;
import net.aurika.namespace.NSKedRegistry;
import net.aurika.data.managers.base.KeyedDataManager;
import top.auspice.main.Auspice;

import java.util.Collection;

public class AuspiceMetadataRegistry extends NSKedRegistry<AuspiceMetadataHandler> implements Lockable {
    private static boolean unlocked = true;
    public static final AuspiceMetadataRegistry INSTANCE = new AuspiceMetadataRegistry();

    private AuspiceMetadataRegistry() {
        super(Auspice.get(), "METADATA");
    }

    public final void register(@NotNull AuspiceMetadataHandler var1) {
        if (var1.getNamespacedKey().getNamespace().equals(Auspice.get().getNamespace())) {
            throw new IllegalArgumentException("Cannot register metadata handlers as auspice namespace: " + var1);
        } else {
            super.register(var1);
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
