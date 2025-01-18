package top.auspice.folia.services.base;

import top.auspice.bukkit.services.BukkitService;

public interface FoliaService extends BukkitService {
    @Override
    default boolean isSupportFolia() {
        return true;
    }
}
