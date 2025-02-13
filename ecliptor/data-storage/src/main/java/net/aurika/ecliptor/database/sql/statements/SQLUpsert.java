package net.aurika.ecliptor.database.sql.statements;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SQLUpsert extends SQLStatement {

    private final @NotNull String parameters;
    private final @NotNull String preparedValues;

    public SQLUpsert(@NotNull String parameters, @NotNull String preparedValues) {
        Objects.requireNonNull(parameters, "parameters");
        Objects.requireNonNull(preparedValues, "preparedValues");
        this.parameters = parameters;
        this.preparedValues = preparedValues;
    }

    public @NotNull String getParameters() {
        return this.parameters;
    }

    public @NotNull String getPreparedValues() {
        return this.preparedValues;
    }
}
