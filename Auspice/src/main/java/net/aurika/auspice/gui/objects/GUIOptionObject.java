package net.aurika.auspice.gui.objects;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.XTag;
import com.cryptomorin.xseries.profiles.builder.XSkull;
import com.cryptomorin.xseries.profiles.objects.Profileable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.aurika.auspice.text.compiler.TextObject;
import net.aurika.text.placeholders.context.TextPlaceholderProvider;
import net.aurika.text.placeholders.context.PlaceholderProvider;
import net.aurika.auspice.utils.reflection.Reflect;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GUIOptionObject implements GUIOptionBuilder {
    protected final String name;
    protected final int[] slots;
    protected final XSound.Record sound;
    protected final List<String> commands;
    protected final GUIInteractionType interactionType;
    protected final ItemStack item;
    protected final TextObject itemName;
    protected final TextObject[] lore;
    protected final TextObject message;
    protected final TextObject skull;
    protected final ConfigSection config;
    private static final boolean a = Reflect.classExists("org.bukkit.inventory.meta.components.JukeboxPlayableComponent");

    public GUIOptionObject(String var1, int[] var2, XSound.Record var3, List<String> var4, GUIInteractionType var5, ItemStack var6, TextObject var7, TextObject[] var8, TextObject var9, TextObject var10, ConfigSection var11) {
        this.name = (String) Objects.requireNonNull(var1);
        this.slots = (int[])Objects.requireNonNull(var2);
        this.sound = var3;
        this.commands = var4;
        this.interactionType = var5;
        this.item = (ItemStack)Objects.requireNonNull(var6);
        this.itemName = var7;
        this.lore = var8;
        this.message = var9;
        this.skull = var10;
        this.config = var11;
    }

    public GUIInteractionType getInteractionType() {
        return this.interactionType;
    }

    public ConfigSection getConfig() {
        return this.config;
    }

    public GUIOptionObject getOption(PlaceholderProvider var1) {
        return this;
    }

    public int[] getSlots() {
        return this.slots;
    }

    public TextObject getMessage() {
        return this.message;
    }

    public XSound.Record getSound() {
        return this.sound;
    }

    public List<String> getCommands() {
        return this.commands;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public ItemStack defineVariables(TextPlaceholderProvider var1) {
        if (this.item.getType() == Material.AIR) {
            return new ItemStack(Material.AIR);
        } else {
            ItemStack var2;
            ItemMeta var3 = (var2 = this.item.clone()).getItemMeta();
            if (this.itemName != null) {
                var3.setDisplayName(this.itemName.build(var1));
            }

            JukeboxPlayableComponent var4;
            if (a && XTag.ITEMS_MUSIC_DISCS.isTagged(XMaterial.matchXMaterial(var2)) && (var4 = var3.getJukeboxPlayable()) != null) {
                var4.setShowInTooltip(false);
                var3.setJukeboxPlayable(var4);
            }

            if (this.lore != null) {
                ArrayList var11 = new ArrayList(this.lore.length);
                TextObject var5 = null;
                TextObject[] var6;
                int var7 = (var6 = this.lore).length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    TextObject var9;
                    if (!(var9 = var6[var8].evaluateDynamicPieces(var1)).isNull()) {
                        TextObject var10 = var9.findLastColors();
                        if (var5 != null) {
                            var9 = var5.merge(new TextObject[]{var9});
                        }

                        if (var10 != null) {
                            var5 = var10;
                        }

                        var9.splitLines().forEach((var2x) -> {
                            var11.add(var2x.build(var1));
                        });
                    }
                }

                var3.setLore(var11);
            }

            if (this.skull != null) {
                String var12 = this.skull.buildPlain(var1);
                var3 = (ItemMeta) XSkull.of(var3).profile(Profileable.detect(var12)).apply();
            }

            var2.setItemMeta(var3);
            return var2;
        }
    }

    public String getName() {
        return this.name;
    }
}
