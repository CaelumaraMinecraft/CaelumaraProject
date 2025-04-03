package net.aurika.auspice.server.bukkit.utils;

import net.aurika.auspice.configs.messages.context.MessageContext;
import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.text.compiler.TextCompiler;
import net.aurika.auspice.text.compiler.pieces.TextPiece;
import net.aurika.util.string.Strings;
import net.aurika.validate.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ItemUtil {

  public static boolean notNull(@Nullable ItemStack itemStack) {
    return !isNull(itemStack);
  }

  public static boolean isNull(@Nullable ItemStack itemStack) {
    return itemStack == null || itemStack.getType() == Material.AIR;
  }

  public static String getName(@NotNull ItemStack itemStack) {
    Validate.Arg.notNull(itemStack, "itemStack");
    if (itemStack.hasItemMeta()) {
      ItemMeta meta = itemStack.getItemMeta();
      //noinspection DataFlowIssue    //itemStack.hasItemMeta() == true 代表 ItemMeta meta != null;
      if (meta.hasDisplayName()) {
        return meta.getDisplayName();
      } else {
        return meta.hasItemName() ? meta.getItemName() : Strings.capitalize(itemStack.getType().name());
      }
    } else {
      return Strings.capitalize(itemStack.getType().name());
    }
  }

  public static void translate(ItemMeta var0, MessageContext var1) {
    if (var0.hasDisplayName()) {
      TextObject var2 = TextCompiler.compile(var0.getDisplayName());
      var0.setDisplayName(var2.build(var1));
    }

    if (var0.hasLore()) {
      List<String> var7 = var0.getLore();
      ArrayList<String> var3 = new ArrayList<>();
      TextObject var4 = null;

      if (var7 != null) {

        Iterator<String> it = var7.iterator();

        while (true) {
          TextObject message;
          do {
            if (!it.hasNext()) {
              var0.setLore(var3);
              return;
            }
          } while ((message = TextCompiler.compile(it.next()).evaluateDynamicPieces(var1)).isNull());

          TextObject var6 = message.findLastColors();
          if (var4 != null) {
            message = var4.merge(message);
          }

          if (var6 != null) {
            var4 = var6;
          }

          for (TextObject textObject : message.splitBy(false, (var0x) -> var0x instanceof TextPiece.NewLine)) {
            var6 = textObject;
            var3.add(var6.build(var1));
          }
        }
      }
    }
  }

}
