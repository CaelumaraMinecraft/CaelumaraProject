package top.auspice.utils.debug;

import top.auspice.key.NSKeyed;
import top.auspice.key.NSKedRegistry;
import top.auspice.main.Auspice;

public interface DebugNS extends NSKeyed {
    DebugNSRegistry REGISTRY = new DebugNSRegistry();

    final class DebugNSRegistry extends NSKedRegistry<DebugNS> {

        private DebugNSRegistry() {
            super(Auspice.get(), "DEBUG");
        }
    }
}
