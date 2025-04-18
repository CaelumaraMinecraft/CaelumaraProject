package net.aurika.wands.constants.wand;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.data.database.dataprovider.SectionableDataSetter;
import net.aurika.wands.constants.spells.objects.SpellObject;

public abstract class AbstractWand implements Wand {

  protected ItemStack itemStack;
  protected SpellObject[] spells;

  public AbstractWand(ItemStack itemStack, SpellObject[] spells) {
    this.itemStack = itemStack;
    this.spells = spells;
  }

  @Override
  public ItemStack getItemStack() {
    return this.itemStack;
  }

  @Override
  public SpellObject[] getSpells() {
    return this.spells;
  }

  @Override
  public void serialize(@NotNull SectionableDataSetter dataSetter) {

  }

}
