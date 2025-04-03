package net.aurika.auspice.text.provider;

import net.aurika.auspice.text.TextObject;

public interface TextProvider {

  /**
   * Provides {@linkplain TextObject}.
   */
  TextObject provideTextObject();

}
