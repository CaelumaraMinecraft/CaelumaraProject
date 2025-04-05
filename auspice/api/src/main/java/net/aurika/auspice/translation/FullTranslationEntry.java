package net.aurika.auspice.translation;

import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.auspice.translation.message.manager.MessageManager;
import net.aurika.auspice.translation.message.provider.MessageProvider;
import net.aurika.common.key.Key;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FullTranslationEntry {

  private final @NotNull Key managerID;
  private final @NotNull TranslationEntry entry;

  public FullTranslationEntry(@NotNull Key managerID, @NotNull TranslationEntry entry) {
    Validate.Arg.notNull(managerID, "managerID");
    Validate.Arg.notNull(entry, "entry");
    this.managerID = managerID;
    this.entry = entry;
  }

  public @NotNull Key managerID() {
    return managerID;
  }

  public @NotNull TranslationEntry entry() {
    return entry;
  }

  public @Nullable MessageManager getDiversityManager() {
    return MessageManager.getManager(managerID);
  }

  public @Nullable MessageProvider getMessage(@NotNull Diversity diversity, boolean useDefault) {
    Validate.Arg.notNull(diversity, "diversity");
    MessageManager manager = getDiversityManager();
    return manager != null ? manager.getMessage(diversity, this.entry, useDefault) : null;
  }

}
