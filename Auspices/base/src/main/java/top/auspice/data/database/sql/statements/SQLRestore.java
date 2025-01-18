package top.auspice.data.database.sql.statements;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Objects;

public class SQLRestore extends SQLStatement {
    @NotNull
    private final Path from;

    public SQLRestore(@NotNull Path var1) {
        Objects.requireNonNull(var1);
        this.from = var1;
    }

    @NotNull
    public final Path getFrom() {
        return this.from;
    }
}
