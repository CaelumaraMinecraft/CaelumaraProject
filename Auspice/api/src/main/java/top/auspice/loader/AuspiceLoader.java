package top.auspice.loader;

import top.auspice.main.Auspice;

public interface AuspiceLoader {
    default void init() {
        Auspice.get().setLoader(this);
    }
}
