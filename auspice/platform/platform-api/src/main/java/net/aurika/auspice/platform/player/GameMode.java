package net.aurika.auspice.platform.player;

import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

public interface GameMode extends Translatable {

  int value();

  enum Java implements GameMode {
    CREATIVE(1, "creative"),
    SURVIVAL(0, "survival"),
    ADVENTURE(2, "adventure"),
    SPECTATOR(3, "spectator");

    private final int value;
    private final String translationKey;

    Java(int value, String name) {
      this.value = value;
      this.translationKey = "gameMode." + name;
    }

    @Override
    public int value() { return this.value; }

    @Override
    public @NotNull String translationKey() { return this.translationKey; }
  }

}
