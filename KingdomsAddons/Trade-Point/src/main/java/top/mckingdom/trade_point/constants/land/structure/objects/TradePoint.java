package top.mckingdom.trade_point.constants.land.structure.objects;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.land.FuelContainer;
import org.kingdoms.constants.land.abstraction.data.SerializationContext;
import org.kingdoms.constants.land.structures.Structure;
import org.kingdoms.constants.land.structures.StructureStyle;
import org.kingdoms.data.database.dataprovider.SectionableDataSetter;
import org.kingdoms.locale.placeholders.context.MessagePlaceholderProvider;

import java.util.Set;
import java.util.UUID;

public class TradePoint extends Structure implements FuelContainer {

    private Set<Kingdom> alias;
    private UUID tradePointId;

    @Nullable
    private Kingdom owner;

    private double fuel;

    public TradePoint(String world, int x, int y, int z, StructureStyle style) {
        super(world, x, y, z, style);
    }

    @Override
    public MessagePlaceholderProvider addMessageContextEdits(MessagePlaceholderProvider messagePlaceholderProvider, Kingdom kingdom) {
        return super.addMessageContextEdits(messagePlaceholderProvider, kingdom);
    }

    public boolean hasOwner() {
        return this.owner != null;
    }

    @Nullable
    public Kingdom getOwner() {
        return owner;
    }

    public void setOwner(@Nullable Kingdom owner) {
        this.owner = owner;
    }

    @Override
    public double getFuel() {
        return this.fuel;
    }

    @Override
    public void setFuel(double v) {
        this.fuel = v;
    }

    @Override
    public double getMaxFuel() {
        return 0;
    } //todo

    @Override
    public void serialize(SerializationContext<SectionableDataSetter> context) {
        super.serialize(context);
        SectionableDataSetter data = context.getDataProvider();
        data
    }
}
