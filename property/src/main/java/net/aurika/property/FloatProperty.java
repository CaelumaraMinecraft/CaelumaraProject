package net.aurika.property;

public interface FloatProperty extends BaseProperty {

    String name();

    boolean isSet();

    float get() throws PropertyNotInitializedException;

    void set(float value);
}

class FloatPropertyImpl implements FloatProperty {
    private final String name;
    private float value;
    private boolean set;

    FloatPropertyImpl(String name) {
        this(name, 0, false);
    }

    FloatPropertyImpl(String name, float value) {
        this(name, value, true);
    }

    private FloatPropertyImpl(String name, float value, boolean set) {
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
    public float get() throws PropertyNotInitializedException {
        if (set) {
            return value;
        } else {
            throw new PropertyNotInitializedException(name);
        }
    }

    @Override
    public void set(float value) {
        this.value = value;
        this.set = true;
    }
}
