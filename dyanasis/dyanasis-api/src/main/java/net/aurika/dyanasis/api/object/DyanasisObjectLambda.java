package net.aurika.dyanasis.api.object;

import org.jetbrains.annotations.NotNull;

public interface DyanasisObjectLambda extends DyanasisObjectSupport {
    /**
     * Gets the arity of this lambda.
     *
     * @return the arity
     */
    @DyanasisObjectDebugMethod
    int arity();

    /**
     * Returns whether the lambda has an output.
     *
     * @return whether it has output
     */
    @DyanasisObjectDebugMethod
    boolean hasOutput();

    @Override
    default @NotNull SupportType supportType() {
        return SupportType.LAMBDA;
    }

    @Override
    @NotNull Object valueAsJava();
}
