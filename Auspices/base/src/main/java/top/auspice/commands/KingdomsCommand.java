package top.auspice.commands;

import com.cryptomorin.xseries.reflection.XReflection;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import top.auspice.config.path.ConfigPath;
import top.auspice.config.sections.ConfigSection;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.configs.texts.SupportedLocale;
import top.auspice.configs.texts.compiler.TextCompilerSettings;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.compiler.pieces.TextPiece;
import top.auspice.configs.texts.messenger.DefaultedMessenger;
import top.auspice.configs.texts.messenger.LanguageEntryMessenger;
import top.auspice.configs.texts.messenger.Messenger;
import top.auspice.configs.texts.messenger.StaticMessenger;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.constants.player.AuspicePlayer;
import top.auspice.diversity.Diversity;
import top.auspice.main.Auspice;
import top.auspice.server.command.CommandSender;
import top.auspice.permission.Permission;
import top.auspice.permission.PermissionDefaultValue;
import top.auspice.server.entity.Player;
import top.auspice.utils.arrays.ArrayUtils;
import top.auspice.utils.enumeration.QuickEnumMap;
import top.auspice.utils.internal.Fn;
import top.auspice.utils.logging.AuspiceLogger;
import top.auspice.utils.nonnull.NonNullMap;
import top.auspice.utils.string.Strings;
import top.auspice.utils.time.TimeUtils;

import java.util.*;
import java.util.regex.Pattern;

public abstract class KingdomsCommand {
    protected static final Auspice plugin = Auspice.get();
    public static final boolean COLORIZE_TAB_COMPLETES;
    protected final @NonNull Permission permission;
    protected final @NonNull Permission bypassCooldownPermission;
    protected final @NonNull Permission bypassDisabledWorldsPermission;
    protected final @NonNull String name;
    protected final String[] path;
    protected final @Nullable KingdomsParentCommand parent;
    protected final @Nullable Messenger description;
    protected final @NonNull Set<String> disabledWorlds;
    protected final @NonNull Map<Diversity, List<String>> aliases;
    protected final long cooldown;
    private static final Pattern a;

