package net.aurika.auspice.constants.ecomony.currency;

import net.aurika.common.key.namespace.NSedKey;

public abstract class AbstractNumberCurrency<T> extends AbstractCurrency<T, Number> implements NumberCurrency<T> {
    protected AbstractNumberCurrency(String type, Class<T> targetClass, NSedKey NSedKey) {
        super(type, targetClass, NSedKey);
    }
}
