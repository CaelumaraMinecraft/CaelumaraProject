package net.aurika.auspice.configs.messages.placeholders;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.auspice.configs.messages.context.MessageContext;
import net.aurika.auspice.configs.messages.placeholders.target.BasePlaceholderTargetProvider;
import net.aurika.auspice.constants.player.AuspicePlayer;
import net.aurika.auspice.server.player.OfflinePlayer;
import net.aurika.auspice.text.compiler.placeholders.types.KingdomsPlaceholder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class KingdomsPlaceholderTranslationContext {

    private final @NotNull BasePlaceholderTargetProvider placeholderContext;
    private final @NotNull KingdomsPlaceholder placeholder;

    public KingdomsPlaceholderTranslationContext(@NotNull BasePlaceholderTargetProvider placeholderContext, @NotNull KingdomsPlaceholder placeholder) {
        Objects.requireNonNull(placeholderContext, "");
        Objects.requireNonNull(placeholder, "");
        this.placeholderContext = placeholderContext;
        this.placeholder = placeholder;
    }

    public final @NotNull BasePlaceholderTargetProvider getPlaceholderContext() {
        return this.placeholderContext;
    }

    public final @NotNull KingdomsPlaceholder getPlaceholder() {
        return this.placeholder;
    }

    @NotNull
    public final MessageContext asMessageBuilder() {
        if (this.placeholderContext instanceof MessageContext) {
            return (MessageContext) this.placeholderContext;
        } else {
            MessageContext var10000 = (MessageContext.messageContext()).inheritTarget(this.placeholderContext, true);
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            return var10000;
        }
    }

    @Nullable
    public final AuspicePlayer getPlayer() {
        Object var1 = this.placeholderContext.primaryTarget();
        AuspicePlayer var10000 = var1 instanceof AuspicePlayer ? (AuspicePlayer) var1 : null;
        if (var10000 == null) {
            OfflinePlayer var2 = (var1 = this.placeholderContext.primaryTarget()) instanceof OfflinePlayer ? (OfflinePlayer) var1 : null;
            if (var2 != null) {
                var10000 = AuspicePlayer.getAuspicePlayer(var2);
                Intrinsics.checkNotNullExpressionValue(var10000, "");
                return var10000;
            }

            var10000 = null;
        }

        return var10000;
    }
//
//    @Nullable
//    public final Land getLand() {
//        Object var1;
//        LandOperator var10000 = (var1 = this.a.getPrimaryTarget()) instanceof LandOperator ? (LandOperator)var1 : null;
//        if (var10000 != null) {
//            Land var2 = var10000.getLand();
//            if (var2 != null) {
//                return var2;
//            }
//        }
//
//        KingdomsPlaceholderTranslationContext var4;
//        Object var3 = var1 = (var4 = (KingdomsPlaceholderTranslationContext)this).a.getPrimaryTarget();
//        return (var3 instanceof Player ? (Player)var1 : null) != null ? Land.getLand((var3 instanceof Player ? (Player)var1 : null).getLocation()) : null;
//    }
//
//    @Nullable
//    public final KingdomPlayer getOtherPlayer() {
//        Object var1;
//        KingdomPlayer var10000 = (var1 = this.a.getSecondaryTarget()) instanceof KingdomPlayer ? (KingdomPlayer)var1 : null;
//        if (var10000 == null) {
//            OfflinePlayer var2 = (var1 = this.a.getSecondaryTarget()) instanceof OfflinePlayer ? (OfflinePlayer)var1 : null;
//            OfflinePlayer var4 = var2;
//            if (var2 != null) {
//                KingdomsBukkitExtensions var3 = KingdomsBukkitExtensions.INSTANCE;
//                var10000 = KingdomPlayer.getKingdomPlayer(var4);
//                Intrinsics.checkNotNullExpressionValue(var10000, "");
//                return var10000;
//            }
//
//            var10000 = null;
//        }
//
//        return var10000;
//    }
//
//    @Nullable
//    public final Kingdom getOtherKingdom() {
//        Object var1;
//        Kingdom var10000 = (var1 = this.a.getSecondaryTarget()) instanceof Kingdom ? (Kingdom)var1 : null;
//        if (var10000 == null) {
//            KingdomPlayer var2 = this.getOtherPlayer();
//            if (var2 != null) {
//                return var2.getKingdom();
//            }
//
//            var10000 = null;
//        }
//
//        return var10000;
//    }
//
//    @Nullable
//    public final Nation getOtherNation() {
//        Object var1;
//        Nation var10000 = (var1 = this.a.getSecondaryTarget()) instanceof Nation ? (Nation)var1 : null;
//        if (var10000 == null) {
//            Kingdom var2 = this.getOtherKingdom();
//            if (var2 != null) {
//                return var2.getNation();
//            }
//
//            var10000 = null;
//        }
//
//        return var10000;
//    }
}
