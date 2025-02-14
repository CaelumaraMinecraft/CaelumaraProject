package net.aurika.property;

public interface ShortProperty extends BaseProperty {

    String name();

    boolean isSet();

    short get() throws PropertyNotInitializedException;

    void set(short value);
}

class ShortPropertyImpl implements ShortProperty {
    private final String name;
    private short value;
    private boolean set;

    ShortPropertyImpl(String name) {
        this(name, (short) 0, false);
    }

    ShortPropertyImpl(String name, short value) {
        this(name, value, true);
    }

    private ShortPropertyImpl(String name, short value, boolean set) {
        this.name = name;
        this.value = value;
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
    public short get() throws PropertyNotInitializedException {
        if (set) {
            return value;
        } else {
            throw new PropertyNotInitializedException(name);
        }
    }

    @Override
    public void set(short value) {
        this.value = value;
        this.set = true;
    }
}
