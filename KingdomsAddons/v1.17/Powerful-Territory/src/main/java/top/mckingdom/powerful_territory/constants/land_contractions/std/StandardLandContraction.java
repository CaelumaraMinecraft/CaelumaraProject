package top.mckingdom.powerful_territory.constants.land_contractions.std;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;
import org.kingdoms.locale.Language;
import org.kingdoms.locale.messenger.DefinedMessenger;
import org.kingdoms.locale.placeholders.context.MessagePlaceholderProvider;
import top.mckingdom.auspice.utils.MessengerUtil;
import top.mckingdom.powerful_territory.PowerfulTerritory;
import top.mckingdom.powerful_territory.constants.land_contractions.ContractionLandProperties;
import top.mckingdom.powerful_territory.constants.land_contractions.LandContraction;

import java.util.Locale;
import java.util.function.Function;

@SuppressWarnings("unused")
public class StandardLandContraction extends LandContraction {

    public static final StandardLandContraction MANAGE_CONTRACTIONS = register(PowerfulTerritory.buildNS("MANAGE_CONTRACTIONS"), (x) -> null);
    public static final StandardLandContraction LAND_RULES = register(PowerfulTerritory.buildNS("LAND_RULES"), (x) -> null);
    public static final StandardLandContraction TURRETS = register(PowerfulTerritory.buildNS("TURRETS"), (x) -> null);
    public static final StandardLandContraction FARMING = register(PowerfulTerritory.buildNS("FARMING"), (x) -> null);
    public static final StandardLandContraction BUILD = register(PowerfulTerritory.buildNS("BUILD"), (x) -> null);
    public static final StandardLandContraction SHOP;


    private final DefinedMessenger nameMessenger;
    private final Function<SectionableDataGetter, ContractionLandProperties> deserializeProperties;

    public StandardLandContraction(Namespace namespace, DefinedMessenger nameMessenger, Function<SectionableDataGetter, ContractionLandProperties> deserializeProperties) {
        super(namespace);
        this.nameMessenger = nameMessenger;
        this.deserializeProperties = deserializeProperties;
    }


    @Nullable
    @Override
    public ContractionLandProperties deserializeProperties(@NotNull SectionableDataGetter context) {
        return deserializeProperties.apply(context);
    }

    @NotNull
    @Override
    public String getName(Language language) {
        return this.nameMessenger.getProvider(language).getMessage().buildPlain(new MessagePlaceholderProvider().lang(language));
    }

    public static StandardLandContraction register(Namespace ns, Function<SectionableDataGetter, ContractionLandProperties> deserializeProperties) {
        String key = ns.getKey().toLowerCase(Locale.ENGLISH).replace('_', '-');
        StandardLandContraction c = new StandardLandContraction(ns, MessengerUtil.createMessenger(new String[]{"powerful-territory", "constants-contraction", key}, key), deserializeProperties);
        PowerfulTerritory.get().getLandContractionRegistry().register(c);
        return c;
    }

    public static void init() {

    }

    static {
        if (Bukkit.getPluginManager().getPlugin("QuickShop") != null) {
            SHOP = register(PowerfulTerritory.buildNS("SHOP"), (x) -> null);
        } else {
            SHOP = null;
        }
    }


}
