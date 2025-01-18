package top.auspice.data.database.sql.statements;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Objects;

public class SQLBackup extends SQLStatement {
    @NotNull
    private final Path to;
    @NotNull
    private final String named;

    public SQLBackup(@NotNull Path var1, @NotNull String var2) {
        Objects.requireNonNull(var1, "");
        Objects.requireNonNull(var2, "");
        this.to = var1;
        this.named = var2;
    }

    @NotNull
    public final Path getTo() {
        return this.to;
    }

    @NotNull
    public final String getNamed() {
        return this.named;
    }
}
