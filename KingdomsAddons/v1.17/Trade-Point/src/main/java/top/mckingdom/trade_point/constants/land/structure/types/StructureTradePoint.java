package top.mckingdom.trade_point.constants.land.structure.types;

import org.kingdoms.constants.land.abstraction.gui.KingdomItemGUIContext;
import org.kingdoms.constants.land.structures.Structure;
import org.kingdoms.constants.land.structures.StructureType;
import org.kingdoms.gui.InteractiveGUI;

public class StructureTradePoint extends StructureType {


    public StructureTradePoint() {
        super("trade-point");
    }

    @Override
    public InteractiveGUI open(KingdomItemGUIContext<Structure> context) {

        return null;
    }
}
