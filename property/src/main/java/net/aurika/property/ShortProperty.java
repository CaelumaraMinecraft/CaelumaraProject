package net.aurika.property;

public interface ShortProperty extends BaseProperty {

    @Override
    String name();

    @Override
    boolean isSet();

    short get() throws PropertyNotInitializedException;

    void set(short value);
}

class ShortPropertyImpl extends BasePropertyImpl implements ShortProperty {
    private short value;

    ShortPropertyImpl(String name) {
        this(name, false, (short) 0);
    }

    ShortPropertyImpl(String name, short value) {
        this(name, true, value);
    }

    private ShortPropertyImpl(String name, boolean set, short value) {
        super(name, set);
        this.value = value;
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
