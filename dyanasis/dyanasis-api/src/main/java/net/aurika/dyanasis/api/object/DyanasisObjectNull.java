package net.aurika.dyanasis.api.object;

import org.jetbrains.annotations.NotNull;

public interface DyanasisObjectNull extends DyanasisObjectSupport {
    @Override
    default @NotNull SupportType supportType() {
        return SupportType.NULL;
    }

    @Override
    default @NotNull Void valueAsJava() {
        return null;
    }
}
