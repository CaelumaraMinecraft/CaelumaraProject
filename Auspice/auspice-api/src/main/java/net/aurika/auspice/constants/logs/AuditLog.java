package net.aurika.auspice.constants.logs;

import net.aurika.auspice.configs.messages.context.MessageContextProvider;
import net.aurika.auspice.configs.messages.context.MessageContext;
import net.aurika.auspice.utils.time.TimeUtils;
import net.aurika.common.annotations.Getter;
import net.aurika.common.annotations.Setter;
import net.aurika.ecliptor.api.serialize.Deserializable;
import net.aurika.ecliptor.api.serialize.Serializable;
import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;
import net.aurika.ecliptor.database.dataprovider.SectionableDataSetter;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;

public abstract class AuditLog implements MessageContextProvider, Serializable, Deserializable {
    protected long time = System.currentTimeMillis();

    public AuditLog() {
    }

    public abstract @NotNull AuditLogConstructor constructor();

    @MustBeInvokedByOverriders
    public void deserialize(@NotNull SectionableDataGetter dataGetter) {
        this.time = dataGetter.get("time").asLong();
    }

    @MustBeInvokedByOverriders
    public void serialize(@NotNull SectionableDataSetter dataSetter) {
        dataSetter.setLong("time", this.time);
    }

    @Getter
    public final long time() {
        return this.time;
    }

    @Setter
    public final void time(long time) {
        this.time = time;
    }

    @MustBeInvokedByOverriders
    public void addMessageContextEdits(@NotNull MessageContext messageContext) {
        addEdits(messageContext);
    }

    @MustBeInvokedByOverriders
    public void addEdits(@NotNull MessageContext messageContext) {
        messageContext.raw("time", TimeUtils.getDateAndTime(this.time).toString());
    }
}
