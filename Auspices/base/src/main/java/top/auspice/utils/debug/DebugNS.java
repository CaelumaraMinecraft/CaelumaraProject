package top.auspice.utils.debug;

import net.aurika.namespace.NamespacedKeyContainer;
import net.aurika.namespace.NSKedRegistry;
import top.auspice.main.Auspice;

public interface DebugNS extends NamespacedKeyContainer {
    DebugNSRegistry REGISTRY = new DebugNSRegistry();

    final class DebugNSRegistry extends NSKedRegistry<DebugNS> {

        private DebugNSRegistry() {
            super(Auspice.get(), "DEBUG");
        }
    }
}
