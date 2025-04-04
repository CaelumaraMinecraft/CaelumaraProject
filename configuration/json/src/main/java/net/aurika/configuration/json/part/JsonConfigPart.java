package net.aurika.configuration.json.part;

import com.google.gson.JsonElement;
import net.aurika.configuration.part.ConfigPart;
import org.jetbrains.annotations.NotNull;

public interface JsonConfigPart extends ConfigPart {

  /**
   * Gets the json element for this config part.
   *
   * @return the json element
   */
  @NotNull JsonElement jsonElement();

}
