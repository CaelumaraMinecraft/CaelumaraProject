package net.aurika.configuration.json.part;

import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.NotNull;

public interface JsonConfigScalar extends JsonConfigPart {

  @Override
  @NotNull JsonPrimitive jsonElement();

}
