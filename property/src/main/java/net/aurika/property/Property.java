package net.aurika.property;

import org.jetbrains.annotations.NotNull;

public interface Property<T> extends BaseProperty {
    /**
     * Create a not initialized property.
     */
    static <T> @NotNull Property<T> property(@NotNull String name) {
        return new PropertyImpl<>(name);
    }

    /**
     * Create an initialized property.
     */
    static <T> @NotNull Property<T> property(@NotNull String name, T value) {
        return new PropertyImpl<>(name, value);
    }

    /**
     * Create a not initialized boolean property.
     */
    static @NotNull BooleanProperty booleanProperty(@NotNull String name) {
        return new BooleanPropertyImpl(name);
    }

    /**
     * Create an initialized boolean property.
     */
    static @NotNull BooleanProperty booleanProperty(@NotNull String name, boolean value) {
        return new BooleanPropertyImpl(name, value);
    }

    /**
     * Create a not initialized int property.
     */
    static @NotNull IntProperty intProperty(@NotNull String name) {
        return new IntPropertyImpl(name);
    }

    /**
     * Create an initialized int property.
     */
    static @NotNull IntProperty intProperty(@NotNull String name, int value) {
        return new IntPropertyImpl(name, value);
    }

    /**
     * Create a not initialized long property.
     */
    static @NotNull LongProperty longProperty(@NotNull String name) {
        return new LongPropertyImpl(name);
    }

    /**
     * Create an initialized long property.
     */
    static @NotNull LongProperty longProperty(@NotNull String name, long value) {
        return new LongPropertyImpl(name, value);
    }

    String name();

    boolean isSet();

    T get() throws PropertyNotInitializedException;

    void set(T value);
}

class PropertyImpl<T> implements Property<T> {
    private final String name;
    private T value;
    private boolean set;

    PropertyImpl(String name) {
        this(name, null, false);
    }

    PropertyImpl(String name, T value) {
        this(name, value, true);
    }

    private PropertyImpl(String name, T value, boolean set) {
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
    public T get() throws PropertyNotInitializedException {
        if (set) {
            return value;
        } else {
            throw new PropertyNotInitializedException(name);
        }
    }

    @Override
    public void set(T value) {
        this.value = value;
        this.set = true;
    }
}
