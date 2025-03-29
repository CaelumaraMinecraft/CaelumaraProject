package net.aurika.auspice.loader;

import net.aurika.auspice.user.Auspice;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public interface AuspiceLoader {

  @OverridingMethodsMustInvokeSuper
  default void init() {
    Auspice.get().setLoader(this);
  }

}
