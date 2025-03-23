package net.aurika.config.sections.label;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public final class ResolverTuple {
    private final @NotNull Label label;
    private final @NotNull Pattern regexp;

    public ResolverTuple(@NotNull Label label, @NotNull Pattern regexp) {
        Validate.Arg.notNull(label, "label");
        Validate.Arg.notNull(regexp, "regexp");
        this.label = label;
        this.regexp = regexp;
    }

    public @NotNull Label getLabel() {
        return label;
    }

    public @NotNull Pattern getRegexp() {
        return regexp;
    }

    public @NotNull String toString() {
        return "Tuple label=" + label + " regexp=" + regexp;
    }
}
