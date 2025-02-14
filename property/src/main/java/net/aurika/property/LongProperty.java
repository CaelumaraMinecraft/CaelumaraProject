package net.aurika.property;

public interface LongProperty extends BaseProperty {

    String name();

    boolean isSet();

    long get() throws PropertyNotInitializedException;

    void set(long value);
}

class LongPropertyImpl implements LongProperty {
    private final String name;
    private long value;
    private boolean set;

    LongPropertyImpl(String name) {
        this(name, 0, false);
    }

    LongPropertyImpl(String name, long value) {
        this(name, value, true);
    }

    private LongPropertyImpl(String name, long value, boolean set) {
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
