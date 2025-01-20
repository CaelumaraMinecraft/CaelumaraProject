package top.mckingdom.powerful_territory.constants.land_contractions;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;
import org.kingdoms.locale.Language;
import top.mckingdom.powerful_territory.data.LandContractions;

public abstract class LandContraction implements Namespaced {

    private final Namespace namespace;

    public LandContraction(Namespace namespace) {
        this.namespace = namespace;
    }

    @Override
    public @NonNull Namespace getNamespace() {
        return namespace;
    }

    public boolean hasContraction(Land land, KingdomPlayer player, boolean requireEffectiveContraction) {
        return LandContractions.hasLandContraction(player, land, this, requireEffectiveContraction);
    }

    //单例方法
    public abstract @Nullable ContractionLandProperties deserializeProperties(@NotNull SectionableDataGetter context);

    public abstract @NotNull String getName(Language lang);
}
