package net.aurika.auspice.configs.globalconfig;

import com.cryptomorin.xseries.XSound;
import net.aurika.auspice.configs.globalconfig.accessor.EnumGlobalConfig;
import net.aurika.auspice.platform.entity.Player;
import net.aurika.auspice.user.Auspice;
import net.aurika.auspice.utils.time.TimeUtils;
import net.aurika.configuration.accessor.YamlUndefinedPathConfigAccessor;
import net.aurika.configuration.adapter.YamlResource;
import net.aurika.configuration.path.ConfigPath;
import net.aurika.configuration.yaml.importers.YamlGlobalImporter;

import java.io.File;

public enum AuspiceGlobalConfig implements EnumGlobalConfig {
  ERROR_SOUND,
  PREFIX,
  DEBUG,
  INTEGRATIONS,

  PLACEHOLDERS_FORMATS(1),
  PLACEHOLDERS_DEFAULTS(1),
  PLACEHOLDERS_VARIABLES(1),

  AUDIT_LOGS_EXPIRATION(2),
  AUDIT_LOGS_EXPIRATION_DEFAULT(2, 3),
  AUDIT_LOGS_DISABLED(2),

  LANG,
  FORCE_LANG,
  DISABLED_WORLDS,
  UPDATES_AUTOMATIC_CONFIG_RELOADS(1),
  UPDATES_SYNCHRONIZE_GUIS_AUTOMATIC(1, 3),
  UPDATES_SYNCHRONIZE_GUIS_REFERENCE_LANGUAGE(1, 3),
  UPDATES_CONFIGS(1),
  DATABASE_METHOD(1),
  DATABASE_USE_DATA_FOLDER(1),
  DATABASE_AUTO_SAVE_INTERVAL(1),
  DATABASE_SMART_SAVE(1),
  DATABASE_LOAD_ALL_DATA_ON_STARTUP(1),
  DATABASE_TABLE_PREFIX(1),
  DATABASE_TABLES_NATIONS(1, 2),
  DATABASE_TABLES_KINGDOMS(1, 2),
  DATABASE_TABLES_PLAYERS(1, 2),
  DATABASE_TABLES_LANDS(1, 2),
  DATABASE_TABLES_MAILS(1, 2),
  DATABASE_TABLES_GLOBALS(1, 2),
  DATABASE_ADDRESS(1),
  DATABASE_DATABASE(1),
  DATABASE_USERNAME(1),
  DATABASE_PASSWORD(1),
  DATABASE_SSL_ENABLED(1, 2),
  DATABASE_SSL_VERIFY_SERVER_CERTIFICATE(1, 2),
  DATABASE_SSL_ALLOW_PUBLIC_KEY_RETRIEVAL(1, 2),
  DATABASE_POOL_SETTINGS_SIZE_MAX(1, 3, 4),
  DATABASE_POOL_SETTINGS_SIZE_MIN(1, 3, 4),
  DATABASE_POOL_SETTINGS_MAX_CONCURRENT_CONNECTIONS(1, 3),
  DATABASE_POOL_SETTINGS_MINIMUM_IDLE(1, 3),
  DATABASE_POOL_SETTINGS_MAXIMUM_LIFETIME(1, 3),
  DATABASE_POOL_SETTINGS_KEEPALIVE_TIME(1, 3),
  DATABASE_POOL_SETTINGS_CONNECTION_TIMEOUT(1, 3),
  DATABASE_PROPERTIES(1),
  DATABASE_URI(1, 2),
  BACKUPS_ENABLED_ENABLED(1, 2),
  BACKUPS_ENABLED_DATA(1, 2),
  BACKUPS_ENABLED_CONFIGS(1, 2),
  BACKUPS_ENABLED_CHUNK_SNAPSHOTS(1, 2),
  BACKUPS_FOLDER(1, 2),
  BACKUPS_IGNORE_TODAYS_BACKUP(1),
  BACKUPS_INTERVAL(1),
  BACKUPS_DELETE_BACKUPS_OLDER_THAN(1),
  NO_KINGDOM_REMINDER,
  GUIS_CREATIVE_SOUND(1),
  GUIS_CLOSE_ON_DAMAGE(1),
  GUIS_DEFAULT_CLICK_SOUND(1),
  GUIS_ALLOW_OWN_INVENTORY_INTERACT(1),
  GUIS_ENABLE_REFRESH_FEATURE(1),
  HOLOGRAMS_UPDATE(1),
  CREATION_KINGDOMS_SOUND(1, 2),
  CREATION_KINGDOMS_SHOW_KINGDOM_TYPE_GUI(1, 2),
  CREATION_KINGDOMS_NEWBIE_PROTECTION(1, 2),
  CREATION_NATIONS_SOUND(1, 2),
  CREATION_NATIONS_NEWBIE_PROTECTION(1, 2),
  KEEP_ADMIN_MODE,
  EVENTS,
  TAGS_ATTEMPT_AUTOMATIC_SETTING(1),
  TAGS_RENAMING_COOLDOWN(1),
  TAGS_MAX_LENGTH(1),
  TAGS_MIN_LENGTH(1),
  TAGS_IGNORE_COLORS(1),
  TAGS_ALLOW_SPACES(1),
  TAGS_ALLOW_NON_ENGLISH(1),
  TAGS_ALLOW_SYMBOLS(1),
  TAGS_ALLOW_NUMBERS(1),
  TAGS_BLACKLISTED_NAMES(1),

  ;
  private final ConfigPath option;

  AuspiceGlobalConfig() {
    this.option = ConfigPath.fromEnum(this);
  }

  AuspiceGlobalConfig(String var3) {
    this.option = new ConfigPath(var3);
  }

  AuspiceGlobalConfig(int... group) {
    this.option = new ConfigPath(this.name(), group);
  }

  @Override
  public YamlUndefinedPathConfigAccessor getManager() {
    return new YamlUndefinedPathConfigAccessor(MAIN, this.option);
  }

  public Long getTimeMillis() {
    String str = this.getString();
    return str == null ? null : TimeUtils.parseTime(str);
  }

  public static final YamlResource MAIN = a("config");

  private static YamlResource a(String profileName) {
    File folder = Auspice.get().getDataFolder();
    return (new YamlResource(new File(folder, profileName + ".yml"))).setImporter(
        YamlGlobalImporter.INSTANCE).load().importDeclarations();
  }

  public static void errorSound(Player player) {
    errorSound(new Player[]{player});
  }

  public static void errorSound(Player... players) {
    XSound.play(ERROR_SOUND.getString(), (__) -> __.forPlayers(players));
  }
}
