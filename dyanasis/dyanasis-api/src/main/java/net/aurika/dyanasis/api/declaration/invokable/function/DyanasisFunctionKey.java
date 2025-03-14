package net.aurika.dyanasis.api.declaration.invokable.function;

import net.aurika.dyanasis.api.Named;
import net.aurika.dyanasis.api.NamingContract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DyanasisFunctionKey implements Named {
    @NamingContract.Invokable
    private final @NotNull String name;
    private final int argLen;

    public static DyanasisFunctionKey dyanasisFunctionKey(@NamingContract.Invokable final @NotNull String name, int argLen) {
        return new DyanasisFunctionKey(name, argLen);
    }

    public DyanasisFunctionKey(@NamingContract.Invokable final @NotNull String name, int argLen) {
        this.name = name;
        this.argLen = argLen;
    }

    /**
     * Gets the dyanasis function name.
     *
     * @return the dyanasis function name
     */
    @Override
    @NamingContract.Invokable
    public @NotNull String name() {
        return name;
    }

    /**
     * Gets the arguments count.
     *
     * @return the arguments count
     */
    public int argLen() {      // TODO rename
        return argLen;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, argLen);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DyanasisFunctionKey that)) return false;
        return argLen == that.argLen && Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return "FunctionKey[" + "name='" + name + '\'' + ", argLen=" + argLen + ']';
    }
}
