package top.mckingdom.props.events;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.events.KingdomsEvent;
import top.mckingdom.props.prop.PropStyle;

public abstract class KingdomPropEvent extends KingdomsEvent {

    public KingdomPropEvent(PropStyle style) {
        super();
        this.style = style;
    }
    private PropStyle style;
    public @NotNull PropStyle getPropStyle() {
        return this.style;
    }
    public void setPropStyle(PropStyle style) {
        this.style = style;
    }

}
