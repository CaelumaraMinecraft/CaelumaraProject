package top.auspice.constants.logs;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.configs.texts.context.provider.TextContextProvider;
import top.auspice.data.object.serialize.Deserializable;
import top.auspice.data.object.serialize.Serializable;
import top.auspice.data.database.dataprovider.SectionableDataGetter;
import top.auspice.data.database.dataprovider.SectionableDataSetter;
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
    public void addMessageContextEdits(@NonNull TextPlaceholderProvider var1) {
        this.addEdits(var1);
    }

    @MustBeInvokedByOverriders
    public void addEdits(@NonNull TextPlaceholderProvider var1) {
        StringBuilder var2 = TimeUtils.getDateAndTime(this.time);
        var1.raw("time", var2.toString());
    }
}
