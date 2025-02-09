package net.aurika.data.api.structure;

import net.aurika.annotations.data.Immutable;
import net.aurika.checker.Checker;
import net.aurika.data.api.structure.entries.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@Immutable
public class SimpleData implements Iterable<MapDataEntry> {
    private final @NonNull Set<MapDataEntry> data;

    public static @NotNull SimpleData of(@NotNull LinkedHashSet<MapDataEntry> data) {
        return new SimpleData(data);
    }

    public static @NotNull SimpleData of(@NotNull MapDataEntry @NotNull ... data) {
        return new SimpleData(Set.of(data));
    }

    protected SimpleData(@NonNull Set<MapDataEntry> data) {
        Checker.Arg.notNull(data, "data");
        for (MapDataEntry entry : data) {
            if (entry == null) {
                throw new IllegalArgumentException("entries contains a null entry");
            }
        }
        this.data = Collections.unmodifiableSet(data);
    }

    public @Unmodifiable @NotNull Set<MapDataEntry> getData() {
        return data;
    }

    public @NotNull String @NotNull [] getKeys() {
        int size = data.size();
        String[] names = new String[size];
        int i = 0;
        for (MapDataEntry entry : data) {
            names[i] = entry.key();
            i++;
        }
        return names;
    }

    /**
     * @param key 键
     * @throws IllegalArgumentException 当没有对应的键时
     */
    public @NotNull MapDataEntry getEntry(@NotNull String key) {
        Checker.Arg.notNull(key, "key");
        for (MapDataEntry entry : data) {
            if (entry.key().equals(key)) {
                return entry;
            }
        }
        throw new IllegalArgumentException("No such key: " + key);
    }

    public int getInt(@NotNull String key) {
        return ((IntMapDataEntry) getEntry(key)).getValue();
    }

    public long getLong(@NotNull String key) {
        return ((LongMapDataEntry) getEntry(key)).getValue();
    }

    public float getFloat(@NotNull String key) {
        return ((FloatMapDataEntry) getEntry(key)).getValue();
    }

    public double getDouble(@NotNull String key) {
        return ((DoubleMapDataEntry) getEntry(key)).getValue();
    }

    public boolean getBoolean(@NotNull String key) {
        return ((BooleanMapDataEntry) getEntry(key)).getValue();
    }

    public @NotNull String getString(@NotNull String key) {
        return ((StringMapDataEntry) getEntry(key)).getValue();
    }

    @Override
    public @NotNull Iterator<MapDataEntry> iterator() {
        return new EntryIterator();
    }

    protected class EntryIterator implements Iterator<MapDataEntry> {
        private final Iterator<MapDataEntry> coreIterator = SimpleData.this.data.iterator();

        @Override
        public boolean hasNext() {
            return coreIterator.hasNext();
        }

        @Override
        public MapDataEntry next() {
            return coreIterator.next();
        }
    }
}
