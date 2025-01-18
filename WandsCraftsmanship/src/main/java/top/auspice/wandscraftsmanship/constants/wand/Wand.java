package top.auspice.wandscraftsmanship.constants.wand;

import org.bukkit.inventory.ItemStack;
import top.auspice.data.Serializable;
import top.auspice.wandscraftsmanship.constants.spells.objects.SpellObject;

public interface Wand extends Serializable {

    /**
     * 获取这个法杖实例对应的物品堆对象
     */
    ItemStack getItemStack();

    /**
     * 获取这个法杖中装填的法术
     */
    SpellObject[] getSpells();

}
