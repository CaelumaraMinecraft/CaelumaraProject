package net.aurika.config.yaml.importers;

import org.jetbrains.annotations.NotNull;
import net.aurika.config.yaml.common.NodeReplacer;

import java.util.Objects;
import java.util.function.Predicate;

public final class YamlImportComposer implements Predicate<NodeReplacer.ReplacementDetails> {
    @NotNull
    public static final YamlImportComposer INSTANCE = new YamlImportComposer();

    private YamlImportComposer() {
    }

    public boolean test(@NotNull NodeReplacer.ReplacementDetails var1) {
        Objects.requireNonNull(var1);
        if (var1.sequence != null) {
            return false;
        } else if (!var1.isMapKey) {
            return false;
        } else {
            String var10000 = var1.path;
            Objects.requireNonNull(var10000);
            if (!var10000.startsWith("(import)")) {
                var10000 = var1.path;
                Objects.requireNonNull(var10000);
                return var10000.startsWith("(module)");
            }

            return true;
        }
    }
}
