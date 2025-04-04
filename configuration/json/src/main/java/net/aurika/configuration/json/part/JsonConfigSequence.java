package net.aurika.configuration.json.part;

import com.google.gson.JsonArray;
import org.jetbrains.annotations.NotNull;

public interface JsonConfigSequence extends JsonConfigPart {

  @Override
  @NotNull JsonArray jsonElement();

}
