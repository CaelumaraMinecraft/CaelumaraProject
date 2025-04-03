package net.aurika.auspice.gui;

import net.aurika.auspice.commands.CommandContext;
import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.auspice.configs.texts.Locale;
import net.aurika.auspice.configs.texts.MessageHandler;
import net.aurika.auspice.configs.texts.SupportedLocale;
import net.aurika.auspice.debug.AuspiceDebug;
import net.aurika.auspice.gui.objects.GUIObject;
import net.aurika.auspice.main.BukkitAuspiceLoader;
import net.aurika.auspice.utils.AuspiceLogger;
import net.aurika.auspice.utils.Pair;
import net.aurika.auspice.utils.filesystem.FolderRegistry;
import net.aurika.config.profile.managers.ConfigManager;
import net.aurika.snakeyaml.extension.validation.NodeValidator;
import net.aurika.snakeyaml.extension.validation.Validator;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public final class GUIConfig {

  private static final Map<String, Function<CommandContext, InteractiveGUI>> a = new HashMap();
  protected static final NodeValidator SCHEMA;
  public static final String FOLDER_NAME = "guis";
  public static final String TEMPLATES_FOLDER_NAME = "templates";
  public static final Path FOLDER = BukkitAuspiceLoader.getPath("guis");
  private static final Function<String[], Boolean> b;

  public GUIConfig() {
  }

  public static Path getFolder() {
    return FOLDER;
  }

  public static void loadInternalGUIs(Plugin var0) {
    String var1 = "/guis";
    URI var2 = null;

    try {
      var2 = var0.getClass().getResource(var1).toURI();
    } catch (Exception var5) {
      MessageHandler.sendConsolePluginMessage("&cUnable to find plugin GUI with URI&8: &e" + var1);
      MessageHandler.sendConsolePluginMessage("&cUsing the default GUI config...");
      var5.printStackTrace();
    }

    try {
      FileSystem var3 = FileSystems.newFileSystem(var2, new HashMap());

      try {
        int var8 = var1.length() + 1;
        Files.walk(var3.getPath(var1)).forEach((var2x) -> {
          String var3;
          if (!(var3 = var2x.toString()).startsWith("/guis/templates") && var3.endsWith(".yml")) {
            var3 = var3.substring(var8, var3.length() - 4);
            a(var0, var3, false, false);
          }
        });
      } catch (Throwable var6) {
        if (var3 != null) {
          try {
            var3.close();
          } catch (Throwable var4) {
            var6.addSuppressed(var4);
          }
        }

        throw var6;
      }

      if (var3 == null) {
        return;
      }

      var3.close();
    } catch (IOException var7) {
      var7.printStackTrace();
    }
  }

  public static void loadFolderGUIs() {
    FolderRegistry var0;
    (var0 = new FolderRegistry("GUI Registry", SupportedLocale.EN.getGUIFolder()) {
      protected final Pair<String, URI> getDefaultsURI() {
        throw new UnsupportedOperationException();
      }

      protected final void handle(FolderRegistry.@NonNull Entry var1) {
        String var2;
        if (!(var2 = var1.getName()).startsWith("templates")) {
          GUIConfig.a(BukkitAuspiceLoader.get(), var2, false, true);
        }
      }

      public final void register() {
        this.visitPresent();
      }
    }).copyDefaults(false).useDefaults(false);
    var0.register();
  }

  public static void init() {
    loadInternalGUIs(BukkitAuspiceLoader.get());
    loadFolderGUIs();
    Iterator var0 = SupportedLocale.getInstalled().iterator();

    while (var0.hasNext()) {
      SupportedLocale var1;
      if ((var1 = (SupportedLocale) var0.next()) != Locale.getDefault()) {
        registerGUIsFor(var1);
      }
    }

    standardGUISync();
  }

  public static void standardGUISync() {
    if (AuspiceGlobalConfig.UPDATES_SYNCHRONIZE_GUIS_AUTOMATIC.getBoolean()) {
      String var0;
      if ((var0 = AuspiceGlobalConfig.UPDATES_SYNCHRONIZE_GUIS_REFERENCE_Locale.getString()) != null && !var0.isEmpty()) {
        SupportedLocale var1;
        if ((var1 = SupportedLocale.fromName(var0)) == null) {
          AuspiceLogger.error("Unknown reference Locale specified for GUI synchronization: " + var0);
        } else if (!var1.isInstalled()) {
          AuspiceLogger.error("The specified reference Locale for GUI synchronization is not installed: " + var1);
        } else {
          AuspiceLogger.info("Synchronizing GUIs against " + var1 + "...");
          int var2 = synchronizeAllGUIs(var1);
          AuspiceLogger.info("Synchronized a total of " + var2 + " GUIs.");
        }
      }
    }
  }

  public static void registerIndependentGUI(KingdomsGUI var0, Function<CommandContext, InteractiveGUI> var1) {
    String var2 = var0.getGUIPath();
    if (a.containsKey(var2)) {
      throw new IllegalArgumentException("Independent GUI already registered: " + var2);
    } else {
      a.put(var2, var1);
    }
  }

  public static Map<String, Function<CommandContext, InteractiveGUI>> getIndependentGuis() {
    return a;
  }

  public static GUIObject getGUI(String var0, Locale var1, boolean var2) {
    GUIObject var3 = var1.getGUIOrDefault(var0, var2);
    if (!var2) {
      if (var3 == null) {
        throw new IllegalArgumentException("Unknown GUI: " + var0 + " (for texts " + var1 + ')');
      }

      if (var3.getConfig().getFile() != null && !var3.getConfig().getFile().exists()) {
        AuspiceLogger.warn("The GUI file for '" + var0 + "' was deleted, regenerating a new one...");
        var3.getConfig().saveDefaultConfig();
        reload(var3, var1);
      }
    }

    return var3;
  }

  public static void reload(String var0, Locale var1) {
    GUIObject var2;
    Objects.requireNonNull(
        var2 = var1.getGUIOrDefault(var0, false), () -> {
          return "Cannot reload unknown GUI: " + var0;
        }
    );
    reload(var2, var1);
  }

  public static void reload(GUIObject var0, Locale var1) {
    reload(var0, var1, true);
  }

  public static void reload(GUIObject var0, Locale var1, boolean var2) {
    Objects.requireNonNull(
        var0, () -> {
          return "Cannot reload null GUI for texts: " + var1;
        }
    );
    Objects.requireNonNull(
        var1, () -> {
          return "Cannot reload GUI for null texts: " + var0.getName();
        }
    );
    var0.getConfig().reload();
    loadAndRegisterGUI(var1, var0.getConfig(), var0.getName());
    if (var2) {
      synchronizeGUIs(var0, var1);
    }
  }

  public static int synchronizeAllGUIs(SupportedLocale var0) {
    int var1 = 0;
    SupportedLocale[] var2;
    int var3 = (var2 = SupportedLocale.VALUES).length;

    for (int var4 = 0; var4 < var3; ++var4) {
      SupportedLocale var5;
      if ((var5 = var2[var4]) != var0 && var5.isInstalled()) {
        Iterator var6 = var5.getGUIs().values().iterator();

        while (var6.hasNext()) {
          GUIObject var7 = (GUIObject) var6.next();
          GUIObject var8;
          if ((var8 = (GUIObject) var0.getGUIs().get(var7.getName())) != null && synchronizeGUI(var5, var7, var8)) {
            ++var1;
          }
        }
      }
    }

    return var1;
  }

  public static void synchronizeGUIs(GUIObject var0, Locale var1) {
    if (AuspiceGlobalConfig.UPDATES_SYNCHRONIZE_GUIS_AUTOMATIC.getBoolean()) {
      SupportedLocale[] var2;
      int var3 = (var2 = SupportedLocale.VALUES).length;

      for (int var4 = 0; var4 < var3; ++var4) {
        SupportedLocale var5;
        GUIObject var6;
        if ((var5 = var2[var4]).isInstalled() && var5 != var1 && (var6 = (GUIObject) var5.getGUIs().get(
            var0.getName())) != null) {
          synchronizeGUI(var5, var6, var0);
        }
      }
    }
  }

  public static boolean synchronizeGUI(SupportedLocale var0, GUIObject var1, GUIObject var2) {
    YamlFile var3 = (new YamlFile(var1.getConfig().getFile())).setLoadAnchors(false).setImportDeclarations(
        false).load();
    YamlFile var4 = (new YamlFile(var2.getConfig().getFile())).setLoadAnchors(false).setImportDeclarations(
        false).load();
    GUILocaleSyncer var5;
    (var5 = new GUILocaleSyncer(var1.getName(), var3.getConfig(), var4.getConfig(), b)).update();
    if (var5.hasChanged()) {
      var3.saveConfig();
      reload(var1, var0, false);
    }

    return var5.hasChanged();
  }

  private static void a(YamlWithDefaults var0, Locale var1) {
    var0.setLoadAnchors(false).load();
    var0.setSchema(SCHEMA);
    var0.setImporter(var1.getYamlImporter()).importDeclarations();
  }

  private static void a(Plugin var0, String var1, boolean var2, boolean var3) {
    Locale var4 = Locale.getDefault();
    if (!var3 || !var4.getGUIs().containsKey(var1)) {
      AuspiceLogger.debug(AuspiceDebug.GUIS_CONFIG, "Loading GUI&8: &9" + var1);
      String var7 = "guis/" + var4.getLowerCaseName() + '/' + var1 + ".yml";
      String var5 = "guis/" + var1 + ".yml";
      File var8 = new File(BukkitAuspiceLoader.get().getDataFolder(), var7);
      if (!var2 && !var8.exists() && var0.getClass().getResource("/" + var5) == null) {
        AuspiceLogger.error("Could not find default " + var4 + " GUI translation for " + var1 + " GUI.");
      } else {
        YamlResource var6;
        a((YamlWithDefaults) (var6 = new YamlResource(var0, var8, var5)), (Locale) var4);
        loadAndRegisterGUI(var4, var6, var1);
      }
    }
  }

  public static void loadAndRegisterGUI(Locale var0, YamlWithDefaults var1, String var2) {
    if (!var1.isLoaded()) {
      AuspiceLogger.debug(
          AuspiceDebug.GUIS_CONFIG, () -> {
            return "Removed " + var0.getLowerCaseName() + '/' + var2 + " GUI since its config was not loaded.";
          }
      );
      var0.getGUIs().remove(var2);
    } else {
      ConfigManager.warnAboutValidation(var1.getFile().toString(), var1.validate());
      GUIObject var3;
      if ((var3 = GUIParser.parse(var1, var2, var0)) != null) {
        var0.getGUIs().put(var2, var3);
      } else {
        var0.getGUIs().remove(var2);
        AuspiceLogger.debug(
            AuspiceDebug.GUIS_CONFIG, () -> {
              return "Did not load " + var0.getLowerCaseName() + '/' + var2 + " GUI because it was null";
            }
        );
      }
    }
  }

  public static void registerGUIsFor(SupportedLocale var0) {
    var0.ensureInstalled();
    Path var1 = var0.getRepoPath().resolve("guis");
    Iterator var2 = SupportedLocale.getRegisteredGUINames().iterator();

    while (true) {
      String var3;
      Path var4;
      File var5;
      do {
        if (!var2.hasNext()) {
          return;
        }

        var3 = (String) var2.next();
        var4 = var1.resolve(var3 + ".yml");
        var5 = var0.getGUIFolder().resolve(var3 + ".yml").toFile();
      } while (!Files.exists(var4, new LinkOption[0]) && !var5.exists());

      YamlWithDefaults var6 = createAdapter(var5, var4.toFile(), var0);
      loadAndRegisterGUI(var0, var6, var3);
    }
  }

  public static YamlWithDefaults createAdapter(File var0, File var1, SupportedLocale var2) {
    YamlFiledDefaults var3;
    a((YamlWithDefaults) (var3 = new YamlFiledDefaults(var0, var1)), (Locale) var2);
    return var3;
  }

  public static void reloadAll() {
    SupportedLocale[] var0;
    int var1 = (var0 = SupportedLocale.VALUES).length;

    for (int var2 = 0; var2 < var1; ++var2) {
      SupportedLocale var3;
      if ((var3 = var0[var2]).isInstalled()) {
        Iterator var4 = var3.getGUIs().values().iterator();

        while (var4.hasNext()) {
          reload((GUIObject) ((GUIObject) var4.next()), var3);
        }
      }
    }

    standardGUISync();
  }

  static {
    String var0 = "schemas/guis/schema.yml";
    InputStream var1 = BukkitAuspiceLoader.get().getResource(var0);
    SCHEMA = Validator.parseSchema(YamlContainer.parse((new YamlParseContext()).named("GUI Schema").stream(var1)));
    b = (var0x) -> {
      String sd = var0x[0];
      if (sd.equals("title")) {
        return Boolean.TRUE;
      } else {
        String var2;
        return !sd.equals("options") ? Boolean.FALSE : (var2 = var0x[var0x.length - 1]).equals("name") || var2.equals(
            "lore");
      }
    };
  }
}
