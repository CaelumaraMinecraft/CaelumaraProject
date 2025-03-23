package net.aurika.auspice.constants.logs;

import net.aurika.common.key.Ident;
import org.jetbrains.annotations.NotNull;

public abstract class AuditLogConstructor implements BaseAuditLogConstructor {
    private final @NotNull Ident id;

    public AuditLogConstructor(@NotNull Ident id) {
        this.id = id;
    }

    @Override
    public final @NotNull Ident ident() {
        return id;
    }

    public abstract AuditLog construct();
}
