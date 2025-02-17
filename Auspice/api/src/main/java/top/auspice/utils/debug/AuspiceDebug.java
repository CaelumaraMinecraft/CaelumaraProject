package top.auspice.utils.debug;

import org.jetbrains.annotations.NotNull;
import net.aurika.namespace.NSedKey;
import top.auspice.main.Auspice;

public enum AuspiceDebug implements DebugNS {
    FOLDER_REGISTRY("FOLDER_REGISTRY"),
    SAVE_ALL("SAVE_ALL");

    private final NSedKey NSedKey;

    AuspiceDebug(String key) {
        this.NSedKey = new NSedKey(Auspice.get().namespace(), key);
    }

    public @NotNull NSedKey getNamespacedKey() {
        return this.NSedKey;
    }
}
