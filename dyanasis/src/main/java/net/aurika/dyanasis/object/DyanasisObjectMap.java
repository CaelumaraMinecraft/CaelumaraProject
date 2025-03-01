package net.aurika.dyanasis.object;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface DyanasisObjectMap extends DyanasisObjectSupport {
    @Override
    default @NotNull SupportType supportType() {
        return SupportType.MAP;
    }

    @Override
    @NotNull Map<?, ?> valueAsJava();
}
