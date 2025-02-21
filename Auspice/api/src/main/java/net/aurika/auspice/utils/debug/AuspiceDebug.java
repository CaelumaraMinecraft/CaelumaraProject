package net.aurika.auspice.utils.debug;

import net.aurika.auspice.main.Auspice;
import net.aurika.common.key.Ident;
import net.aurika.common.key.KeyPatterns;
import org.jetbrains.annotations.NotNull;

public enum AuspiceDebug implements DebugEntry {
    FOLDER_REGISTRY("file.folder_registry"),
    SAVE_ALL("data.save.save_all");

    private final DebugIdent id;

    AuspiceDebug(@KeyPatterns.IdentValue final @NotNull String identValueString) {
        this.id = new DebugIdent(Ident.ident(Auspice.NAMESPACE, identValueString));
    }

    @Override
    public @NotNull DebugIdent ident() {
        return this.id;
    }
}
