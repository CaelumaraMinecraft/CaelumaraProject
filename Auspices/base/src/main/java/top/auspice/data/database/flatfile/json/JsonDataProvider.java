package top.auspice.data.database.flatfile.json;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.flatfile.ObjectDataProvider;

public interface JsonDataProvider
extends ObjectDataProvider {
    @NotNull
    public JsonElement getElement();

    @Override
    @NotNull
    default public Object getRawDataObject() {
        return this.getElement();
    }
}
 