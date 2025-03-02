package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import net.aurika.dyanasis.lexer.DyanasisLexerSettings;
import net.aurika.dyanasis.object.AbstractDyanasisObject;
import net.aurika.dyanasis.object.DyanasisObject;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class StandardDyanasisObjectNumber extends AbstractDyanasisObject<Number> implements DyanasisObject {

    public StandardDyanasisObjectNumber(@NotNull Number value, @NotNull DyanasisLexerSettings settings) {
        super(value, settings);
    }

    @Override
    public @NotNull DyanasisProperties dyanasisProperties() {
        TODO
    }

    @Override
    public @NotNull DyanasisFunctions dyanasisFunctions() {
        TODO
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        TODO
    }
}
