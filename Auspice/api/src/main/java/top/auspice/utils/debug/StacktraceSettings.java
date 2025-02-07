package top.auspice.utils.debug;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

public final class StacktraceSettings {
    @NotNull
    public static final StacktraceSettings INSTANCE = new StacktraceSettings();
    public boolean isWhitelist = true;
    @NotNull
    public Set<DebugNS> list = new LinkedHashSet<>();

    private StacktraceSettings() {
    }
}