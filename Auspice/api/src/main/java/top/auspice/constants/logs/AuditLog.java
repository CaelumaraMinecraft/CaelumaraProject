package top.auspice.constants.logs;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import net.aurika.config.placeholders.context.MessagePlaceholderProvider;
import net.aurika.text.context.provider.TextContextProvider;
import net.aurika.ecliptor.api.serialize.Deserializable;
import net.aurika.ecliptor.api.serialize.Serializable;
import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;
import net.aurika.ecliptor.database.dataprovider.SectionableDataSetter;
import top.auspice.utils.time.TimeUtils;

public abstract class AuditLog implements TextContextProvider, Serializable, Deserializable {
    protected long time = System.currentTimeMillis();

    public AuditLog() {
    }

    public abstract @NonNull AuditLogProvider getProvider();

    public void deserialize(@NonNull SectionableDataGetter dataGetter) {
        this.time = dataGetter.get("time").asLong();
    }

    public void serialize(@NonNull SectionableDataSetter dataSetter) {
        dataSetter.setLong("time", this.time);
    }

    public final long getTime() {
        return this.time;
    }

    @MustBeInvokedByOverriders
    public void addMessageContextEdits(@NonNull MessagePlaceholderProvider var1) {
        this.addEdits(var1);
    }

    @MustBeInvokedByOverriders
    public void addEdits(@NonNull MessagePlaceholderProvider var1) {
        StringBuilder var2 = TimeUtils.getDateAndTime(this.time);
        var1.raw("time", var2.toString());
    }
}
