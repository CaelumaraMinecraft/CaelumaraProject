package net.aurika.auspice.gui;

import com.cryptomorin.xseries.profiles.builder.XSkull;
import com.cryptomorin.xseries.profiles.objects.Profileable;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.texts.AuspiceLang;
import top.auspice.configs.texts.ContextualMessenger;
import net.aurika.text.placeholders.context.TextPlaceholderProvider;
import top.auspice.utils.enumeration.QuickEnumMap;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class OptionHandler implements ContextualMessenger {
    protected final String holder;
    protected final InteractiveGUI gui;
    protected Function<ItemStack, ItemStack> itemHandler;
    protected TextPlaceholderProvider settings;
    protected Consumer<String> conversation;
    protected Map<ClickType, Runnable> handlers;
    protected GUIOption object;

    protected OptionHandler(InteractiveGUI var1, String var2) {
        this.handlers = new QuickEnumMap(EnumCache.CLICK_TYPES);
        this.gui = var1;
        this.holder = var2;
        this.settings = new TextPlaceholderProvider();
    }

    public OptionHandler editItem(Function<ItemStack, ItemStack> var1) {
        this.itemHandler = var1;
        return this;
    }

    public OptionHandler setEdits(Object... var1) {
        this.settings.raws(var1);
        return this;
    }

    public String getName() {
        return this.holder;
    }

    @NotNull
    public OptionHandler var(@NotNull String variable, @Nullable Object value) {
        super.var(variable, value);
        return this;
    }

    public OptionHandler setConversation(BiConsumer<OptionHandler, String> var1) {
        this.conversation = (var2) -> {
            this.settings.raw("arg", var2);
            var1.accept(this, var2);
        };
        return this;
    }

    public void converse(Consumer<String> var1) {
        this.setConversation(var1);
        this.startConversation();
    }

    public OptionHandler setConversation(Consumer<String> var1) {
        this.conversation = var1;
        if (this.isPushed()) {
            this.object.setConversation(var1);
        }

        return this;
    }

    public OptionHandler setSettings(TextPlaceholderProvider var1) {
        this.settings = var1;
        return this;
    }

    public OptionHandler onNormalClicks(Runnable var1) {
        Objects.requireNonNull(var1);
        this.handlers.put(ClickType.LEFT, var1);
        this.handlers.put(ClickType.RIGHT, var1);
        return this;
    }

    public void startConversation() {
        if (this.object == null) {
            throw new IllegalStateException("Cannot start a conversation for an option that's not pushed to the GUI: " + this.holder + " in GUI " + this.gui.getName());
        } else {
            this.gui.startConversation(this.object, false);
        }
    }

    public OptionHandler onNormalClicks(Consumer<OptionHandler> var1) {
        Objects.requireNonNull(var1);
        Runnable var2 = () -> {
            var1.accept(this);
        };
        this.handlers.put(ClickType.LEFT, var2);
        this.handlers.put(ClickType.RIGHT, var2);
        return this;
    }

    public void pushHead(OfflinePlayer var1) {
        this.pushHead(var1, true);
    }

    public void pushHead(OfflinePlayer var1, boolean var2) {
        if (var1 != null) {
            if (var2) {
                this.settings.withContext(var1);
            }

            this.settings.parse("online", (var1.isOnline() ? AuspiceLang.PLAYERS_ONLINE : AuspiceLang.PLAYERS_OFFLINE).parse(var1, new Object[0]));
            this.itemHandler = (var1x) -> {
                ItemMeta var2;
                if ((var2 = var1x.getItemMeta()) instanceof SkullMeta) {
                    XSkull.of(var2).profile(Profileable.of(var1)).lenient().apply();
                    var1x.setItemMeta(var2);
                }

                return var1x;
            };
        }

        this.done();
    }

    public void endConversation() {
        this.gui.endConversation();
    }

    public OptionHandler on(Collection<ClickType> var1, BiConsumer<OptionHandler, ClickType> var2) {
        Objects.requireNonNull(var1);
        Objects.requireNonNull(var2);

        for (ClickType var3 : var1) {
            this.handlers.put(var3, () -> {
                var2.accept(this, var3);
            });
        }

        return this;
    }

    public OptionHandler on(ClickType var1, Runnable var2) {
        Objects.requireNonNull(var1);
        Objects.requireNonNull(var2);
        this.handlers.put(var1, var2);
        return this;
    }

    public OptionHandler on(ClickType var1, Consumer<OptionHandler> var2) {
        Objects.requireNonNull(var1);
        Objects.requireNonNull(var2);
        this.handlers.put(var1, () -> {
            var2.accept(this);
        });
        return this;
    }

    public GUIOption constructGUIOptionObject() {
        if (this.settings.getPrimaryTarget() == null) {
            this.settings.inheritContext(this.gui.getTextContext(), false);
        }

        this.settings.inheritPlaceholders(this.gui.getTextContext());
        return this.gui.constructOption(this.holder, this.settings);
    }

    public ConfigSection getConfig() {
        GUIOption var1 = this.constructGUIOptionObject();
        return var1 == null ? null : var1.getSettings().getConfig();
    }

    public void done() {
        this.object = this.gui.push(this);
    }

    protected boolean isPushed() {
        return this.object != null;
    }

    public @NotNull TextPlaceholderProvider getTextContext() {
        return this.settings;
    }

    public @NotNull CommandSender getMessageReceiver() {
        return this.gui.getOwner();
    }
}
