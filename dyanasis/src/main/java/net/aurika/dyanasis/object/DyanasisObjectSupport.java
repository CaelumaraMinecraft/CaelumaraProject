package net.aurika.dyanasis.object;

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
        MAP(DyanasisObjectMap.class),
        NULL(DyanasisObjectNull.class),
        NUMBER(DyanasisObjectNumber.class),
        STRING(DyanasisObjectString.class),
        ;
        private final @NotNull Class<? extends DyanasisObjectSupport> type;

        SupportType(@NotNull Class<? extends DyanasisObjectSupport> type) {
            this.type = type;
        }

        public @NotNull Class<? extends DyanasisObjectSupport> type() {
            return type;
        }
    }
}
