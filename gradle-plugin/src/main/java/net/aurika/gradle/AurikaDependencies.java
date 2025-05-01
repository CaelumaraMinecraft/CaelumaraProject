package net.aurika.gradle;

public final class AurikaDependencies {

  public static final String YAMLENGINE = "org.snakeyaml:snakeyaml-engine:2.9";
  public static final String GSON = "com.google.code.gson:gson:2.13.1";
  public static final String CAFFEINE = "com.github.ben-manes.caffeine:caffeine:3.2.0";
  public static final String GUAVA = "com.google.guava:guava:33.4.8-jre";
  public static final String FASTUTIL = "it.unimi.dsi:fastutil:8.5.15";

  public static final String APACHE_COMMONS_COMPRESS = "org.apache.commons:commons-compress:1.27.1";
  public static final String APACHE_COMMONS_CONFIGURATION = "org.apache.commons:commons-configuration2:2.11.0";
  public static final String APACHE_COMMONS_COLLECTIONS = "org.apache.commons:commons-collections4:4.5.0-M3";
  public static final String APACHE_COMMONS_EXEC = "org.apache.commons:commons-exec:1.4.0";
  public static final String APACHE_COMMONS_IO = "commons-io:commons-io:2.18.0";
  public static final String APACHE_COMMONS_LANG = "org.apache.commons:commons-lang3:3.13.0";
  public static final String APACHE_COMMONS_MATH = "org.apache.commons:commons-math3:3.6.1";
  public static final String APACHE_COMMONS_TEXT = "org.apache.commons:commons-text:1.13.0";

  public static final String HIKARI = "com.zaxxer:HikariCP:6.0.0";

  public static final String MONGODB_CORE = Groups.MONGODB + ":mongodb-driver-core:" + Versions.MONGODB;
  public static final String MONGODB_SYNC = Groups.MONGODB + ":mongodb-driver-sync:" + Versions.MONGODB;
  public static final String MONGODB_BSON = Groups.MONGODB + ":bson:" + Versions.MONGODB;
  public static final String H2 = "com.h2database:h2:2.3.232";
  public static final String POSTGRESQL = "org.postgresql:postgresql:42.7.5";

  public static final String XSERIES = "com.github.cryptomorin:XSeries:13.1.0";
  public static final String EXAMINATION_API = Groups.KYORI + ":examination-api:" + Versions.EXAMINATION;
  public static final String EXAMINATION_STRING = Groups.KYORI + ":examination-string:" + Versions.EXAMINATION;

  public static final String ADVENTURE_KEY = Groups.KYORI + ":adventure-key:" + Versions.ADVENTURE;
  public static final String ADVENTURE_API = Groups.KYORI + ":adventure-api:" + Versions.ADVENTURE;
  public static final String ADVENTURE_NBT = Groups.KYORI + ":adventure-nbt:" + Versions.ADVENTURE;

  public static final String ADVENTURE_TEXT_BUNGEE = Groups.KYORI + ":adventure-text-serializer-bungeecord:" + Versions.ADVENTURE_PLATFORM;
  public static final String ADVENTURE_TEXT_GSON = Groups.KYORI + ":adventure-text-serializer-gson:" + Versions.ADVENTURE;
  public static final String ADVENTURE_TEXT_JSON = Groups.KYORI + ":adventure-text-serializer-json:" + Versions.ADVENTURE;
  public static final String ADVENTURE_TEXT_PLAIN = Groups.KYORI + ":adventure-text-serializer-plain:" + Versions.ADVENTURE;
  public static final String ADVENTURE_TEXT_LEGACY = Groups.KYORI + ":adventure-text-serializer-legacy:" + Versions.ADVENTURE;
  public static final String ADVENTURE_TEXT_MINIMESSAGE = Groups.KYORI + ":adventure-text-minimessage:" + Versions.ADVENTURE;

  public static final String ADVENTURE_PLATFORM_API = Groups.KYORI + ":adventure-platform-api:" + Versions.ADVENTURE_PLATFORM;
  public static final String ADVENTURE_PLATFORM_BUKKIT = Groups.KYORI + ":adventure-platform-bukkit:" + Versions.ADVENTURE_PLATFORM;
  public static final String ADVENTURE_PLATFORM_BUNGEECORD = Groups.KYORI + ":adventure-platform-bungeecord:" + Versions.ADVENTURE_PLATFORM;

  public static final String EVENT_API = Groups.KYORI + ":event-api:" + Versions.EVENT;
  public static final String EVENT_METHOD = Groups.KYORI + ":event-method:" + Versions.EVENT;

  public static final String JETBRAINS_ANNOTATIONS = "org.jetbrains:annotations:26.0.2";
  public static final String CHECKER_ANNOTATIONS = "org.checkerframework:checker-qual:3.49.2";
  public static final String BYTEBUDDY = "net.bytebuddy:byte-buddy:1.17.5";
  public static final String ASM = "org.ow2.asm:asm:" + Versions.ASM;
  public static final String ASM_COMMONS = "org.ow2.asm:asm-commons:" + Versions.ASM;

  public static final class Groups {

    public static final String KYORI = "net.kyori";
    public static final String MONGODB = "org.mongodb";

  }

  public static final class Versions {

    public static final String ASM = "9.8";
    public static final String EXAMINATION = "1.3.0";
    public static final String ADVENTURE = "4.21.0";
    public static final String ADVENTURE_PLATFORM = "4.3.4";
    public static final String EVENT = "3.0.0";
    public static final String MONGODB = "5.2.1";

  }

}
