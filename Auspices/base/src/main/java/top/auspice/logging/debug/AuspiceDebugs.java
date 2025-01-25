package top.auspice.logging.debug;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import net.aurika.namespace.NSKedRegistry;
import net.aurika.namespace.NSKey;
import net.aurika.namespace.NSedKey;
import top.auspice.main.Auspice;

import java.util.Collections;
import java.util.Map;

public final class AuspiceDebugs extends NSKedRegistry<AuspiceDebug> implements DebugsContainer<AuspiceDebug> {
    public static final AuspiceDebugs INSTANCE = new AuspiceDebugs();

    private AuspiceDebugs() {
        super(Auspice.get(), "DEBUG");
    }

    @Override
    public @Unmodifiable Map<NSedKey, ? extends AuspiceDebug> getDebugs() {
        return Collections.unmodifiableMap(this.registered);
    }

    public enum Std implements AuspiceDebug {

        FOLDER_REGISTRY("FOLDER_REGISTRY"),
        SAVE_ALL("SAVE_ALL"),


        ;

        private final NSedKey nsKey;

        Std(@NotNull @NSKey.Key String key) {
            nsKey = NSedKey.auspice(key);
        }

        @Override
        public @NotNull NSedKey getNamespacedKey() {
            return this.nsKey;
        }
    }

    static {
        for (Std std : Std.values()) {
            INSTANCE.register(std);
        }
    }
}
