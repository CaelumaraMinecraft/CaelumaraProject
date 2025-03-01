package top.mckingdom.props.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import top.mckingdom.props.prop.ActiveType;
import top.mckingdom.props.prop.PropStyle;

public class KingdomPropActiveEvent extends KingdomPropEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private ActiveType activeType;

    public KingdomPropActiveEvent(PropStyle style, ActiveType activeType) {
        super(style);
        this.activeType = activeType;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public ActiveType getActiveType() {
        return activeType;
    }

    public void setActiveType(ActiveType activeType) {
        this.activeType = activeType;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
