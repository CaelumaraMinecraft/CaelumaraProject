package net.aurika.wands.constants.wand;

import org.bukkit.inventory.ItemStack;
import net.aurika.wands.constants.spells.objects.SpellObject;

public class StandardWand extends AbstractWand implements Wand {
    protected int[] spellUseHistory;
    public StandardWand(ItemStack itemStack, SpellObject[] spells, int[] spellUseHistory) {
        super(itemStack, spells);
        this.spellUseHistory = spellUseHistory;
    }



}
