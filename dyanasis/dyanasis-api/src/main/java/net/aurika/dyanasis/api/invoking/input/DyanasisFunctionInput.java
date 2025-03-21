package net.aurika.dyanasis.api.invoking.input;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * The data of dyanasis function invocation. It is based on string.
 */
public interface DyanasisFunctionInput {

    /**
     * Gets an argument from {@code index}.
     *
     * @param index the index
     * @return the argument
     * @throws IndexOutOfBoundsException when the index is out of bound for the arguments
     */
    @NotNull String arg(@Range(from = 0, to = 127) int index) throws IndexOutOfBoundsException;

    /**
     * Gets the arguments of the dyanasis function input.
     *
     * @return the arguments
     */
    @NotNull String @NotNull [] args();

    @NotNull String originalString();
}
