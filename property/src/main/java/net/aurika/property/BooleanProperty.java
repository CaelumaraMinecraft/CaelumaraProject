package net.aurika.property;

public interface BooleanProperty extends BaseProperty {

    String name();

    boolean isSet();

    boolean get() throws PropertyNotInitializedException;

    void set(boolean value);
}

class BooleanPropertyImpl implements BooleanProperty {
    private final String name;
    private boolean value;
    private boolean set;

    BooleanPropertyImpl(String name) {
        this(name, false, false);
    }

    BooleanPropertyImpl(String name, boolean value) {
        this(name, value, true);
    }

    private BooleanPropertyImpl(String name, boolean value, boolean set) {
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
    public boolean get() throws PropertyNotInitializedException {
        if (set) {
            return value;
        } else {
            throw new PropertyNotInitializedException(name);
        }
    }

    @Override
    public void set(boolean value) {
        this.value = value;
        this.set = true;
    }
}
