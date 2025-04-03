package net.aurika.auspice.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

public enum SessionAnswer {
  YES,
  NO,
  OK;

  /**
   * Gets a session answer by name, this name can be lower-cased.
   */
  @Nullable
  public static SessionAnswer get(@NotNull String name) {
    Objects.requireNonNull(name);

    SessionAnswer var4;
    try {
      String var3 = name.toUpperCase(Locale.ENGLISH);
      Objects.requireNonNull(var3);
      var4 = SessionAnswer.valueOf(var3);
    } catch (IllegalArgumentException var2) {
      var4 = null;
    }

    return var4;
  }
}