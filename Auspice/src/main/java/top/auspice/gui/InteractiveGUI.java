package top.auspice.gui;


import com.cryptomorin.xseries.XSound;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import top.auspice.gui.objects.GUIObject;
import top.auspice.gui.objects.GUIOptionBuilder;
import top.auspice.configs.texts.ContextualMessenger;
import net.aurika.text.placeholders.context.TextPlaceholderProvider;
import net.aurika.text.placeholders.context.PlaceholderProvider;
import top.auspice.main.BukkitAuspiceLoader;
import top.auspice.managers.chat.ChatInputHandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class InteractiveGUI implements ContextualMessenger {
    @NotNull
    public static final Companion Companion = new Companion();
    @NotNull
    private TextPlaceholderProvider a;
    @NotNull
    private final GUIObject b;
    @NotNull
    private final Player c;
    @NotNull
    private final ConfigSection d;
    @Nullable
    private Runnable e;
    @Nullable
    private BiConsumer<GUIOption, ChatInputHandler<?>> f;
    private boolean g;
    @Nullable
    private BukkitTask h;
    private boolean i;
    private boolean j;

    @NotNull
    public final TextPlaceholderProvider getTextContext() {
        return this.a;
    }

    public final void setMessageContext(@NotNull TextPlaceholderProvider var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.a = var1;
    }

    @NotNull
    protected final GUIObject getGuiSettings() {
        return this.b;
    }

    @NotNull
    public final Player getOwner() {
        return this.c;
    }

    @NotNull
    public final ConfigSection getConfig() {
        return this.d;
    }

    @Nullable
    public final Runnable getOnClose() {
        return this.e;
    }

    @Nullable
    public final BiConsumer<GUIOption, ChatInputHandler<?>> getEavesdrop() {
        return this.f;
    }

    public final void setEavesdrop(@Nullable BiConsumer<GUIOption, ChatInputHandler<?>> var1) {
        this.f = var1;
    }

    public final boolean getAbsoluteOnClose() {
        return this.g;
    }

    protected final boolean getSwitched() {
        return this.i;
    }

    protected final void setSwitched(boolean var1) {
        this.i = var1;
    }

    protected final boolean getRefreshed() {
        return this.j;
    }

    protected final void setRefreshed(boolean var1) {
        this.j = var1;
    }

    protected InteractiveGUI(@NotNull GUIObject var1, @NotNull Player var2, @NotNull ConfigSection var3, @NotNull TextPlaceholderProvider var4) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        Intrinsics.checkNotNullParameter(var3, "");
        Intrinsics.checkNotNullParameter(var4, "");
        super();
        this.b = var1;
        this.d = var3;
        this.a = var4;
        this.c = var2;
    }

    public abstract void resetOptions();

    public final void endConversation() {
        ChatInputManager.endConversation(this.c);
    }

    public final void startConversation(@Nullable String var1) {
        this.startConversation(var1, false);
    }

    public abstract void startConversation(@Nullable String var1, boolean var2);

    public final void startConversation(@NotNull GUIOption var1, boolean var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        ChatInputHandler<GUIOption> var3 = new ChatInputHandler<>(var1);
        var3.onInput(InteractiveGUI::a);
        var3.onCancel(InteractiveGUI::a);
        if (var2) {
            var3.sync();
        }

        BiConsumer var10000 = this.f;
        if (var10000 != null) {
            var10000.accept(var1, var3);
        }

        ChatInputManager.startConversation(this.c, var3);
        this.c.closeInventory();
    }

    @NotNull
    public final InteractiveGUI push(@NotNull String var1, @NotNull Runnable var2, @NotNull Object... var3) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        Intrinsics.checkNotNullParameter(var3, "");
        return this.push(var1, var2, (Consumer)null, Arrays.copyOf(var3, var3.length));
    }

    @NotNull
    public final InteractiveGUI push(@NotNull String var1, @NotNull Runnable var2, @Nullable Consumer<String> var3, @NotNull Object... var4) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        Intrinsics.checkNotNullParameter(var4, "");
        this.option(var1).setEdits(Arrays.copyOf(var4, var4.length)).onNormalClicks(var2).setConversation(var3).done();
        return this;
    }

    @NotNull
    public final InteractiveGUI eavesdrop(@NotNull BiConsumer<GUIOption, ChatInputHandler<?>> var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.f = var1;
        return this;
    }

    @NotNull
    public final OptionHandler option(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        return new OptionHandler(this, var1);
    }

    @Nullable
    protected abstract GUIOption push(@NotNull OptionHandler var1);

    public final boolean is(@NotNull GUIPathContainer var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        return Intrinsics.areEqual(this.getName(), var1.getGUIPath());
    }

    public final boolean is(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        return Intrinsics.areEqual(this.getName(), var1);
    }

    @Nullable
    public final GUIOption constructOption(@Nullable String var1, @NotNull TextPlaceholderProvider var2) {
        Intrinsics.checkNotNullParameter(var2, "");
        GUIOptionBuilder var10000 = this.b.getOption(var1);
        if (var10000 == null) {
            return null;
        } else {
            GUIOptionBuilder var3 = var10000;
            return new GUIOption(var1, Companion.evalCondOption(var3, (PlaceholderProvider)var2));
        }
    }

    public final void inherit(@NotNull InteractiveGUI var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.f = var1.f;
        this.e = var1.e;
    }

    @NotNull
    public final String getName() {
        String var10000 = this.b.getName();
        Intrinsics.checkNotNullExpressionValue(var10000, "");
        return var10000;
    }

    @Nullable
    public abstract ReusableOptionHandler getReusableOption(@NotNull String var1);

    public final boolean wasSwitched() {
        return this.i;
    }

    public final boolean wasRefreshed() {
        return this.j;
    }

    public void setRemainingOptions() {
        this.push("close", InteractiveGUI::b);
    }

    public final void performOpenActions() {
        Strings.performCommands((OfflinePlayer)this.c, (Collection)this.b.getCommands());
        if (this.b.getSound() != null) {
            XSound.SoundPlayer var10000 = this.b.getSound().soundPlayer();
            Player[] var1;
            (var1 = new Player[1])[0] = this.c;
            var10000.forPlayers(var1).play();
        }

        if (this.b.getMessage() != null) {
            this.b.getMessage().getSimpleProvider().send((CommandSender)this.c, this.a);
        }

    }

    public final void open(boolean var1, boolean var2) {
        if (Bukkit.isPrimaryThread()) {
            this.internalOpen(var1, var2);
        } else {
            Bukkit.getScheduler().runTask((Plugin) BukkitAuspiceLoader.get(), InteractiveGUI::a);
        }
    }

    protected abstract void internalOpen(boolean var1, boolean var2);

    @NotNull
    public abstract List<OptionCategory> getOptions(@NotNull String var1);

    public final void onClose(@Nullable Runnable var1) {
        this.onClose(false, var1);
    }

    public final void onClose(boolean var1, @Nullable Runnable var2) {
        this.g = var1;
        this.e = var2;
    }

    public final void close() {
        this.c.closeInventory();
    }

    public final void handleClosing() {
        this.cancelRefreshTask();
        Runnable var10000 = this.e;
        if (var10000 != null) {
            var10000.run();
        }
    }

    public final void onCloseEvent() {
        this.i = false;
        this.j = false;
    }

    @Nullable
    public final BukkitTask getRefreshTask() {
        return this.h;
    }

    @NotNull
    public String toString() {
        return Reflection.getOrCreateKotlinClass(this.getClass()).getQualifiedName() + '[' + this.getName() + '|' + this.hashCode() + ']';
    }

    public final void setRefreshTask(@Nullable BukkitTask var1) {
        this.cancelRefreshTask();
        this.h = var1;
    }

    public final void cancelRefreshTask() {
        BukkitTask var10000 = this.h;
        if (var10000 != null) {
            var10000.cancel();
        }
    }

    @NotNull
    public final ConfigSection getOptionsSection() {
        ConfigSection var10000 = this.d;
        String[] var1;
        (var1 = new String[1])[0] = "options";
        var10000 = var10000.getSection(var1);
        Intrinsics.checkNotNullExpressionValue(var10000, "");
        return var10000;
    }

    @NotNull
    public CommandSender getMessageReceiver() {
        return (CommandSender)this.c;
    }

    @JvmOverloads
    public final void open(boolean var1) {
        open$default(this, var1, false, 2, (Object)null);
    }

    @JvmOverloads
    public final void open() {
        open$default(this, false, false, 3, (Object)null);
    }

    private static final Boolean a(ChatInputHandler var0, GUIOption var1, AsyncPlayerChatEvent var2) {
        Intrinsics.checkNotNullParameter(var0, "");
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        Consumer var3;
        if ((var3 = ((GUIOption)var0.getSession()).getConversation()) == null) {
            throw new IllegalStateException("No conversation handler is set for " + var1);
        } else {
            var3.accept(var2.getMessage());
            return Boolean.FALSE;
        }
    }

    private static final void a(InteractiveGUI var0) {
        Intrinsics.checkNotNullParameter(var0, "");
        var0.open(false, false);
    }

    private static final void b(InteractiveGUI var0) {
        Intrinsics.checkNotNullParameter(var0, "");
        var0.c.closeInventory();
    }

    private static final void a(InteractiveGUI var0, boolean var1, boolean var2) {
        Intrinsics.checkNotNullParameter(var0, "");
        var0.internalOpen(var1, var2);
    }

    @Metadata(
            mv = {1, 9, 0},
            k = 1,
            xi = 48,
            d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0007¢\u0006\u0004\b\u0007\u0010\b"},
            d2 = {"Lorg/kingdoms/gui/InteractiveGUI$Companion;", "", "Lorg/kingdoms/gui/objects/GUIOptionBuilder;", "p0", "Lorg/kingdoms/texts/placeholders/context/PlaceholderProvider;", "p1", "Lorg/kingdoms/gui/objects/GUIOptionObject;", "evalCondOption", "(Lorg/kingdoms/gui/objects/GUIOptionBuilder;Lorg/kingdoms/texts/placeholders/context/PlaceholderProvider;)Lorg/kingdoms/gui/objects/GUIOptionObject;", "<init>", "()V"}
    )
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final GUIOptionObject evalCondOption(@NotNull GUIOptionBuilder var1, @NotNull PlaceholderProvider var2) {
            Intrinsics.checkNotNullParameter(var1, "");
            Intrinsics.checkNotNullParameter(var2, "");
            if (var1 instanceof ConditionalGUIOptionObject) {
                GUIOptionObject var3;
                Intrinsics.checkNotNull(var3 = var1.getOption(var2));
                return var3;
            } else {
                return (GUIOptionObject)var1;
            }
        }
    }
}