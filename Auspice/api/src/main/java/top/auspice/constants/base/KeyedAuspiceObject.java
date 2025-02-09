package top.auspice.constants.base;

import org.jetbrains.annotations.NotNull;
import net.aurika.data.object.Keyed;
import net.aurika.data.api.KeyedDataObject;

public interface KeyedAuspiceObject<K> extends AuspiceObject, Keyed<K>, KeyedDataObject<K> {
    @NotNull K getKey();

    abstract class Impl<K> extends AuspiceObject.Impl implements KeyedAuspiceObject<K> {
        private final @NotNull K key;

        public Impl(@NotNull K key) {
            this.key = key;
        }

        public @NotNull K getKey() {
            return this.key;
        }
    }

    interface WrapperImpl<K> extends KeyedAuspiceObject<K>, AuspiceObject.WrapperImpl {
        @NotNull KeyedAuspiceObject<K> getWrapped();

        @Override
        default @NotNull K getKey() {
            return this.getWrapped().getKey();
        }
    }
}
