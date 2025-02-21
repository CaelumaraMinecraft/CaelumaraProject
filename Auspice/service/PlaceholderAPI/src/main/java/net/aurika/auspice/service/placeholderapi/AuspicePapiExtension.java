package net.aurika.auspice.service.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

public class AuspicePapiExtension extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "auspice_";
    }

    @Override
    public @NotNull String getAuthor() {
        return "McKingdom";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }
}
