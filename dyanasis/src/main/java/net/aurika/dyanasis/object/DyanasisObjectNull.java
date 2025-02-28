package net.aurika.dyanasis.object;

import org.jetbrains.annotations.NotNull;

public interface DyanasisObjectNull extends DyanasisObjectSupport {
    @Override
    default @NotNull SupportType supportType() {
        return SupportType.NULL;
    }
}
