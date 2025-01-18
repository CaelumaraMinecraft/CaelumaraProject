package top.auspice.constants.base;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import top.auspice.config.accessor.ClearlyConfigAccessor;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.configs.texts.context.provider.CascadingTextContextProvider;
import top.auspice.constants.logs.AuditLog;
import top.auspice.constants.logs.Loggable;
import top.auspice.constants.metadata.AuspiceMetadata;
import top.auspice.constants.metadata.AuspiceMetadataHandler;
import top.auspice.constants.metadata.Metadatable;
import top.auspice.utils.nonnull.NonNullMap;

import java.time.Duration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class AuspiceObject extends CompressedSmartObject implements CascadingTextContextProvider, Loggable, Metadatable {
    protected @NonNull Map<AuspiceMetadataHandler, AuspiceMetadata> metadata;
    protected @NonNull LinkedList<AuditLog> logs;
    protected long lastLogsExpirationCheck;

    protected AuspiceObject(Map<AuspiceMetadataHandler, AuspiceMetadata> metadata, LinkedList<AuditLog> logs) {
        this.setMetadata(metadata);
        this.logs = logs;
    }

    protected AuspiceObject() {
        this(new NonNullMap<>(), new LinkedList<>());
    }

    public void addMessageContextEdits(@NotNull TextPlaceholderProvider textPlaceholderProvider) {
    }

    public AuspiceMetadata getMetadata(AuspiceMetadataHandler handler) {
        return this.metadata.get(handler);
    }

    public @NotNull Map<AuspiceMetadataHandler, AuspiceMetadata> getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Map<AuspiceMetadataHandler, AuspiceMetadata> metadata) {
        this.metadata = NonNullMap.of(Objects.requireNonNull(metadata, "Metadata cannot be null"));
    }

    public <C extends AuditLog, T> T getNewestLog(Class<C> type, Function<C, T> var2) {
        this.ensureObjectExpiration();
        Iterator<AuditLog> var3 = this.getLogs().descendingIterator();

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

    public @NonNull LinkedList<AuditLog> getLogs() {
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
                Long var8 = var5.get(new String[]{nextLog.getProvider().getNamespacedKey().getConfigOptionName()}).getLong();  //todo replace to getTimeMillis()
                if (var8 == null) {
                    var8 = def;
                }

                long var9 = nextLog.getTime();
                if (var1 - var9 >= var8) {
                    var6.remove();
                }
            }

            this.lastLogsExpirationCheck = var1;
            return this.logs;
        }
    }

    public void log(@NotNull AuditLog log) {
        this.ensureObjectExpiration();
        if (!AuspiceGlobalConfig.AUDIT_LOGS_DISABLED.getStringList().contains(log.getProvider().getNamespacedKey().getConfigOptionName())) {
            this.logs.add(log);
        }
    }

}
