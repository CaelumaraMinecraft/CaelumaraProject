package net.aurika.property;

public interface LongProperty extends BaseProperty {

    @Override
    String name();

    @Override
    boolean isSet();

    long get() throws PropertyNotInitializedException;

    void set(long value);
}

class LongPropertyImpl extends BasePropertyImpl implements LongProperty {
    private long value;

    LongPropertyImpl(String name) {
        this(name, false, 0L);
    }

    LongPropertyImpl(String name, long value) {
        this(name, true, value);
    }

    private LongPropertyImpl(String name, boolean set, long value) {
        super(name, set);
        this.value = value;
    }

    @Override
    public long get() throws PropertyNotInitializedException {
        if (set) {
            return value;
        } else {
            throw new PropertyNotInitializedException(name);
        }
    }

    @Override
    public void set(long value) {
        this.value = value;
        this.set = true;
    }
}
