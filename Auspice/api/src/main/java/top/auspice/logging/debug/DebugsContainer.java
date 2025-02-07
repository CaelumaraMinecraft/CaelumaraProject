package top.auspice.logging.debug;

import org.jetbrains.annotations.Unmodifiable;
import net.aurika.namespace.NSedKey;

import java.util.Map;

public interface DebugsContainer<DEBUG extends Debug> {
    @Unmodifiable
    Map<NSedKey, ? extends DEBUG> getDebugs();

    void register(DEBUG debug);
}