    public KingdomsCommand(@org.intellij.lang.annotations.Pattern("[a-zA-Z0-9]+") @NonNull String var1, @Nullable KingdomsParentCommand var2, PermissionDefaultValue permissionDefaultValue) {
        Objects.requireNonNull(var1, "Command name cannot be null");
        if (!a.matcher(var1).matches()) {
            throw new IllegalArgumentException("Command name doesn't match pattern: " + a.pattern());
        } else {
            this.name = var1.toLowerCase(Locale.ENGLISH);
            this.parent = var2;
            String var4 = this.name;
            KingdomsParentCommand var5 = var2;

            StringBuilder var6;
            for(var6 = new StringBuilder(50); var5 != null; var5 = var5.parent) {
                var6.insert(0, var5.name + '.');
            }

            var4 = var6.append(var4).toString();
            this.path = ArrayUtils.merge(new String[]{"command"}, Strings.splitArray(var4, '.'));
            this.description = this.lang("description");
            ConfigSection var15 = AuspiceGlobalConfig.COMMANDS.getSection().getSection().getSection(ConfigPath.buildRaw(Strings.replace(var4, '.', ".commands.").toString()));
            boolean var17 = var2 != null && var2.isDisabled();
            String var7;
            if (var15 != null) {
                if (!var17) {
                    var17 = var15.getBoolean(new String[]{"disabled"});
                }

                if (!var17 && !com.google.common.base.Strings.isNullOrEmpty(var7 = var15.getString(new String[]{"permission-default"}))) {
                    permissionDefaultValue = (PermissionDefault) BukkitPermission.DEFAULT_VALUE_MAPPING.get(PermissionDefaultValue.valueOf(var7));
                }
            }

            this.permission = new Permission("kingdoms.command." + var4, this.description.parse(new Object[0]), permissionDefaultValue == null ? (var2 == null ? PermissionDefault.OP : ((KingdomsCommand)var2).getPermission().getDefaultValue()) : permissionDefaultValue);
            this.bypassDisabledWorldsPermission = new Permission("kingdoms.command." + var4 + ".bypass.disabled-worlds", "Permission to use this command even when it's disabled in a certain world.");
            this.bypassCooldownPermission = new Permission("kingdoms.command." + var4 + ".bypass.cooldown", "Permission to bypass the command cooldown.");
            if (var15 == null && !var17) {
                this.cooldown = 0L;
                this.aliases = Fn.cast(NonNullMap.of(new QuickEnumMap(SupportedLocale.VALUES)));
                this.disabledWorlds = new HashSet();
            } else {
                if (var17) {
                    this.cooldown = 0L;
                    this.disabledWorlds = new HashSet();
                    this.aliases = null;
                    return;
                }

                this.aliases = Fn.cast(NonNullMap.of(new QuickEnumMap(SupportedLocale.VALUES)));
                var7 = var15.getString(new String[]{"cooldown"});
                this.cooldown = com.google.common.base.Strings.isNullOrEmpty(var7) ? 0L : Optional.ofNullable(TimeUtils.parseTime(var7)).orElseThrow(() -> {
                    return new IllegalArgumentException("Invalid time format for command cooldown " + this.name + ": " + var7);
                });
                this.disabledWorlds = new HashSet<>(var15.getStringList(new String[]{"disabled-worlds"}));
            }

            if (this instanceof Listener) {
                Bukkit.getPluginManager().registerEvents((Listener)this, BukkitAuspiceLoader.get());
            }

            if (permissionDefaultValue == null && var2 != null) {
                this.permission.setDefault(((KingdomsCommand)var2).getPermission().getDefaultValue());
            }

            Bukkit.getPluginManager().addPermission(this.permission);
            Bukkit.getPluginManager().addPermission(this.bypassCooldownPermission);
            Bukkit.getPluginManager().addPermission(this.bypassDisabledWorldsPermission);
            Map var18 = var2 == null ? KingdomsCommandHandler.COMMANDS : var2.children;
            Iterator var13 = SupportedLanguage.getInstalled().iterator();

            while(var13.hasNext()) {
                Language var14 = (Language)var13.next();
                Map var16 = (Map)var18.computeIfAbsent(var14, (var0) -> {
                    return new HashMap(1);
                });

                try {
                    TextObject var19 = this.lang("aliases").getMessageObject(var14);
                    String var8 = this.getDisplayName().getMessageObject(var14).build(TextPlaceholderProvider.DEFAULT);
                    var16.put(var8.toLowerCase(var14.getLocale()), this);
                    if (var14 == Language.getDefault()) {
                        var16.put(var1.toLowerCase(Language.getDefault().getLocale()), this);
                    }

                    if (var19 != null) {
                        String[] var20 = Strings.splitArray(var19.build(TextPlaceholderProvider.DEFAULT), ' ');
                        this.aliases.put(var14, Arrays.asList(var20));
                        int var21 = (var20 = var20).length;

                        for(int var9 = 0; var9 < var21; ++var9) {
                            String var10 = var20[var9];
                            KingdomsCommand var11;
                            if ((var11 = (KingdomsCommand)var16.put(var10.toLowerCase(var14.getLocale()), this)) != null) {
                                AuspiceLogger.warn("The alias '" + var10 + "' for command '" + var4 + "' has overridden command '" + var11.name + '\'');
                            }
                        }
                    } else {
                        this.aliases.put(var14, Collections.emptyList());
                    }
                } catch (IllegalStateException var12) {
                }
            }

        }
    }

    public @Unmodifiable @NotNull Map<Diversity, List<String>> getAliases() {
        return Collections.unmodifiableMap(this.aliases);
    }

    public void setPermissionDefault(PermissionDefaultValue defaultValue) {
        this.permission.setDefault(defaultValue);
    }

    public KingdomsCommand(@org.intellij.lang.annotations.Pattern("[a-zA-Z0-9]+") @NonNull String name) {
        this(name, false);
    }

    public KingdomsCommand(@org.intellij.lang.annotations.Pattern("[a-zA-Z0-9]+") @NonNull String name, boolean notAdmin) {
        this(name, (KingdomsParentCommand)null, notAdmin ? PermissionDefaultValue.EVERYONE : null);
    }

    public KingdomsCommand(@org.intellij.lang.annotations.Pattern("[a-zA-Z0-9]+") @NonNull String name,
                           KingdomsParentCommand parent) {
        this(name, parent, null);
    }

