package net.aurika.auspice.constants.ecomony.currency;

import org.jetbrains.annotations.NotNull;
import net.aurika.common.key.namespace.NSedKey;

public abstract class AbstractCurrency<T, C> implements CurrencyType<T, C> {

    protected final String type;
    protected final Class<T> targetClass;
    protected final NSedKey NSedKey;

    protected AbstractCurrency(String type, Class<T> targetClass, NSedKey NSedKey) {
        this.type = type;
        this.targetClass = targetClass;
        this.NSedKey = NSedKey;
    }


    public @NotNull String getType() {
        return this.type;
    }

    public @NotNull Class<T> getTargetClass() {
        return this.targetClass;
    }

    public @NotNull NSedKey getNamespacedKey() {
        return this.NSedKey;
    }
}
