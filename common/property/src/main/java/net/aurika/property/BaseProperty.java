package net.aurika.property;

import java.io.Serializable;

/**
 * A property API for lazy initialization.
 */
public interface BaseProperty extends Serializable {
    /**
     * Get the property name.
     */
    String name();

    /**
     * Get whether this property has been set.
     */
    boolean isSet();

    /**
     * Unset the property.
     */
    void unset();
}

abstract class BasePropertyImpl implements BaseProperty {
    protected final String name;
    protected boolean set;

    protected BasePropertyImpl(String name, boolean set) {
        this.name = name;
        this.set = set;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isSet() {
        return set;
    }

    @Override
    public void unset() {
        set = false;
    }
}
