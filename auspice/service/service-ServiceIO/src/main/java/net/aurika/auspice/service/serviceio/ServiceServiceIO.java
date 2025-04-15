package net.aurika.auspice.service.serviceio;

import net.aurika.auspice.service.api.BukkitServiceEconomy;
import net.aurika.auspice.service.vault.ServiceVault;

/**
 * A service to service ServiceIO.
 */
public class ServiceServiceIO extends ServiceVault implements BukkitServiceEconomy {

  @Override
  public boolean isSupportFolia() {
    return true;
  }

}
