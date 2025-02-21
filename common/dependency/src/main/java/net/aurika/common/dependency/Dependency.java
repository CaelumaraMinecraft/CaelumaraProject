package net.aurika.common.dependency;

import net.aurika.common.dependency.relocation.SimpleRelocation;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public enum Dependency {
    REPTILE_YAML(
            "com{}github{}cryptomorin",
            "ReptileYAML", "4.0.0",
            "",
            SimpleRelocation.of("snakeyaml", "org{}snakeyaml")
    ),
    GSON(
            "com{}google{}code{}gson",
            "gson", "2.11.0",
            "V5KNblpu3rKr03cKj5W6RNzkXzsjt6ncKzCcWBVSp4s="
    ),
    GUAVA("com{}google{}guava",
            "guava", "33.2.1-jre",
            "RSstl4e302b6jPXtmhxAQEVC0F7/p6WY2gO7u7dtnzE="
    ),
    XSERIES("com{}github{}cryptomorin",
            "XSeries",
            "11.3.0",
            "q66T8gveELqz6p6rwKhwW2qNbh4bX6Lx+LoHzv6HEZs=",
            SimpleRelocation.of("xseries", "com{}cryptomorin{}xseries")
    ),
    ASM("org.ow2.asm",
            "asm", "9.7",
            "rfRtXjSUC98Ujs3Sap7o7qlElqcgNP9xQQZrPupcTp0="
    ),
    ASM_COMMONS("org.ow2.asm",
            "asm-commons",
            "9.7",
            "OJvCR5WOBJ/JoECNOYySxtNwwYA1EgOV1Muh2dkwS3o="
    ),
    CAFFEINE(
            "com{}github{}ben-manes{}caffeine",
            "caffeine",
            Arrays.asList(
                    DependencyVersion.of("2.9.2", "/wJFhkxtOMISmYG18O/IFGBX/kpVSXwjRa7e1GolE7k="),
                    DependencyVersion.of("3.1.8", "fdFfnfG+I4/6o2fOb1VnN6iAMd5ClNrRju9XxHTd8dM=")
            ),
            SimpleRelocation.of("caffeine", "com{}github{}benmanes{}caffeine")
    ),
    MARIADB_DRIVER(
            "org{}mariadb{}jdbc",
            "mariadb-java-client",
            "3.4.0",
            "2Dlw3NoxmMpIDlmzjp5wVd8Jgz5A2JjI7Fd4oedn+Ts=",
            SimpleRelocation.of("mariadb", "org{}mariadb{}jdbc")
    ),
    MYSQL_DRIVER(
            "com{}mysql",
            "mysql-connector-j",
            "9.0.0",
            "oiHEEGt/5opFkSzb+DUfG0OtPFOkPDvJZhgcwU+G+jA=",
            SimpleRelocation.of("mysql", "com{}mysql")
    ),
    POSTGRESQL_DRIVER(
            "org{}postgresql",
            "postgresql",
            "42.7.3",
            "omRMv7obqhRf9+jI71gqbu16fsTKeS9/BUEivex1Ymg=",
            SimpleRelocation.of("postgresql", "org{}postgresql")
    ),
    H2_DRIVER(
            "com.h2database",
            "h2",
            Arrays.asList(
                    DependencyVersion.of("2.2.224", "udjxk1itqCpPbrWxdMbP4yCjdbWpy1pP5FbWI+blVJc="),
                    DependencyVersion.of("2.1.214", "1iPNwPYdIYz1SajQnxw5H/kQlhFrIuJHVHX85PvnK9A=")
            )
    ),
    SLF4J_SIMPLE(
            "org.slf4j",
            "slf4j-simple",
            "2.1.0-alpha1",
            "AU/trHoyKI7W+PcqEAfn+zKuxb/tsnFGfkluCVNIL3U="
    ),
    SQLITE_DRIVER(
            "org.xerial",
            "sqlite-jdbc",
            "3.46.0.0",
            "5pffFb4/lSGdgHc8XxACAw4z6TKt2hhsHIb9Ud9mkak="
    ),
    MONGODB_DRIVER_CORE(
            "org{}mongodb",
            "mongodb-driver-core",
            "5.1.2",
            "/Dv5Vqkv1pFZASBTJxeVSdJ8YmATeL0B3UFLuBf1niQ=",
            SimpleRelocation.of("mongodb", "com{}mongodb"),
            SimpleRelocation.of("bson", "org{}bson")
    ),
    MONGODB_DRIVER_SYNC(
            "org{}mongodb",
            "mongodb-driver-sync",
            "5.1.2",
            "zYOd5mmmjKI3kmD+xEBll/EtD9ROfMABEeto8zidZa0=",
            SimpleRelocation.of("mongodb", "com{}mongodb"),
            SimpleRelocation.of("bson", "org{}bson")
    ),
    MONGODB_DRIVER_BSON(
            "org{}mongodb",
            "bson",
            "5.1.2",
            "wcEnpPAEpSTeGz2Bebt6cDbZgBWfpWVpvpBn3pWLxSc=",
            SimpleRelocation.of("mongodb", "com{}mongodb"),
            SimpleRelocation.of("bson", "org{}bson")
    ),
    KOTLIN_STDLIB(
            "org{}jetbrains{}" + Dependency.HashedNames.KOTLIN,
            Dependency.HashedNames.KOTLIN + "-stdlib",
            "2.0.20-Beta2",
            "rFnFiItEt/OSYo338a26XZh/AiBxz97IgmEVolBpzYg=",
            SimpleRelocation.of(HashedNames.KOTLIN, HashedNames.KOTLIN)
    ),
    KOTLIN_REFLECT(
            "org{}jetbrains{}" + Dependency.HashedNames.KOTLIN,
            Dependency.HashedNames.KOTLIN + "-reflect",
            "2.0.20-Beta2",
            "+AAY6ksSfwuYtxgNP2tx/Unu/Iq43gP6gNHI1hai73g=",
            SimpleRelocation.of(HashedNames.KOTLIN, HashedNames.KOTLIN + "{}reflect")
    ),
    HIKARI(
            "com{}zaxxer",
            "HikariCP",
            Arrays.asList(
                    DependencyVersion.of("4.0.3", "fAJK7/HBBjV210RTUT+d5kR9jmJNF/jifzCi6XaIxsk="),
                    DependencyVersion.of("6.0.0", "wQrs+YkMMpMSkAzkkDUIXXGnAfCNio5/t9smD91qpo4=")
            ),
            SimpleRelocation.of("hikari", "com{}zaxxer{}hikari")
    );

    private final @NotNull List<SimpleRelocation> relocations;
    private static final String MAVEN_FORMAT = "%s/%s/%s/%s-%s.jar";
    private final @NotNull String groupId;
    private final @NotNull String artifactId;
    private final @NotNull Map<String, DependencyVersion> versions;
    private @NotNull String defaultVersion;

    Dependency(String groupId, String artifactId, String version, String checksum, SimpleRelocation... relocations) {
        this(groupId, artifactId, Collections.singletonList(DependencyVersion.of(version, checksum)), relocations);
    }

    Dependency(String groupId, String artifactId, List<DependencyVersion> versions, SimpleRelocation... relocations) {
        this.groupId = rewriteEscaping(groupId);
        this.artifactId = rewriteEscaping(artifactId);
        this.relocations = Arrays.asList(relocations);
        this.versions = versions.stream().collect(Collectors.toMap(DependencyVersion::getVersion, (ver) -> ver));
        this.defaultVersion = versions.getFirst().getVersion();
    }

    private static @NotNull String rewriteEscaping(@NotNull String s) {
        return s.replace("{}", ".");
    }

    public void defaultVersion(@NotNull String defaultVersion) {
        this.defaultVersion = defaultVersion;
    }

    public @NotNull String getGroupId() {
        return this.groupId;
    }

    public @NotNull String getArtifactId() {
        return this.artifactId;
    }

    public String getFileName(String classifier) {
        String name = this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
        String extra = classifier != null && !classifier.isEmpty() ? '-' + classifier : "";
        return name + '-' + this.getDefaultVersion().getVersion() + extra + ".jar";
    }

    String getMavenRepoPath() {
        String version = this.getDefaultVersion().getVersion();
        return String.format(MAVEN_FORMAT, this.groupId.replace(".", "/"), this.artifactId, version, this.artifactId, version);
    }

    public DependencyVersion getDefaultVersion() {
        return Objects.requireNonNull(this.versions.get(this.defaultVersion), () -> "Cannot find default version " + this.defaultVersion + " for " + this);
    }

    public @NotNull List<SimpleRelocation> getRelocations() {
        return this.relocations;
    }

    public static MessageDigest createDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static final class HashedNames {
        private static final String KOTLIN = new String(Base64.getDecoder().decode("a290bGlu"));

        private HashedNames() {
        }
    }
}
