package top.auspice.bukkit.services;

import org.bukkit.entity.Player;
import top.auspice.services.base.Service;

public interface ServiceAuth extends Service {
    /**
     * 是否身份验证过
     *
     * @param player 需要检查是否身份验证过的玩家
     * @return 该玩家是否身份验证过
     */
    boolean isAuthenticated(Player player);
}
