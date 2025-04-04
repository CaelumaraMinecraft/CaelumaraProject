package net.aurika.kingdoms.powerfulterritory.constant.land.contraction;

import org.kingdoms.constants.namespace.Lockable;
import org.kingdoms.constants.namespace.NamespacedRegistry;

public class LandContractionRegistry extends NamespacedRegistry<LandContraction> implements Lockable {

  public static boolean unLocked = true;

  public final void lock() {
    if (!unLocked) {
      throw new IllegalAccessError("Registers are already closed");
    } else {
      unLocked = false;
    }
  }

  @Override
  public void register(LandContraction contraction) {
    if (unLocked) {
      super.register(contraction);
    } else {
      throw new RuntimeException("Register is closed");   //TODO
    }
  }

}
