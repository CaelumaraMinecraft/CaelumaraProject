package top.auspice.constants.base;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.abstraction.Keyed;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.constants.logs.AuditLog;
import top.auspice.constants.metadata.AuspiceMetadata;
import top.auspice.constants.metadata.AuspiceMetadataHandler;
import top.auspice.data.object.KeyedDataObject;
import top.auspice.utils.unsafe.io.ByteArrayOutputStream;

import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;

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

    interface WrapperImpl<K> extends KeyedAuspiceObject<K> {
        @NotNull KeyedAuspiceObject<K> getWrapped();

        @Override
        default @NotNull K getKey() {
            return this.getWrapped().getKey();
        }

        @Override
        default void addMessageContextEdits(@NotNull TextPlaceholderProvider textPlaceholderProvider) {
            this.getWrapped().addMessageContextEdits(textPlaceholderProvider);
        }

        @Override
        default AuspiceMetadata getMetadata(AuspiceMetadataHandler handler) {
            return this.getWrapped().getMetadata(handler);
        }

        @Override
        default @NotNull Map<AuspiceMetadataHandler, AuspiceMetadata> getMetadata() {
            return this.getWrapped().getMetadata();
        }

        @Override
        default void setMetadata(Map<AuspiceMetadataHandler, AuspiceMetadata> metadata) {
            this.getWrapped().setMetadata(metadata);
        }

        @Override
        default <C extends AuditLog, T> T getNewestLog(Class<C> type, Function<C, T> var2) {
            return this.getWrapped().getNewestLog(type, var2);
        }

        @Override
        default @NonNull LinkedList<AuditLog> getLogs() {
            return this.getWrapped().getLogs();
        }

        @Override
        default void log(@NotNull AuditLog log) {
            this.getWrapped().log(log);
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
