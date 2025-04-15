package net.aurika.auspice.service.api;

public interface BukkitService extends Service {

  default boolean isSupportFolia() {
    return false;
  }

}
