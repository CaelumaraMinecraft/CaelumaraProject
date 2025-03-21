package net.aurika.dyanasis.api.declaration.doc;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDyanasisDoc implements DyanasisDoc {

    protected @NotNull String value;

    protected AbstractDyanasisDoc(@NotNull String value) {
        Validate.Arg.notNull(value, "value");
        this.value = value;
    }

    @Override
    public @NotNull String value() {
        return value;
    }
}
