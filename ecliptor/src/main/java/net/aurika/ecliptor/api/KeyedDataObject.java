package net.aurika.ecliptor.api;

import org.jetbrains.annotations.NotNull;

public interface KeyedDataObject<K> extends DataObject, Keyed<K> {

    @Override
    @NotNull K dataKey();

    class Impl<K> extends DataObject.Impl implements KeyedDataObject<K>, Keyed<K> {
        private final @NotNull K key;

        public Impl(@NotNull K key) {
            this.key = key;
        }

        @Override
        public @NotNull K dataKey() {
            return key;
        }
    }

    interface Wrapper<K> extends KeyedDataObject<K>, DataObject.Wrapper {

        @Override
        @NotNull KeyedDataObject<K> getWrapped();

        @Override
        default @NotNull K dataKey() {
            return this.getWrapped().dataKey();
        }
    }
}
