package net.aurika.property;

public interface CharProperty extends BaseProperty {

    @Override
    String name();

    @Override
    boolean isSet();

    char get() throws PropertyNotInitializedException;

    void set(char value);
}

class CharPropertyImpl extends BasePropertyImpl implements CharProperty {
    private char value;

    CharPropertyImpl(String name) {
        this(name, false, (char) 0);
    }

    CharPropertyImpl(String name, char value) {
        this(name, true, value);
    }

    private CharPropertyImpl(String name, boolean set, char value) {
        super(name, set);
        this.value = value;
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

