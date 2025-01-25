package top.auspice.diversity;

import org.jetbrains.annotations.NotNull;
import net.aurika.namespace.NSedKey;

import java.util.Objects;

public abstract class AbstractDiversity implements Diversity {
    protected final NSedKey NSedKey;

    public AbstractDiversity(NSedKey NSedKey) {
        this.NSedKey = NSedKey;
    }

    @Override
    public @NotNull NSedKey getNamespacedKey() {
        Objects.requireNonNull(NSedKey);
        return this.NSedKey;
    }
}
