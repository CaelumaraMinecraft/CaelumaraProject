package net.aurika.data.database.flatfile.json;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import net.aurika.data.database.flatfile.ObjectDataProvider;

public interface JsonDataProvider extends ObjectDataProvider {
    @NotNull JsonElement getElement();

    @Override
    default @NotNull Object getRawDataObject() {
        return this.getElement();
    }
}
 