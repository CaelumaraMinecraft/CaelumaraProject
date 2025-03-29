package net.aurika.auspice.server.inventory;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public interface InventorySlot {

  int @NotNull [] slots();

  /**
   * For things that are only intended to be a single slot.
   */
  default int getSlot() {
    int[] slots = slots();
    if (slots.length != 1) {
      throw new UnsupportedOperationException("Not a single slot: " + this);
    }
    return slots[0];
  }

  static int @NotNull [] inherit(InventorySlot @NotNull ... slots) {
    return Arrays.stream(slots).flatMapToInt(x -> Arrays.stream(x.slots())).toArray();
  }

  static int @NotNull [] range(int min, int max) {
    int[] slots = new int[max - min];

    int index = 0;
    for (int i = min; i < max; i++) {
      slots[index++] = i;
    }
    return slots;
  }

  enum Player implements InventorySlot {
    HELMET(39),
    CHESTPLATE(38),
    LEGGINGS(37),
    BOOTS(36),
    UPPER_STORAGE(9, 35),
    ARMOR(HELMET, CHESTPLATE, LEGGINGS, BOOTS),

    HOTBAR(0, 8),
    OFFHAND(40);

    private final int[] slots;

    Player(int @NotNull [] slots) {
      this.slots = slots;
    }

    Player(Player @NotNull ... slots) {
      this.slots = inherit(slots);
    }

    Player(int min, int max) {
      this.slots = range(min, max);
    }

    Player(int slot) {
      this.slots = new int[]{slot};
    }

    @Override
    public int @NotNull [] slots() {
      return slots;
    }
  }

}
