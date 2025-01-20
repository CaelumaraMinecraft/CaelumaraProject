package top.mckingdom.auspice.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class ItemUtil {

    public static boolean hasItem(Inventory inv, Material material, int amount) {
        int i = 0;
        for (ItemStack itemStack : inv) {
            if (itemStack.getType() == material) {
                i = i + itemStack.getAmount();
            }
        }
        return (i >= amount);
    }

    /**
     * 将对应数量的物品从任意物品栏中移除
     * <p>
     * 调用时应确保物品栏中的物品足够 hasItem()
     *
     * @throws StackOverflowError 当要移除的物品数量大于物品栏内对应物品的数量时抛出
     */
    public static void removeItem(Inventory inv, Material material, int amount) {
        int i = amount;                   //剩下还要吃掉多少个物品
//        try {
        for (ItemStack itemStack : inv) {
            if (itemStack.getType() == material) {
                int a = itemStack.getAmount();
                if (a > amount) {
                    itemStack.setAmount(a - amount);
                } else {
                    itemStack.setType(Material.AIR);
                    i = i - a;
                }
            }
        }
//        } catch (StackOverflowError error) {
//            AuspiceAddon.getInstance().getServer().getLogger().severe("你必须在检查物品栏内的物品是否够用后才能移除对应物品(又或者是出了什么别的问题?)");
//        }

    }


}