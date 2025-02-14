package net.aurika.property;

public interface DoubleProperty extends BaseProperty {

    String name();

    boolean isSet();

    double get() throws PropertyNotInitializedException;

    void set(double value);
}

class DoublePropertyImpl implements DoubleProperty {
    private final String name;
    private double value;
    private boolean set;

    DoublePropertyImpl(String name) {
        this(name, 0, false);
    }

    DoublePropertyImpl(String name, double value) {
        this(name, value, true);
    }

    private DoublePropertyImpl(String name, double value, boolean set) {
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
    public double get() throws PropertyNotInitializedException {
        if (set) {
            return value;
        } else {
            throw new PropertyNotInitializedException(name);
        }
    }

    @Override
    public void set(double value) {
        this.value = value;
        this.set = true;
    }
}
