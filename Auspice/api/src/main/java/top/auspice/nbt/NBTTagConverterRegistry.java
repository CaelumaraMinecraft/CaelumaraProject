package top.auspice.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.nbt.tag.NBTTag;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public final class NBTTagConverterRegistry {
    @NotNull
    public static final NBTTagConverterRegistry INSTANCE = new NBTTagConverterRegistry();
    @NotNull
    private static final Map<NBTTagId, NBTConverter<NBTTag<?>, ?>> registry = new EnumMap<>(NBTTagId.class);

    private NBTTagConverterRegistry() {
    }

    public void register(@NotNull NBTConverter<? extends NBTTag<?>, ?> converter) {
        Objects.requireNonNull(converter);
        if (registry.put(converter.getType().id(), (NBTConverter<NBTTag<?>, ?>) converter) != null) {
            throw new IllegalArgumentException("Converter registered twice: " + converter.getType() + " -> " + converter);
        }
    }

    @Nullable
    public NBTConverter<NBTTag<?>, ?> get(@NotNull NBTTagId type) {
        Objects.requireNonNull(type);
        return registry.get(type);
    }
}
