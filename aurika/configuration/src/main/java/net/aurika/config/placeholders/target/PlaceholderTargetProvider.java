package net.aurika.config.placeholders.target;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.config.placeholders.context.PlaceholderProvider;

import java.util.Map;

public interface PlaceholderTargetProvider extends PlaceholderProvider, BasePlaceholderTargetProvider {
    @NotNull
    Map<String, Object> getTargets();

    @Contract("-> this")
    @NotNull
    PlaceholderTargetProvider switchTargets();

    @NotNull
    default BasePlaceholderTargetProvider getTargetProviderFor(@Nullable String var1) {
        if (this.getPrimaryTarget() == null && this.getSecondaryTarget() == null) {
            return BasePlaceholderTargetProvider.EMPTY;
        } else if (var1 == null) {
            return new AbstractBasePlaceholderTargetProvider(this.getPrimaryTarget(), this.getSecondaryTarget());
        } else if (Intrinsics.areEqual(var1, "other")) {
            return new AbstractBasePlaceholderTargetProvider(this.getSecondaryTarget(), this.getPrimaryTarget());
        } else {
            Object var10000 = this.getTargets().get(var1);
            if (var10000 == null) {
                throw new IllegalArgumentException("Unknown pointer: " + var1);
            } else {
                return new AbstractBasePlaceholderTargetProvider(var10000, null);
            }
        }
    }
}
