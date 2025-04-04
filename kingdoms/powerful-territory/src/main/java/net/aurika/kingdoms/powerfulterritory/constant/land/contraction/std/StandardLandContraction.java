package net.aurika.kingdoms.powerfulterritory.constant.land.contraction.std;

import net.aurika.kingdoms.auspice.util.MessengerUtil;
import net.aurika.kingdoms.powerfulterritory.PowerfulTerritoryAddon;
import net.aurika.kingdoms.powerfulterritory.constant.land.contraction.ContractionLandProperties;
import net.aurika.kingdoms.powerfulterritory.constant.land.contraction.LandContraction;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.data.database.dataprovider.SectionableDataGetter;
import org.kingdoms.locale.Language;
import org.kingdoms.locale.messenger.DefinedMessenger;
import org.kingdoms.locale.placeholders.context.MessagePlaceholderProvider;

import java.util.Locale;
import java.util.function.Function;

import static net.aurika.kingdoms.powerfulterritory.PowerfulTerritoryAddon.KEY;

@SuppressWarnings("unused")
public class StandardLandContraction extends LandContraction {

  public static final StandardLandContraction MANAGE_CONTRACTIONS = register(
      PowerfulTerritoryAddon.buildNS("MANAGE_CONTRACTIONS"), (x) -> null);
  public static final StandardLandContraction LAND_RULES = register(
      PowerfulTerritoryAddon.buildNS("LAND_RULES"), (x) -> null);
  public static final StandardLandContraction TURRETS = register(
      PowerfulTerritoryAddon.buildNS("TURRETS"), (x) -> null);
  public static final StandardLandContraction FARMING = register(
      PowerfulTerritoryAddon.buildNS("FARMING"), (x) -> null);
  public static final StandardLandContraction BUILD = register(PowerfulTerritoryAddon.buildNS("BUILD"), (x) -> null);
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
    return this.nameMessenger.getProvider(language).getMessage().buildPlain(
        new MessagePlaceholderProvider().lang(language));
  }

  public static StandardLandContraction register(Namespace ns, Function<SectionableDataGetter, ContractionLandProperties> deserializeProperties) {
    String key = ns.getKey().toLowerCase(Locale.ENGLISH).replace('_', '-');
    StandardLandContraction c = new StandardLandContraction(
        ns, MessengerUtil.createMessenger(new String[]{KEY, "land-contraction", key}, key),
        deserializeProperties
    );
    PowerfulTerritoryAddon.get().landContractionRegistry().register(c);
    return c;
  }

  public static void init() {

  }

  static {
    if (Bukkit.getPluginManager().getPlugin("QuickShop") != null) {
      SHOP = register(PowerfulTerritoryAddon.buildNS("SHOP"), (x) -> null);
    } else {
      SHOP = null;
    }
  }
}
