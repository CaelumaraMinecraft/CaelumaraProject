package top.auspice.commands;

import com.github.benmanes.caffeine.cache.Cache;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.configs.texts.AuspiceLang;
import top.auspice.configs.texts.SimpleMessenger;
import top.auspice.configs.texts.messenger.DefaultedMessenger;
import top.auspice.configs.texts.messenger.Messenger;
import top.auspice.configs.texts.messenger.StaticMessenger;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.constants.player.AuspicePlayer;
import top.auspice.diversity.Diversity;
import top.auspice.key.NSedKey;
import top.auspice.loader.AuspiceLoader;
import top.auspice.platform.bukkit.entity.OfflinePlayer;
import top.auspice.server.command.CommandSender;
import top.auspice.server.entity.Player;
import top.auspice.utils.Pair;
import top.auspice.utils.Validate;
import top.auspice.utils.cache.caffeine.CacheHandler;
import top.auspice.utils.internal.arrays.FunctionalList;
import top.auspice.utils.number.AnyNumber;
import top.auspice.utils.number.NumberConstraint;
import top.auspice.utils.number.NumberProcessor;
import top.auspice.utils.string.Strings;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CommandContext extends SimpleMessenger {
    private static final Cache<UUID, a> a;
    public final AuspiceLoader plugin;
    public final String[] args;
    private final KingdomsCommand command;
    protected Map<String, String> mappedArguments;
    protected Messenger result;
    protected CommandLoggingLevel loggingLevel;
    protected int argPosition;
    private boolean c;
    private CommandSession.Session d;

    public CommandContext(AuspiceLoader var1, KingdomsCommand command, CommandSender var3, String[] var4) {
        super(var3, (new TextPlaceholderProvider()).withContext(var3));
        this.loggingLevel = CommandLoggingLevel.ALL;
        this.c = false;
        super.messageSettings.lang(this.isPlayer() ? this.getKingdomPlayer().getDiversity() : Diversity.getDefault());
        this.plugin = var1;
        this.command = command;
        this.args = var4;
    }

    public String arg(@IntRange(from = 0L, to = 5L) int var1) {
        if (var1 >= this.args.length) {
            throw this.error(this.command.getUsage());
        } else {
            this.argPosition = var1 + 1;
            return this.args[var1];
        }
    }

    public boolean argIsAny(@IntRange(from = 0L, to = 5L) int var1, String... var2) {
        String var6 = this.arg(var1).toLowerCase();
        int var3 = (var2 = var2).length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            if (var6.equals(var5)) {
                return true;
            }
        }

        return false;
    }

    public String joinArgs() {
        return this.joinArgs(" ");
    }

    public String joinArgs(String var1) {
        return String.join(var1, this.args);
    }

    public String joinArgs(String var1, @IntRange(from = 0L, to = 5L) int var2) {
        StringJoiner var3 = new StringJoiner(var1);

        while (var2 < this.args.length) {
            var3.add(this.args[var2++]);
        }

        return var3.toString();
    }

    public boolean hasPermission() {
        return !(super.sender instanceof Player) || this.command.hasPermission(super.sender) || AuspicePlayer.getAuspicePlayer((Player) super.sender).isAdmin();
    }

    public void requireYesNo(Duration var1, CommandSession.SessionCallback var2) {
        CommandSession.addSession(this, var1, var2);
    }

    public void requireYesNo(Messenger var1) {
        if (this.d != null) {
            NSedKey var2 = NSedKey.auspice("COMMAND_" + this.command.name.toUpperCase(Locale.ENGLISH));
            Cache<Integer, CommandSession.Session> var3 = CommandSession.SESSIONS.get(var2);
            if (var3 != null && var3.getIfPresent(this.d.getId()) != null) {
                return;
            }
        }

        this.d = CommandSession.addSession(this, Duration.ofSeconds(30L), (var1x) -> {
            if (var1x.equalsIgnoreCase("yes")) {
                this.command.execute(this);
            } else {
                this.sendError(new StaticMessenger("&4Unknown answer&8: &e" + var1x));
            }

            return false;
        });
        throw this.error(var1);
    }

    public boolean requireConfirmation(@NonNull Messenger var1) {
        UUID var2 = this.isPlayer() ? ((SimpleMessenger) this).senderAsPlayer().getUniqueId() : new UUID(0L, 0L);
        a var3 = a.getIfPresent(var2);
        if (var3 != null && var3.a == this.command && Arrays.equals(var3.b, this.args)) {
            return false;
        } else {
            this.sendError(var1);
            a.put(var2, new a(this.command, this.args, Duration.ofSeconds(30L)));
            return true;
        }
    }

    public KingdomsCommand getCommand() {
        return this.command;
    }

    public Integer parseInt(int var1, Messenger var2, boolean var3) {
        String var5 = this.arg(var1);
        super.messageSettings.raw("arg", var2);

        try {
            var1 = Integer.parseInt(var5);
            if (var3) {
                return var1;
            } else if (var1 < 0) {
                this.sendMessage(this.command.getUsage(), super.messageSettings);
                this.sendError(AuspiceLang.INVALID_NUMBER_NEGATIVE);
                return null;
            } else {
                return var1;
            }
        } catch (NumberFormatException var4) {
            this.sendMessage(this.command.getUsage(), super.messageSettings);
            this.sendError(AuspiceLang.INVALID_NUMBER);
            return null;
        }
    }

    public void setLoggingLevel(CommandLoggingLevel var1) {
        this.loggingLevel = var1;
    }

    public CommandLoggingLevel getLoggingLevel() {
        return this.loggingLevel;
    }

    public void sendMessage(@NotNull CommandSender messageReceiver, @NotNull Messenger messenger, Object... edits) {
        if (this.loggingLevel.atLeast(super.sender == messageReceiver ? CommandLoggingLevel.ALL : CommandLoggingLevel.INFO)) {
            super.sendMessage(messageReceiver, messenger, edits);
        }
    }

    public void sendError(@NotNull Messenger messenger, Object... edits) {
        if (this.loggingLevel.atLeast(CommandLoggingLevel.ERRORS)) {
            super.sendError(messenger, edits);
        }
    }

    public Boolean parseBool(@IntRange(from = 0L, to = 5L) int var1) {
        String var2;
        if (!(var2 = this.arg(var1).toLowerCase(Locale.ENGLISH)).equals("true") && !AuspiceLang.TRUE.parse(super.sender).toLowerCase().equals(var2)) {
            if (!var2.equals("false") && !AuspiceLang.FALSE.parse(super.sender).toLowerCase().equals(var2)) {
                this.sendError(AuspiceLang.INVALID_BOOLEAN, "arg", this.arg(var1));
                return null;
            } else {
                return Boolean.FALSE;
            }
        } else {
            return Boolean.TRUE;
        }
    }

    public String arg(@IntRange(from = 0L, to = 5L) int var1, String var2) {
        return this.assertArgs(var1 + 1) ? this.args[var1] : var2;
    }

    public CommandContext debug(String message) {
        AuspiceLogger.info(message);
        return this;
    }

    public int nextArg() {
        this.c = true;
        return this.argPosition++;
    }

    public int previousArg() {
        return --this.argPosition;
    }

    public String nextString() {
        return this.arg(this.nextArg());
    }

    private boolean a(String var1, AuspiceLang var2) {
        String[] var6;
        int var3 = (var6 = Strings.splitArray(var2.parse(super.messageSettings), ' ')).length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String var5 = var6[var4];
            if (var1.equals(var5)) {
                return true;
            }
        }

        return false;
    }

    protected SetterType getNumberModifierType(String var1) {
        if (this.a(var1, AuspiceLang.SETTERS_TYPE_ADD)) {
            return CommandContext.SetterType.ADDITION;
        } else if (this.a(var1, AuspiceLang.SETTERS_TYPE_SUBTRACT)) {
            return CommandContext.SetterType.SUBTRACTION;
        } else {
            return this.a(var1, AuspiceLang.SETTERS_TYPE_SET) ? CommandContext.SetterType.SET : null;
        }
    }

    public AnyNumber numberModifier(String var1, Number var2, NumberConstraint... var3) {
        int var4 = this.nextArg();
        String var5 = this.arg(var4);
        SetterType var6 = this.getNumberModifierType(var5);
        DefaultedMessenger var7 = DefaultedMessenger.of(this.command.lang("parameters", var1), new StaticMessenger(var1));
        Pair var8;
        if (var6 == null) {
            this.var("setter_type", var5);
            if ((var8 = this.getNumber(var4, var7, var3)).getValue() == null) {
                return CommandContext.SetterType.ADDITION.handle(AnyNumber.of(var2), (AnyNumber) var8.getKey());
            } else if (var5.isEmpty()) {
                throw this.emptyError();
            } else {
                throw this.error(AuspiceLang.SETTERS_TYPE_UNKNOWN);
            }
        } else if (!this.hasMoreParameters()) {
            throw this.error(this.command.getUsage());
        } else if ((var8 = this.getNumber(this.nextArg(), var7, var3)).getValue() != null) {
            throw this.error((Messenger) var8.getValue());
        } else {
            return var6.handle(AnyNumber.of(var2), (AnyNumber) var8.getKey());
        }
    }

    public CommandResult fail(Messenger var1) {
        super.sendError(var1);
        this.result = var1;
        return CommandResult.FAILED;
    }

    @Internal
    public Messenger getResult() {
        return this.result;
    }

    public void wrongUsage() {
        this.fail(this.command.getUsage());
    }

    public boolean assertPlayer() {
        if (this.isPlayer()) {
            return false;
        } else {
            this.fail(AuspiceLang.COMMANDS_PLAYERS_ONLY);
            return true;
        }
    }

    public boolean assertEnabledWorld() {
        if (!this.isPlayer()) {
            return false;
        } else {
            String var1 = ((SimpleMessenger) this).senderAsPlayer().getWorld().getName();
            if (AuspiceGlobalConfig.DISABLED_WORLDS.getStringList().contains(var1)) {
                this.var("world", var1);
                this.fail(AuspiceLang.DISABLED_WORLD);
                return true;
            } else {
                return false;
            }
        }
    }

    public @NotNull CommandContext var(@NotNull String variable, @Nullable Object value) {
        super.var(variable, value);
        return this;
    }

    public AuspiceLoader getPlugin() {
        return this.plugin;
    }

    public String[] getArgs() {
        return this.args;
    }

    public Player senderAsPlayer() {
        return (Player) super.sender;
    }

    public boolean assertArgs(@IntRange(from = 1L, to = 5L) int var1) {
        return this.args.length >= var1;
    }

    public boolean requireArgs(@IntRange(from = 1L, to = 5L) int var1) {
        if (!this.assertArgs(var1)) {
            this.wrongUsage();
            return true;
        } else {
            return false;
        }
    }

    protected CommandUserError emptyError() {
        return new CommandUserError("Please report to the developers if you see this message", this, null);
    }

    protected CommandUserError error(Messenger result) {
        this.result = result;
        return new CommandUserError("Please report to the developers if you see this message", this, result);
    }

    private void checkMappedArguments() {
        if (this.mappedArguments == null) {
            this.mapArguments();
        }
    }

    public @NonNull String required(String var1) {
        this.checkMappedArguments();
        if ((var1 = this.mappedArguments.get(var1)) == null) {
            throw this.error(AuspiceLang.COMMANDS_MAPPED_ARGUMENTS_MISSING);
        } else {
            return var1;
        }
    }

    public @Nullable String optional(String var1) {
        this.checkMappedArguments();
        return this.mappedArguments.get(var1);
    }

    public void mapArguments() {
        this.mappedArguments = new HashMap<>(10);
        String var1 = null;
        String var2 = null;
        String[] var3;
        int var4 = (var3 = this.args).length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String var6;
            int var7;
            if ((var7 = (var6 = var3[var5]).indexOf(61)) > 0) {
                if (var1 != null) {
                    throw new IllegalArgumentException("End quote for argument '" + var1 + "' not found: " + this.joinArgs());
                }

                boolean var8 = var6.length() > var7 + 1 && var6.charAt(var7 + 1) == '"';
                var1 = var6.substring(0, var7);
                var2 = var6.substring(var8 ? var7 + 2 : var7 + 1);
                if (!var8) {
                    this.mappedArguments.put(var1, var2);
                    var1 = null;
                    var2 = null;
                }
            } else {
                if (var1 == null) {
                    throw new IllegalArgumentException("No context key found: " + this.joinArgs());
                }

                if (var6.endsWith("\"")) {
                    var2 = var2 + ' ' + var6.substring(0, var6.length() - 1);
                    this.mappedArguments.put(var1, var2);
                    var1 = null;
                    var2 = null;
                } else {
                    var2 = var2 + ' ' + var6;
                }
            }
        }
    }

    public Messenger lang(String... var1) {
        return this.command.lang(var1);
    }

    public boolean argsLengthEquals(int var1) {
        return this.args.length == var1;
    }

    public boolean isAtArg(@Range(from = 0L, to = 5L) int i) {
        //noinspection ConstantValue  就是爱检查 (
        if (i < 0) {
            throw new IllegalArgumentException("Cannot check negative index: " + i);
        } else if (this.args.length == 0 && i == 0) {
            return true;
        } else {
            return this.args.length == i + 1;
        }
    }

    public boolean isAtCurrentParameter() {
        Validate.isTrue(this.c, "Next argument is not ready for " + this.argPosition);
        this.c = false;
        return this.isAtArg(this.argPosition - 1);
    }

    public boolean hasMoreParameters() {
        return this.argPosition < this.args.length;
    }

    public @Range(from = 1L, to = 5L) int getParameterPosition() {
        return this.args.length;
    }

    public int intArg(@Range(from = 0L, to = 5L) int i) {
        return Integer.parseInt(this.arg(i));
    }

    public boolean isNumber(int i) {
        try {
            Double.parseDouble(this.arg(i));
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public Double getDouble(int i) {
        AnyNumber var2 = this.getNumber(i, false, true, null);
        return var2 == null ? null : var2.getValue().doubleValue();
    }

    public Integer getInt(int i) {
        AnyNumber var2;
        return (var2 = this.getNumber(i, true, true, null)) == null ? null : var2.getValue().intValue();
    }

    public AnyNumber getNumber(int var1, boolean var2, boolean var3, Messenger var4) {
        Pair var5;
        if ((var5 = this.getNumber(var1, var4, (NumberConstraint[]) FunctionalList.create().addIf(var2, NumberConstraint.INTEGER_ONLY).addIf(var3, NumberConstraint.POSITIVE).toArray(new NumberConstraint[0]))).getValue() != null) {
            this.fail((Messenger) var5.getValue());
            return null;
        } else {
            return (AnyNumber) var5.getKey();
        }
    }

    public Pair<AnyNumber, Messenger> getNumber(int var1, Messenger var2, NumberConstraint... var3) {
        if (var1 >= 0 && var1 < this.args.length) {
            super.messageSettings.raw("arg", this.args[var1]).raw("needed", var2);
            return ProcessToMessage.getNumber((new NumberProcessor(this.arg(var1))).withAllDecorators().withConstraints(var3));
        } else {
            throw new IllegalArgumentException("Cannot get parameter at index " + var1 + " with parameters: (" + this.args.length + ')' + Arrays.toString(this.args));
        }
    }

    public AuspicePlayer getKingdomPlayer() {
        return AuspicePlayer.getAuspicePlayer((this).senderAsPlayer());
    }

    public boolean argEquals(int var1, AuspiceLang var2) {
        return this.argEquals(var1, var2.parse());
    }

    public boolean argEquals(int var1, String var2) {
        return this.args.length > var1 && this.arg(var1).equals(var2);
    }

    @NotNull
    public CommandSender getMessageReceiver() {
        return super.sender;
    }

    public boolean hasPermission(AuspiceDefaultPluginPermission var1) {
        return this.hasPermission(var1, true);
    }

    public boolean hasPermission(KingdomsDefaultPluginPermission var1, boolean var2) {
        return var1.hasPermission(super.sender, var2);
    }

    public boolean isAdmin() {
        return !this.isPlayer() || this.getKingdomPlayer().isAdmin();
    }

    public @Nullable Player getPlayer(int var1) {
        return this.getPlayer(var1, true);
    }

    public @Nullable Player getPlayer(int var1, boolean var2) {
        String var3;
        Player var4;
        if ((var4 = PlayerUtils.getPlayer(var3 = this.arg(var1), var2)) == null) {
            super.messageSettings.raw("name", var3);
            this.sendError(AuspiceLang.NOT_FOUND_PLAYER);
        }

        return var4;
    }

    public @Nullable OfflinePlayer getOfflinePlayer(int var1) {
        String var3 = this.arg(var1);
        OfflinePlayer var2 = PlayerUtils.getOfflinePlayer(var3);
        if (var2 == null) {
            super.messageSettings.raw("name", var3);
            this.sendError(AuspiceLang.NOT_FOUND_PLAYER);
        }

        return var2;
    }

    static {
        a = CacheHandler.newBuilder().expireAfterWrite(10L, TimeUnit.SECONDS).build();
    }

    private static final class a {
        private final KingdomsCommand a;
        private final String[] b;
        private final Duration c;

        private a(KingdomsCommand var1, String[] var2, Duration var3) {
            this.a = var1;
            this.b = var2;
            this.c = var3;
        }
    }

    protected enum SetterType {
        ADDITION {
            public final AnyNumber handle(AnyNumber var1, AnyNumber var2) {
                return var1.plus(var2);
            }
        }, SUBTRACTION {
            public final AnyNumber handle(AnyNumber var1, AnyNumber var2) {
                return var1.minus(var2);
            }
        }, SET {
            public final AnyNumber handle(AnyNumber var1, AnyNumber var2) {
                return var2;
            }
        };

        SetterType() {
        }

        public abstract AnyNumber handle(AnyNumber var1, AnyNumber var2);
    }
}

