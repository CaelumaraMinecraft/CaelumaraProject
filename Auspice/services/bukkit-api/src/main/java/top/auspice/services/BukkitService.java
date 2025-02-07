package top.auspice.services;

import top.auspice.services.base.Service;

public interface BukkitService extends Service {
    default boolean isSupportFolia() {
        return false;
    }
}
