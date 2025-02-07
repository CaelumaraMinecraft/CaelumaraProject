package top.auspice.services.kingdoms;

import org.kingdoms.constants.player.KingdomPlayer;
import top.auspice.services.base.Service;

import java.util.UUID;

public class ServiceKingdoms implements Service {
    public boolean hasKingdom(UUID player) {
        return KingdomPlayer.getKingdomPlayer(player).hasKingdom();
    }

    public boolean hasNation(UUID player) {
        KingdomPlayer kp = KingdomPlayer.getKingdomPlayer(player);
        return kp.hasKingdom() && kp.getKingdom().hasNation();
    }
}
