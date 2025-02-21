package net.aurika.auspice.constants.base;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import net.aurika.config.accessor.ClearlyConfigAccessor;
import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.auspice.text.context.provider.CascadingTextContextProvider;
import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.constants.logs.AuditLog;
import net.aurika.auspice.constants.logs.Loggable;
import net.aurika.auspice.constants.metadata.AuspiceMetadata;
import net.aurika.auspice.constants.metadata.AuspiceMetadataHandler;
import net.aurika.auspice.constants.metadata.Metadatable;
import net.aurika.ecliptor.api.DataObject;
import top.auspice.utils.ZeroArrays;
import net.aurika.util.collection.nonnull.NonNullMap;

import java.time.Duration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public interface AuspiceObject extends DataObject, CascadingTextContextProvider, Loggable, Metadatable {

    @Override
    void addMessageContextEdits(@NotNull MessageContextImpl textPlaceholderProvider);

    @Override
    AuspiceMetadata getMetadata(AuspiceMetadataHandler handler);

    @Override
    @NotNull Map<AuspiceMetadataHandler, AuspiceMetadata> getMetadata();

    void setMetadata(Map<AuspiceMetadataHandler, AuspiceMetadata> metadata);

    <C extends AuditLog, T> T getNewestLog(Class<C> type, Function<C, T> var2);

    @Override
    @NonNull
    LinkedList<AuditLog> logs();

    @Override
    void log(@NotNull AuditLog log);

    abstract class Impl implements
            AuspiceObject,
            DataObject, CascadingTextContextProvider, Loggable, Metadatable,
            DataObject.WrapperImpl {

        private final DataObject wrapped = new DataObject.Impl();

        protected @NonNull Map<AuspiceMetadataHandler, AuspiceMetadata> metadata;
        protected @NonNull LinkedList<AuditLog> logs;
        protected long lastLogsExpirationCheck;

        protected Impl(Map<AuspiceMetadataHandler, AuspiceMetadata> metadata, @NotNull LinkedList<AuditLog> logs) {
            this.setMetadata(metadata);
            this.logs = logs;
        }

        protected Impl() {
            this(new NonNullMap<>(), new LinkedList<>());
        }

        @Override
        public void addMessageContextEdits(@NotNull MessageContextImpl textPlaceholderProvider) {
        }

        @Override
        public AuspiceMetadata getMetadata(AuspiceMetadataHandler handler) {
            return this.metadata.get(handler);
        }

        @Override
        public @NotNull Map<AuspiceMetadataHandler, AuspiceMetadata> getMetadata() {
            return this.metadata;
        }

        @Override
        public void setMetadata(Map<AuspiceMetadataHandler, AuspiceMetadata> metadata) {
            Objects.requireNonNull(metadata, "Metadata cannot be null");
            this.metadata = NonNullMap.of(metadata);
        }

        @Override
        public <C extends AuditLog, T> T getNewestLog(Class<C> type, Function<C, T> var2) {
            this.ensureObjectExpiration();
            Iterator<AuditLog> var3 = this.logs().descendingIterator();

            AuditLog log;
            T var5;
            do {
                if (!var3.hasNext()) {
                    return null;
                }

                log = var3.next();
            } while (!type.isInstance(log) || (var5 = var2.apply((C) log)) != null);

            return var5;
        }

        @Override
        public @NonNull LinkedList<AuditLog> logs() {
            this.ensureObjectExpiration();
            long var1 = System.currentTimeMillis();
            if (var1 - this.lastLogsExpirationCheck < Duration.ofHours(1L).toMillis()) {
                return this.logs;
            } else {
                Long def = AuspiceGlobalConfig.AUDIT_LOGS_EXPIRATION_DEFAULT.getTimeMillis();
                ClearlyConfigAccessor var5 = AuspiceGlobalConfig.AUDIT_LOGS_EXPIRATION.getManager().getSection().noDefault();
                Iterator<AuditLog> var6 = this.logs.iterator();

                while (var6.hasNext()) {
                    AuditLog nextLog = var6.next();
                    Long var8 = var5.get(new String[]{nextLog.constructor().getNamespacedKey().getConfigOptionName()}).getLong(ZeroArrays.STRING);  // TODO replace to getTimeMillis()
                    if (var8 == null) {
                        var8 = def;
                    }

                    long var9 = nextLog.time();
                    if (var1 - var9 >= var8) {
                        var6.remove();
                    }
                }

                this.lastLogsExpirationCheck = var1;
                return this.logs;
            }
        }

        @Override
        public void log(@NotNull AuditLog log) {
            this.ensureObjectExpiration();
            if (!AuspiceGlobalConfig.AUDIT_LOGS_DISABLED.getStringList().contains(log.constructor().getNamespacedKey().getConfigOptionName())) {
                this.logs.add(log);
            }
        }

        @Override
        public @NotNull DataObject getWrapped() {
            return this.wrapped;
        }
    }

    interface WrapperImpl extends AuspiceObject, DataObject.WrapperImpl {
        @NotNull AuspiceObject getWrapped();

        @Override
        default void addMessageContextEdits(@NotNull MessageContextImpl textPlaceholderProvider) {
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
        default @NonNull LinkedList<AuditLog> logs() {
            return this.getWrapped().logs();
        }

        @Override
        default void log(@NotNull AuditLog log) {
            this.getWrapped().log(log);
        }
    }
}
