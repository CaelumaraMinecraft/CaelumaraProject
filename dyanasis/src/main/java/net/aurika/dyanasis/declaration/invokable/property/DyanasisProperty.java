package net.aurika.dyanasis.declaration.invokable.property;

import net.aurika.dyanasis.NamingContract;
import net.aurika.dyanasis.declaration.invokable.DyanasisInvokable;
import org.jetbrains.annotations.NotNull;

public interface DyanasisProperty extends DyanasisInvokable {
    /**
     * Gets the dyanasis property name.
     *
     * @return the dyanasis property name
     */
    @NamingContract.Invokable
    @NotNull String name();

    @Override
    @NotNull DyanasisPropertyOwner owner();

    static boolean isGetable(@NotNull DyanasisProperty property) {
        return property instanceof DyanasisGetableProperty;
    }

    static boolean isSetable(@NotNull DyanasisProperty property) {
        return property instanceof DyanasisSetableProperty;
    }
}
