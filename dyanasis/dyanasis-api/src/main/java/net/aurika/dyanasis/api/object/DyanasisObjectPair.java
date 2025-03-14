package net.aurika.dyanasis.api.object;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public interface DyanasisObjectPair extends DyanasisObjectSupport {

    @DyanasisObjectDebugMethod
    @NotNull DyanasisObject component1();

    @DyanasisObjectDebugMethod
    @NotNull DyanasisObject component2();

    @Override
    default @NotNull SupportType supportType() {
        return SupportType.PAIR;
    }

    @Override
    @NotNull Object valueAsJava();
}
