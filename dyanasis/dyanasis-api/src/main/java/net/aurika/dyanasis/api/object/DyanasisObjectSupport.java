package net.aurika.dyanasis.api.object;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Syntactics dyanasis object.
 */
@ApiStatus.NonExtendable
public interface DyanasisObjectSupport extends DyanasisObject {
    @NotNull SupportType supportType();

    enum SupportType {
        ARRAY(DyanasisObjectArray.class),
        BOOL(DyanasisObjectBool.class),
        LAMBDA(DyanasisObjectLambda.class),
        MAP(DyanasisObjectMap.class),
        NULL(DyanasisObjectNull.class),
        NUMBER(DyanasisObjectNumber.class),
        PAIR(DyanasisObjectPair.class),
        STRING(DyanasisObjectString.class),
        ;
        private final @NotNull Class<? extends DyanasisObjectSupport> type;

        SupportType(@NotNull Class<? extends DyanasisObjectSupport> type) {
            this.type = type;
        }

        @ApiStatus.Experimental
        public @NotNull Class<? extends DyanasisObjectSupport> type() {
            return type;
        }
    }
}
