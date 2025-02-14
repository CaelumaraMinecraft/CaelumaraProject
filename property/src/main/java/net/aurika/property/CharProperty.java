package net.aurika.property;

public interface CharProperty extends BaseProperty {

    String name();

    boolean isSet();

    char get() throws PropertyNotInitializedException;

    void set(char value);
}

class CharPropertyImpl implements CharProperty {
    private final String name;
    private char value;
    private boolean set;

    CharPropertyImpl(String name) {
        this(name, (char) 0, false);
    }

    CharPropertyImpl(String name, char value) {
        this(name, value, true);
    }

    private CharPropertyImpl(String name, char value, boolean set) {
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
    public char get() throws PropertyNotInitializedException {
        if (set) {
            return value;
        } else {
            throw new PropertyNotInitializedException(name);
        }
    }

    @Override
    public void set(char value) {
        this.value = value;
        this.set = true;
    }
}

