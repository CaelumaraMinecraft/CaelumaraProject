package net.aurika.dyanasis.declaration.doc;

import net.aurika.dyanasis.declaration.DyanasisDeclaration;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public interface DyanasisDoc extends DyanasisDeclaration {
    /**
     * Gets the dyanasis doc value.
     *
     * @return the doc value
     */
    @NotNull String value();

    /**
     * Get the adherent declaration.
     *
     * @return the adherent declaration
     */
    @NotNull DyanasisDocOwner owner();
}
