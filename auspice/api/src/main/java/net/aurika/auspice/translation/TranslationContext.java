package net.aurika.auspice.translation;

import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.common.annotation.Getter;
import net.aurika.common.annotation.Setter;

public interface TranslationContext {

  /**
   * Gets the {@linkplain Diversity}.
   */
  @Getter
  Diversity diversity();

  /**
   * Sets the {@linkplain Diversity}
   */
  @Setter
  TranslationContext diversity(Diversity diversity);

}
