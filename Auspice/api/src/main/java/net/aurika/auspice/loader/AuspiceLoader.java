package net.aurika.auspice.loader;

import net.aurika.auspice.main.Auspice;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public interface AuspiceLoader {
    @OverridingMethodsMustInvokeSuper
    default void init() {
        Auspice.get().setLoader(this);
    }
}
