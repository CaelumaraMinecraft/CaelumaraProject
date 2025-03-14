package net.aurika.dyanasis.api.object;

import org.jetbrains.annotations.NotNull;

public interface DyanasisObjectBool extends DyanasisObjectSupport {
    @Override
    default @NotNull SupportType supportType() {
        return SupportType.BOOL;
    }

    @Override
    @NotNull Boolean valueAsJava();
}
