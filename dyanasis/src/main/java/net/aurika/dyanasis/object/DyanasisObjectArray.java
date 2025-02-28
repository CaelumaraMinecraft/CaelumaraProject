package net.aurika.dyanasis.object;

import org.jetbrains.annotations.NotNull;

public interface DyanasisObjectArray extends DyanasisObjectSupport {
    @Override
    default @NotNull SupportType supportType() {
        return SupportType.ARRAY;
    }
}
