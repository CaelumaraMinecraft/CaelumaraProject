package net.aurika.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

final class a implements DynamicSection {
    @NotNull
    private final SectionableDataSetter setter;

    public a(@NotNull SectionableDataSetter setter) {
        Objects.requireNonNull(setter, "setter");
        this.setter = setter;
    }

    @NotNull
    public SectionableDataSetter getSetter() {
        return this.setter;
    }

    public void close() {
    }
}
