package net.aurika.auspice.gui;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class GUIOption {
    protected final String name;
    protected final GUIOptionObject settings;
    private ItemStack a;
    protected Map<ClickType, Runnable> runnables;
    protected Consumer<String> conversation;

    public GUIOption(String var1, GUIOptionObject var2) {
        this.name = (String)Objects.requireNonNull(var1, "GUI option name is null");
        this.settings = (GUIOptionObject)Objects.requireNonNull(var2, "GUI option settings is null");
    }

    public void defineVariables(MessagePlaceholderProvider var1) {
        this.a = this.settings.defineVariables(var1);
    }

    public GUIOptionObject getSettings() {
        return this.settings;
    }

    public String getName() {
        return this.name;
    }

    public GUIOption clone() {
        GUIOption var1;
        (var1 = new GUIOption(this.name, this.settings)).conversation = this.conversation;
        var1.runnables = this.runnables;
        return var1;
    }

    public ItemStack getItem() {
        return (ItemStack)Objects.requireNonNull(this.a, "Getting item before defining variables");
    }

    public Map<ClickType, Runnable> getRunnables() {
        return this.runnables;
    }

    public void setRunnables(Map<ClickType, Runnable> var1) {
        this.runnables = var1;
    }

    public Consumer<String> getConversation() {
        return this.conversation;
    }

    public void setConversation(Consumer<String> var1) {
        this.conversation = var1;
    }

    public String toString() {
        return "GUIOption{name='" + this.name + '\'' + '}';
    }
}
