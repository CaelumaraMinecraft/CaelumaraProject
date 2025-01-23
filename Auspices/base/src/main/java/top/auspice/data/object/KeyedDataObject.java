package top.auspice.data.object;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.abstraction.Keyed;
import top.auspice.utils.unsafe.io.ByteArrayOutputStream;

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

        @Override
        default @Nullable ByteArrayOutputStream getObjectState() {
            return this.getWrapped().getObjectState();
        }

        @Override
        default void setObjectState(@Nullable ByteArrayOutputStream objectState) {
            this.getWrapped().setObjectState(objectState);
        }
    }
}
