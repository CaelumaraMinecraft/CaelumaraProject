package net.aurika.property;

public interface IntProperty extends BaseProperty {

    @Override
    String name();

    @Override
    boolean isSet();

    int get() throws PropertyNotInitializedException;

    void set(int value);
}

class IntPropertyImpl extends BasePropertyImpl implements IntProperty {
    private int value;

    IntPropertyImpl(String name) {
        this(name, false, 0);
    }

    IntPropertyImpl(String name, int value) {
        this(name, true, value);
    }

    private IntPropertyImpl(String name, boolean set, int value) {
        super(name, set);
        this.value = value;
    }

    @Override
    public int get() throws PropertyNotInitializedException {
        if (set) {
            return value;
        } else {
            throw new PropertyNotInitializedException(name);
        }
    }

    @Override
    public void set(int value) {
        this.value = value;
        this.set = true;
    }
}
