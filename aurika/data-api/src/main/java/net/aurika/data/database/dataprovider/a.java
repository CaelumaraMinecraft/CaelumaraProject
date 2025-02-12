package net.aurika.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

final class a implements DynamicSection {

    private final @NotNull SectionableDataSetter setter;

    public a(@NotNull SectionableDataSetter setter) {
        Objects.requireNonNull(setter, "setter");
        this.setter = setter;
    }

    @Override
    public @NotNull SectionableDataSetter getSetter() {
        return this.setter;
    }

    @Override
    public void close() {
    }
}
