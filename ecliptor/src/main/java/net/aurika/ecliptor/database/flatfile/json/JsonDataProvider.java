package net.aurika.ecliptor.database.flatfile.json;

import com.google.gson.JsonElement;
import net.aurika.ecliptor.database.flatfile.ObjectDataProvider;
import org.jetbrains.annotations.NotNull;

public interface JsonDataProvider extends ObjectDataProvider {

  @NotNull JsonElement jsonElement();

  @Override
  default @NotNull Object rawDataObject() {
    return this.jsonElement();
  }

}
 