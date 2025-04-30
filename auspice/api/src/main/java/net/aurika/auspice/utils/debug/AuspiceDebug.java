package net.aurika.auspice.utils.debug;

import net.aurika.auspice.user.Auspice;
import net.aurika.common.ident.Ident;
import net.aurika.common.ident.IdentPatterns;
import org.jetbrains.annotations.NotNull;

public enum AuspiceDebug implements DebugEntry {
  FOLDER_REGISTRY("file.folder_registry"),
  SAVE_ALL("data.save.save_all");

  private final DebugIdent id;

  AuspiceDebug(@IdentPatterns.IdentPath final @NotNull String identValueString) {
    this.id = new DebugIdent(Ident.ident(Auspice.GROUP_STRING, identValueString));
  }

  @Override
  public @NotNull DebugIdent ident() {
    return this.id;
  }
}