    public void unregisterPermissions() {
        Iterator var1 = Arrays.asList(this.permission, this.bypassCooldownPermission, this.bypassDisabledWorldsPermission).iterator();

        while(var1.hasNext()) {
            Permission var2 = (Permission)var1.next();
            Bukkit.getPluginManager().removePermission(var2);
        }

    }

    public boolean canBypassDisabledWorlds(CommandSender var1) {
        return var1.hasPermission(this.bypassDisabledWorldsPermission);
    }

    public boolean canBypassCooldown(CommandSender var1) {
        return var1.hasPermission(this.bypassCooldownPermission);
    }

    @Deprecated
    public static String processTabMessage(String var0) {
        TextObject var1 = MessageCompiler.compile(var0);
        return COLORIZE_TAB_COMPLETES ? var1.buildPlain(TextPlaceholderProvider.DEFAULT) : var1.buildPlain((new TextPlaceholderProvider()).ignoreColors());
    }

    @Deprecated
    public static List<String> tabComplete(String var0) {
        return Collections.singletonList(processTabMessage(var0));
    }

    @Deprecated
    public static List<String> tabComplete(String... var0) {
        for(int var1 = 0; var1 < var0.length; ++var1) {
            var0[var1] = processTabMessage(var0[var1]);
        }

        return Arrays.asList(var0);
    }

    @Deprecated
    public static List<String> tabComplete(Collection<String> var0) {
        ArrayList var1 = new ArrayList(var0.size());
        Iterator var3 = var0.iterator();

        while(var3.hasNext()) {
            String var2 = (String)var3.next();
            var1.add(processTabMessage(var2));
        }

        return var1;
    }

    @Deprecated
    protected static List<String> emptyTab() {
        return Collections.emptyList();
    }

    public Messenger getUsage() {
        return this.lang("usage");
    }

    public @NonNull Set<String> getDisabledWorlds() {
        return this.disabledWorlds;
    }

    public long getCooldown() {
        return this.cooldown;
    }

    protected boolean isDisabled() {
        return this.aliases == null;
    }

    public @NonNull Permission getPermission() {
        return this.permission;
    }

    public boolean hasPermission(@NonNull CommandSender var1) {
        return var1.hasPermission(this.permission);
    }

    public boolean hasPermission(CommandSender messageReceiver, String var2) {
        if (messageReceiver.hasPermission((var2))) {
            return true;
        } else {
            return messageReceiver instanceof Player && AuspicePlayer.getAuspicePlayer((Player)messageReceiver).isAdmin();
        }
    }

    public String toString() {
        KingdomsParentCommand var1 = this.parent;

        StringBuilder var2;
        for(var2 = new StringBuilder(this.name); var1 != null; var1 = var1.parent) {
            var2.insert(0, var1.name + ' ');
        }

        return var2.toString();
    }

    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        } else if (!(var1 instanceof KingdomsCommand var2)) {
            return false;
        } else {
            return this.name.equals(var2.name) && Objects.equals(this.parent, var2.parent);
        }
    }

    public int hashCode() {
        int var1 = 465 + this.name.hashCode();
        return var1 * 31 + (this.parent == null ? 0 : this.parent.hashCode());
    }

    public @NotNull Messenger lang(String... var1) {
        return new LanguageEntryMessenger(ArrayUtils.merge(this.path, var1));
    }

    @Experimental
    public void configure(@NonNull CommandContext var1) {
    }

    public abstract @NotNull CommandResult execute(@NonNull CommandContext var1);

    public @NonNull List<String> tabComplete(@NonNull CommandTabContext var1) {
        return new ArrayList();
    }

    public final @NonNull Messenger getDisplayName() {
        return new DefaultedMessenger(this.lang("name"), () -> {
            return new StaticMessenger(TextObject.from(Boolean.FALSE, TextCompilerSettings.NONE_SETTINGS, new TextPiece.Plain(this.name)));
        });
    }

    public final @NonNull String getName() {
        return this.name;
    }

    public final @Nullable KingdomsParentCommand getParent() {
        return this.parent;
    }

    public @Nullable Messenger getDescription() {
        return this.description;
    }

    static {
        COLORIZE_TAB_COMPLETES = XReflection.supports(13) && AuspiceGlobalConfig.COMMAND_ENABLE_TAB_COLORIZATION.getBoolean();
        a = Pattern.compile("[a-zA-Z0-9]+");
    }
}