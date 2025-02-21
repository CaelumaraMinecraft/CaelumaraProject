package net.aurika.ecliptor.database.dataprovider;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

final class AbstractDynamicSection implements DynamicSection {

    private final @NotNull SectionableDataSetter setter;

    public AbstractDynamicSection(@NotNull SectionableDataSetter setter) {
        Objects.requireNonNull(setter, "setter");
        this.setter = setter;
    }

    @Override
    public @NotNull SectionableDataSetter setter() {
        return this.setter;
    }

    @Override
    public void close() {
    }
}
