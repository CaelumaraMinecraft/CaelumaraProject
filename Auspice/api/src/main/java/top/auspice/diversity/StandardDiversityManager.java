package top.auspice.diversity;

import net.aurika.namespace.NSedKey;
import net.aurika.utils.Checker;
import org.jetbrains.annotations.NotNull;
import top.auspice.configs.messages.messenger.DefinedMessenger;

import java.util.Map;
import java.util.Objects;

public class StandardDiversityManager implements DiversityManager {
    private final @NotNull NSedKey id;
    private final @NotNull Diversity defaultDiversity;
    protected final @NotNull Map<@NotNull NSedKey, DefinedMessenger[]> defaultMessengers;

    public static final StandardDiversityManager STANDARD = new StandardDiversityManager();

    protected StandardDiversityManager(@NotNull NSedKey id, @NotNull Diversity defaultDiversity, @NotNull Map<NSedKey, DefinedMessenger[]> defaultMessengers) {
        Checker.Arg.notNull(id, "id");
        Checker.Arg.notNull(defaultDiversity, "defaultDiversity");
        Checker.Arg.notNull(defaultMessengers, "defaultMessengers");
        this.id = id;
        this.defaultDiversity = defaultDiversity;
        this.defaultMessengers = defaultMessengers;
    }

    public void register(@NotNull NSedKey id, @NotNull DefinedMessenger @NotNull [] defaultMessengers) {
        Checker.Arg.notNull(id, "id");
        Checker.Arg.nonNullArray(defaultMessengers, "defaultMessengers");
        if (defaultMessengers.)
    }


    public @NotNull Diversity getDefaultDiversity() {
        return defaultDiversity;
    }

    public @NotNull Map<NSedKey, DefinedMessenger[]> getDefaultMessengers() {
        return defaultMessengers;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        StandardDiversityManager that = (StandardDiversityManager) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
