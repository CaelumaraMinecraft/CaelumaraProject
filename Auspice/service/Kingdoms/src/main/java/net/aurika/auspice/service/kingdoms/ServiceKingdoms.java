package net.aurika.auspice.service.kingdoms;

import net.aurika.auspice.service.api.BukkitService;
import org.kingdoms.constants.player.KingdomPlayer;

import java.util.UUID;

public class ServiceKingdoms implements BukkitService {
    public boolean hasKingdom(UUID player) {
        return KingdomPlayer.getKingdomPlayer(player).hasKingdom();
    }

    public boolean hasNation(UUID player) {
        KingdomPlayer kp = KingdomPlayer.getKingdomPlayer(player);
        return kp.hasKingdom() && kp.getKingdom().hasNation();
    }
}
