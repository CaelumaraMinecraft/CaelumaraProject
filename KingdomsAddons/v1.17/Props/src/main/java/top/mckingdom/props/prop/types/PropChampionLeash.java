package top.mckingdom.props.prop.types;

import org.kingdoms.constants.player.KingdomPlayer;
import top.mckingdom.props.prop.PropActiveContext;
import top.mckingdom.props.prop.PropType;

public class PropChampionLeash extends PropType {
    public PropChampionLeash() {
        super("champion-leash");
    }

    @Override
    public void active(PropActiveContext context) {
        KingdomPlayer player = context.getPlayer();
        if (player != null) {
            player.getInvasion().getChampion();               //TODO: 还是废稿

        }
    }
}
