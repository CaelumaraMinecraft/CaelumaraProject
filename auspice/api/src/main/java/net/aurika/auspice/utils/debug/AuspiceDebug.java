package net.aurika.auspice.utils.debug;

import net.aurika.auspice.user.Auspice;
import net.aurika.common.key.Key;
import net.aurika.common.key.KeyPatterns;
import org.jetbrains.annotations.NotNull;

public enum AuspiceDebug implements DebugEntry {
  FOLDER_REGISTRY("file.folder_registry"),
  SAVE_ALL("data.save.save_all");

  private final DebugKey id;

  AuspiceDebug(@KeyPatterns.IdentValue final @NotNull String identValueString) {
    this.id = new DebugKey(Key.key(Auspice.NAMESPACE, identValueString));
  }

  @Override
  public @NotNull DebugKey key() {
    return this.id;
  }
}
