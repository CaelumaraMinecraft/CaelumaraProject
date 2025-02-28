package net.aurika.dyanasis.object;

import org.jetbrains.annotations.NotNull;

public interface DyanasisObjectMap extends DyanasisObjectSupport {
    @Override
    default @NotNull SupportType supportType() {
        return SupportType.MAP;
    }
}
