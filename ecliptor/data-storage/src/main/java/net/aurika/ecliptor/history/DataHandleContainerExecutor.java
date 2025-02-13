package net.aurika.ecliptor.history;

import org.jetbrains.annotations.NotNull;
import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;

import java.util.Objects;
import java.util.function.Consumer;

public final class DataHandleContainerExecutor<H extends DataHolder> implements TemporaryDataHandler<H> {

    private final @NotNull Consumer<DataHandleContainer<H>> containerHandle;

    public DataHandleContainerExecutor(@NotNull Consumer<DataHandleContainer<H>> containerHandle) {
        Objects.requireNonNull(containerHandle, "containerHandle");
        this.containerHandle = containerHandle;
    }

    public @NotNull Consumer<DataHandleContainer<H>> getContainerHandle() {
        return this.containerHandle;
    }

    public void load(@NotNull SectionableDataGetter provider, @NotNull H dataHolder) {
        Objects.requireNonNull(provider, "provider");
        Objects.requireNonNull(dataHolder, "dataHolder");
        this.containerHandle.accept(new DataHandleContainer<>(provider, dataHolder));
    }
}
