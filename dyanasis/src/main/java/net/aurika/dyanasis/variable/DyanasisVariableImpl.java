package net.aurika.dyanasis.variable;

import net.aurika.dyanasis.object.DyanasisObject;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class DyanasisVariableImpl implements DyanasisVariable {
    private final @NotNull DyanasisObject value;

    public DyanasisVariableImpl(@NotNull DyanasisObject value) {
        Validate.Arg.notNull(value, "value");
        this.value = value;
    }

    @Override
    public @NotNull DyanasisObject value() {
        return value;
    }
}
