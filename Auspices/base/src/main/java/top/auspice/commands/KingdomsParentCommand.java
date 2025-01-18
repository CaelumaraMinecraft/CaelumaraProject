package top.auspice.commands;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.diversity.Diversity;
import top.auspice.permission.PermissionDefaultValue;
import top.auspice.utils.enumeration.QuickEnumMap;
import top.auspice.utils.internal.Fn;

import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public abstract class KingdomsParentCommand extends KingdomsCommand {
    protected final Map<Diversity, Map<String, KingdomsCommand>> children;

    public KingdomsParentCommand(@NonNull String var1, @Nullable KingdomsParentCommand var2, PermissionDefaultValue var3) {
        super(var1, var2, var3);
        this.children = (Map) Fn.cast(new QuickEnumMap(SupportedLanguage.VALUES));
    }

    public KingdomsParentCommand(@NonNull String var1, @Nullable KingdomsParentCommand var2) {
        super(var1, var2, (PermissionDefaultValue)null);
        this.children = (Map)Fn.cast(new QuickEnumMap(SupportedLanguage.VALUES));
    }

    public KingdomsParentCommand(@NonNull String var1, boolean var2) {
        super(var1, var2);
        this.children = (Map)Fn.cast(new QuickEnumMap(SupportedLanguage.VALUES));
    }

    public KingdomsParentCommand(@NonNull String var1) {
        super(var1, false);
        this.children = (Map)Fn.cast(new QuickEnumMap(SupportedLanguage.VALUES));
    }

    public final @NonNull Collection<KingdomsCommand> getChildren(Diversity var1) {
        return ((Map)Objects.requireNonNull((Map)this.children.get(var1), () -> "Null children for command '" + super.name + "' for language: " + var1)).values();
    }

    public Map<Diversity, Map<String, KingdomsCommand>> getChildren() {
        return this.children;
    }

    public @NonNull List<String> tabComplete(CommandTabContext var1) {
        return ((CommandContext)var1).isAtArg(0) ? TabCompleteManager.tabCompleteSubCommands(((SimpleContextualMessageSender)var1).getMessageReceiver(), this, var1.args) : emptyTab();
    }

    public CommandResult execute(CommandContext var1) {
        Set var2 = Collections.newSetFromMap(new IdentityHashMap(this.children.size()));
        boolean var3 = var1.isPlayer() && var1.getKingdomPlayer().isAdmin();
        Stream var10000 = this.getChildren((SupportedLanguage)((SimpleContextualMessageSender)var1).getMessageContext().getLanguage()).stream();
        Objects.requireNonNull(var2);
        KingdomsCommand[] var4 = (KingdomsCommand[])var10000.filter(var2::add).filter((var2x) -> var3 || var2x.hasPermission(((SimpleContextualMessageSender)var1).getMessageReceiver())).toArray((var0) -> new KingdomsCommand[var0]);
        (new PaginatedCommandHelp(var1, 100, ((KingdomsCommand)this).getDisplayName(), () -> var4)).execute();
        return CommandResult.SUCCESS;
    }
}
