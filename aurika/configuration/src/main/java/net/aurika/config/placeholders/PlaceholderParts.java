package net.aurika.config.placeholders;

import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public final class PlaceholderParts {
    @NotNull
    private final String full;
    @Nullable
    private List<String> placeholder;

    public PlaceholderParts(@NotNull String full) {
        Objects.requireNonNull(full);
        this.full = full;
        if (this.full.isEmpty()) {
            throw new IllegalArgumentException("Placeholder parts cannot be empty: " + this.full);
        }
    }

    @NotNull
    public String getFull() {
        return this.full;
    }

    @NotNull
    public List<String> getParts() {
        if (this.placeholder == null) {
            this.placeholder = StringsKt.split(this.full, new char[]{'_'}, false, 0);
        }

        return Objects.requireNonNull(this.placeholder);
    }

    @NotNull
    public String getId() {
        return this.getParts().get(0);
    }

    public boolean isTerminal() {
        return this.getParts().size() == 1;
    }

    @NotNull
    public String getParameterFrom(int var1) {
        return Objects.requireNonNull(String.join("_", this.getParts().subList(var1, this.getParts().size())));
    }

    @NotNull
    public String toString() {
        return "PlaceholderParts(full='" + this.full + "', placeholder=" + this.getParts() + ')';
    }
}

