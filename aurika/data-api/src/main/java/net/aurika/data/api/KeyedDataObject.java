package net.aurika.data.api;

import org.jetbrains.annotations.NotNull;

public interface KeyedDataObject<K> extends DataObject, Keyed<K> {
    @NotNull K getKey();

    class Impl<K> extends DataObject.Impl implements KeyedDataObject<K>, Keyed<K> {
        private final @NotNull K key;

        public Impl(@NotNull K key) {
            this.key = key;
        }

        public @NotNull K getKey() {
            return this.key;
        }
    }

    interface WrapperImpl<K> extends KeyedDataObject<K>, DataObject.WrapperImpl {

        @NotNull KeyedDataObject<K> getWrapped();

        @Override
        default @NotNull K getKey() {
            return this.getWrapped().getKey();
        }
    }
}
