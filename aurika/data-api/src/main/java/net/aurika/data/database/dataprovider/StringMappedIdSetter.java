package net.aurika.data.database.dataprovider;

import net.aurika.checker.Checker;
import net.aurika.data.api.bundles.BundledData;
import net.aurika.util.uuid.FastUUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class StringMappedIdSetter implements MappedIdSetter {  // SimpleData   ComponentsDataLike

    private final @NotNull Function<String, SectionCreatableDataSetter> handler;

    private @Nullable String str;

    public StringMappedIdSetter(@NotNull Function<String, SectionCreatableDataSetter> var1) {
        Checker.Arg.notNull(var1, "");
        this.handler = var1;
    }

    private static UnsupportedOperationException unsupported(String type) {
        return new UnsupportedOperationException(type + " ID type is not supported");
    }

    @Override
    public @NotNull SectionCreatableDataSetter getValueProvider() {
        if (str == null) {
            throw new IllegalStateException("Cannot get value provider before setting the ID");
        } else {
            return handler.apply(str);
        }
    }

    @Override
    public void setInt(int value) {
        this.str = String.valueOf(value);
    }

    @Override
    public void setLong(long value) {
        this.str = String.valueOf(value);
    }

    @Override
    public void setFloat(float value) {
        throw unsupported("Float");
    }

    @Override
    public void setDouble(double value) {
        throw unsupported("Double");
    }

    @Override
    public void setBoolean(boolean value) {
        throw unsupported("Boolean");
    }

    @Override
    public void setString(@Nullable String value) {
        this.str = value;
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        this.str = value != null ? FastUUID.toString(value) : null;
    }

    @Override
    public void setObject(@Nullable BundledData value) {
//        str = value != null ? value.asPlainDataString() : null;
    }

    @Override
    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> handler) {
        throw unsupported("Collection");
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler) {
        throw unsupported("Map");
    }
}
