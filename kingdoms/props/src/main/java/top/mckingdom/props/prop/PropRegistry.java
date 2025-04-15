package top.mckingdom.props.prop;

import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.kingdoms.config.managers.ConfigManager;
import org.kingdoms.libs.snakeyaml.validation.NodeValidator;
import org.kingdoms.libs.snakeyaml.validation.Validator;
import org.kingdoms.main.Kingdoms;
import org.kingdoms.nbt.tag.NBTTagCompound;
import org.kingdoms.nbt.tag.NBTTagType;
import org.kingdoms.platform.bukkit.item.ItemNBT;
import org.kingdoms.utils.Validate;
import org.kingdoms.utils.config.CustomConfigValidators;
import org.kingdoms.utils.config.FolderYamlRegistry;
import org.kingdoms.utils.config.adapters.YamlContainer;
import org.kingdoms.utils.config.importer.YamlGlobalImporter;
import org.kingdoms.utils.string.Strings;
import top.mckingdom.props.prop.types.PropChampionLeash;
import top.mckingdom.props.prop.types.PropRespawnStructurePlacer;
import top.mckingdom.props.prop.types.turret_props.jammer.PropChunkTurretJammer;
import top.mckingdom.props.prop.types.turret_props.jammer.PropRangedTurretJammer;
import top.mckingdom.props.prop.types.turret_props.jammer.PropSingleTurretJammer;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class PropRegistry {
  private static final PropRegistry INSTANCE = new PropRegistry();
  private static final Map<String, PropType> types = new HashMap<>();
  private static final Map<String, PropStyle> styles = new HashMap<>();
  private static final NodeValidator SCHEMA = YamlContainer.parseValidator("Prop Schema", "schemas/Props/prop.yml");
  public static final Path PROPS_PATH = Kingdoms.getPath("Props");

  public PropType registerType(PropType type) {
    Objects.requireNonNull(type, "Cannot register structures type with null type");
    String var2 = Strings.deleteWhitespace(type.getName().toLowerCase(Locale.ENGLISH));
    Validate.isTrue(!types.containsKey(var2), "Kingdom Prop type '" + var2 + "' is already registered");
    return types.put(var2, type);
  }

  public PropStyle registerStyle(PropStyle style) {
    style.loadSettings();
    return styles.put(style.getName(), style);
  }

  public static void validate(String styleName, YamlContainer yaml) {
    ConfigManager.warnAboutValidation("Props/" + styleName, Validator.validate(yaml.getConfig().getNode(), SCHEMA, CustomConfigValidators.getValidators()));
  }

  public static PropRegistry get() {
    return INSTANCE;
  }

  public void init() {
    types.clear();
    registerType(new PropChampionLeash());
    registerType(new PropRespawnStructurePlacer());
    registerType(new PropChunkTurretJammer());
    registerType(new PropRangedTurretJammer());
    registerType(new PropSingleTurretJammer());
    styles.clear();
    (new FolderYamlRegistry("Prop", "Props", (styleName, yaml) -> {    //parm1: display name, param2: folder path, param3:
      validate(styleName, yaml);
      yaml.setImporter(YamlGlobalImporter.INSTANCE).importDeclarations();
      this.registerStyle(new PropStyle(styleName, yaml));
    })).useDefaults(false).copyDefaults(false).register();
  }

  public PropStyle getStyle(String s) {
    return styles.get(s);
  }

  public Map<String, PropType> getTypes() {
    return types;
  }

  public Map<String, PropStyle> getStyles() {
    return styles;
  }

  public PropType getType(String s) {
    return types.get(s);
  }

  public static @Nullable PropStyle tryGetStyle(ItemStack item) {
    return tryGetStyle(ItemNBT.getTag(item));
  }

  public static @Nullable PropStyle tryGetStyle(NBTTagCompound nbt) {
    NBTTagCompound kingdomsNBT = nbt.tryGetTag("Kingdoms", NBTTagType.COMPOUND);
    if (kingdomsNBT != null) {
      kingdomsNBT.tryGetTag("Prop", NBTTagType.COMPOUND);
    }


    return null;        //TODO
  }

}
