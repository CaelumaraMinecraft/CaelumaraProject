package top.auspice.configs.messages;

import net.aurika.util.Checker;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import top.auspice.diversity.MessengerManageKey;

import java.util.regex.Pattern;

public class MessageEntry {
    private final @NotNull MessengerManageKey scope;
    private final @NotNull String @NotNull [] entry;
    @Language("RegExp")
    public static final String ALLOWED_SECTION = "(AZaz) + (AZaz-) + (AZaz)";
    public static final Pattern SECTION_PATTERN = Pattern.compile(ALLOWED_SECTION);

    public MessageEntry(@NotNull MessengerManageKey scope, @NotNull String @NotNull [] entry) {
        this(scope, entry, true);
    }

    protected MessageEntry(@NotNull MessengerManageKey scope, @NotNull String @NotNull [] entry, boolean validate) {
        Checker.Arg.notNull(scope, "scope");
        Checker.Arg.notNull(entry, "entry");
        this.scope = scope;
        this.entry = entry;
        if (validate) {
            for (int i = 0; i < entry.length; i++) {
                String sec = entry[i];
                if (!SECTION_PATTERN.matcher(sec).matches()) {
                    throw new IllegalArgumentException("Invalid entry section at index " + i + " : " + sec);
                }
            }
        }
    }

    public @NotNull MessengerManageKey getScope() {
        return scope;
    }

    public @NotNull String @NotNull [] getEntry() {
        return entry;
    }
}
