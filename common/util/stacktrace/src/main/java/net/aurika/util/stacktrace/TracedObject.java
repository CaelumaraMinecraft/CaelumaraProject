
package net.aurika.util.stacktrace;

import org.jetbrains.annotations.NotNull;

public final class TracedObject<V> {
    private final RuntimeException exception;
    private final V object;

    public TracedObject(V object, RuntimeException exception) {
        this.exception = exception;
        this.object = object;
    }

    public V getObject() {
        return this.object;
    }

    public @NotNull RuntimeException getException() {
        return new RuntimeException(this.exception);
    }

    public int hashCode() {
        return this.object.hashCode();
    }

    public boolean equals(Object var1) {
        return this.object.equals(var1);
    }

    public String toString() {
        return this.object.toString();
    }
}
