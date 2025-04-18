package net.aurika.auspice.platform.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class RegisterExistingException extends RuntimeException {

  public RegisterExistingException(@NotNull Key key) {
    super("Register a exist key '" + key.asString() + "'");
  }

}
