package net.aurika.dyanasis.api.declaration.invokable.function;

import net.aurika.dyanasis.api.Named;
import net.aurika.dyanasis.api.NamingContract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DyanasisFunctionKey implements Named {
    @NamingContract.Invokable
    private final @NotNull String name;
    private final int arity;

    public static DyanasisFunctionKey dyanasisFunctionKey(@NamingContract.Invokable final @NotNull String name, int argLen) {
        return new DyanasisFunctionKey(name, argLen);
    }

    protected DyanasisFunctionKey(@NamingContract.Invokable final @NotNull String name, int arity) {
        this.name = name;
        this.arity = arity;
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
    public int arity() {      // TODO rename
        return arity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, arity);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DyanasisFunctionKey that)) return false;
        return arity == that.arity && Objects.equals(name, that.name);
    }

    @Override
    public @NotNull String toString() {
        return "FunctionKey[name='" + name + "', arity=" + arity + "]";
    }
}
