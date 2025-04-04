package net.aurika.configuration.json.part;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public interface JsonConfigSection extends JsonConfigPart {

  @Override
  @NotNull JsonObject jsonElement();

}
