package net.aurika.ecliptor.history;

import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DataHandleContainer<H extends DataHolder> {

  private final @NotNull SectionableDataGetter provider;
  private final @NotNull H data;

  public DataHandleContainer(@NotNull SectionableDataGetter provider, @NotNull H data) {
    Objects.requireNonNull(provider, "provider");
    Objects.requireNonNull(data, "data");
    this.provider = provider;
    this.data = data;
  }

  public @NotNull SectionableDataGetter getProvider() {
    return this.provider;
  }

  public @NotNull H getData() {
    return this.data;
  }

}
