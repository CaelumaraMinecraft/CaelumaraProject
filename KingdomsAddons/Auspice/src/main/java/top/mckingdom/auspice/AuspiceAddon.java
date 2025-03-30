package top.mckingdom.auspice;

import org.jetbrains.annotations.NotNull;
import org.kingdoms.commands.admin.CommandAdmin;
import org.kingdoms.constants.metadata.KingdomMetadataHandler;
import org.kingdoms.constants.metadata.KingdomMetadataRegistry;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.main.Kingdoms;
import net.aurika.kingdoms.auspice.commands.admin.registry.CommandAdminRegistry;
import net.aurika.kingdoms.auspice.commands.admin.relation.CommandAdminRelation;
import net.aurika.kingdoms.auspice.configs.AuspiceLang;
import net.aurika.kingdoms.auspice.configs.AuspicePlaceholder;
import net.aurika.kingdoms.auspice.configs.CustomConfigValidators;
import net.aurika.kingdoms.auspice.costs.CurrencyRegistry;
import net.aurika.kingdoms.auspice.util.AddonTemplate;
import net.aurika.kingdoms.auspice.util.GroupExt;
import net.aurika.kingdoms.auspice.util.land.LandUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static net.aurika.kingdoms.auspice.util.KingdomsNamingContract.Namespace.Key;

public final class AuspiceAddon extends AddonTemplate {
  public static final String NAMESPACE = "Auspice";

  private static AuspiceAddon instance;

  public static @NotNull AuspiceAddon get() {
    if (instance == null) {
      throw new IllegalStateException("AuspiceAddon is not initialized yet.");
    } else {
      return instance;
    }
  }

  /**
   * Builds a {@linkplain Namespace} for Auspice addon.
   */
  @SuppressWarnings("PatternValidation")
  public static @NotNull Namespace buildNS(@Key final @NotNull String key) {
    return new Namespace(NAMESPACE, key);
  }

  private final Set<KingdomMetadataHandler> landMetadataHandlers = new HashSet<>();   // Land 的元数据存储器

  public AuspiceAddon() {
    super();
  }

  @Override
  protected void onInit0() {
    instance = this;
  }

  @Override
  public void onLoad0() {

    landMetadataHandlers.addAll(Arrays.asList(

    ));
    landMetadataHandlers.forEach(metaHandler -> {
      Kingdoms.get().getMetadataRegistry().register(metaHandler);
    });

    LanguageManager.registerMessenger(AuspiceLang.class);

    GroupExt.init();

    CurrencyRegistry.init();

    CustomConfigValidators.init();

    LandUtil.init();

    AuspicePlaceholder.init();
  }

  @Override
  public void onEnable0() {
    getLogger().info("Registering event listeners...");
    // TODO do this if have event listeners

    getLogger().info("Registering commands...");
    registerAllCommands();
  }

  @Override
  public void onDisable0() {
  }

  @Override
  public void reloadAddon0() {
    getLogger().info("Registering event listeners...");
    // TODO

    getLogger().info("Registering commands...");
    registerAllCommands();
  }

  @Override
  public void uninstall0() {
    getLogger().info("Removing auspice addon metadata...");
    KingdomMetadataRegistry.removeMetadata(Kingdoms.get().getDataCenter().getLandManager(), landMetadataHandlers);

    Kingdoms.get().getDataCenter().getKingdomManager().getKingdoms().forEach(kingdom -> {

      kingdom.getGroup().getAttributes().values().forEach(attrSet -> {
        attrSet.remove(GroupExt.DIRECTLY_TRANSFER_MEMBERS);
      });

      kingdom.getRanks().forEach(rank -> {
        rank.getPermissions().remove(GroupExt.PERMISSION_TRANSFER_MEMBERS);
      });
    });
  }

  @Override
  public @NotNull String getAddonName() {
    return "auspice";
  }

  private void registerAllCommands() {
    CommandAdmin ca = CommandAdmin.getInstance();

    new CommandAdminRelation(ca);
    new CommandAdminRegistry(ca);
  }

  {
    System.out.println("<init> called");
  }

  static {
    System.out.println("<clinit> called");
  }
}
