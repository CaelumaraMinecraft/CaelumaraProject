package net.aurika.property;

import java.io.Serializable;

public interface BaseProperty extends Serializable {
    String name();

    boolean isSet();
}

abstract class BasePropertyImpl implements BaseProperty {
    private final String name;
    private boolean set;

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

    protected void unset() {
        set = false;
    }
}
