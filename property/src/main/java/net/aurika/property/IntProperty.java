package net.aurika.property;

public interface IntProperty extends BaseProperty {

    String name();

    boolean isSet();

    int get() throws PropertyNotInitializedException;

    void set(int value);
}

class IntPropertyImpl implements IntProperty {
    private final String name;
    private int value;
    private boolean set;

    IntPropertyImpl(String name) {
        this(name, 0, false);
    }

    IntPropertyImpl(String name, int value) {
        this(name, value, true);
    }

    private IntPropertyImpl(String name, int value, boolean set) {
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
