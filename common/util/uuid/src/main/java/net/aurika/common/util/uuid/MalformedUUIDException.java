package net.aurika.common.util.uuid;

import org.jetbrains.annotations.NotNull;

public final class MalformedUUIDException extends IllegalArgumentException {

  private final @NotNull CharSequence uuid;

  public MalformedUUIDException(@NotNull CharSequence uuid, @NotNull Throwable throwable) {
    super("" + '\'' + uuid + '\'', throwable);
    this.uuid = uuid;
  }

  public @NotNull CharSequence uuidChars() {
    return this.uuid;
  }

}