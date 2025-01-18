package top.auspice.config.sections.label;

import org.jetbrains.annotations.NotNull;
import top.auspice.utils.Checker;

import java.util.regex.Pattern;

public final class ResolverTuple {
    private final @NotNull Label label;
    private final @NotNull Pattern regexp;

    public ResolverTuple(@NotNull Label label, @NotNull Pattern regexp) {
        Checker.Argument.checkNotNull(label, "label");
        Checker.Argument.checkNotNull(regexp, "regexp");
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
