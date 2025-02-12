package net.aurika.data.api.bundles;

import net.aurika.annotations.data.Immutable;
import net.aurika.checker.Checker;
import net.aurika.data.api.bundles.entries.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@Immutable
public class BundledData implements Iterable<SimpleMappingDataEntry> {   // TODO rename
    private final @NonNull Set<SimpleMappingDataEntry> data;

    public static @NotNull BundledData of(@NotNull LinkedHashSet<SimpleMappingDataEntry> data) {
        return new BundledData(data);
    }

    public static @NotNull BundledData of(@NotNull SimpleMappingDataEntry @NotNull ... data) {
        return new BundledData(Set.of(data));
    }

    protected BundledData(@NonNull Set<SimpleMappingDataEntry> data) {
        Checker.Arg.notNull(data, "data");
        for (SimpleMappingDataEntry entry : data) {
            if (entry == null) {
                throw new IllegalArgumentException("entries contains a null entry");
            }
        }
        this.data = Collections.unmodifiableSet(data);
    }

    public @Unmodifiable @NotNull Set<SimpleMappingDataEntry> getData() {
        return data;
    }

    public @NotNull String @NotNull [] getKeys() {
        int size = data.size();
        String[] names = new String[size];
        int i = 0;
        for (SimpleMappingDataEntry entry : data) {
            names[i] = entry.key();
            i++;
        }
        return names;
    }

    /**
     * @param key 键
     * @throws IllegalArgumentException 当没有对应的键时
     */
    public @NotNull SimpleMappingDataEntry getEntry(@NotNull String key) {
        Checker.Arg.notNull(key, "key");
        for (SimpleMappingDataEntry entry : data) {
            if (entry.key().equals(key)) {
                return entry;
            }
        }
        throw new IllegalArgumentException("No such key: " + key);
    }

    public int getInt(@NotNull String key) {
        return ((IntEntry) getEntry(key)).getValue();
    }

    public long getLong(@NotNull String key) {
        return ((LongEntry) getEntry(key)).getValue();
    }

    public float getFloat(@NotNull String key) {
        return ((FloatEntry) getEntry(key)).getValue();
    }

    public double getDouble(@NotNull String key) {
        return ((DoubleEntry) getEntry(key)).getValue();
    }

    public boolean getBoolean(@NotNull String key) {
        return ((BooleanEntry) getEntry(key)).getValue();
    }

    public @NotNull String getString(@NotNull String key) {
        return ((StringEntry) getEntry(key)).getValue();
    }

//    public @NotNull String asPlainDataString() {
//        Object[] components = new Object[data.size()];
//        int i = 0;
//        for (SimpleMappingDataEntry entry : data) {
//            components[i++] = entry;
//        }
//        return CommaDataSplitStrategy.toString(components);
//    }

    @Override
    public @NotNull Iterator<SimpleMappingDataEntry> iterator() {
        return new EntryIterator();
    }

    protected class EntryIterator implements Iterator<SimpleMappingDataEntry> {
        private final Iterator<SimpleMappingDataEntry> coreIterator = BundledData.this.data.iterator();

        @Override
        public boolean hasNext() {
            return coreIterator.hasNext();
        }

        @Override
        public SimpleMappingDataEntry next() {
            return coreIterator.next();
        }
    }
}
