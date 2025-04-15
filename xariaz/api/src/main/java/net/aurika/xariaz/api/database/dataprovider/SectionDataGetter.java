package net.aurika.xariaz.api.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface SectionDataGetter extends DataGetter {

  /**
   * Gets a sub data getter that named {@code name}.
   *
   * @param name the sub data getter name
   * @return the sub data getter
   */
  @NotNull DataGetter get(@NotNull String name);

}
