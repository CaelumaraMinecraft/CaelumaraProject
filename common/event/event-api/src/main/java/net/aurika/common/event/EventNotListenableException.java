package net.aurika.common.event;

import org.jetbrains.annotations.NotNull;

public class EventNotListenableException extends IllegalArgumentException {

  public EventNotListenableException() {
  }

  public EventNotListenableException(@NotNull String message) {
    super(message);
  }

  public EventNotListenableException(@NotNull String message, @NotNull Throwable cause) {
    super(message, cause);
  }

  public EventNotListenableException(@NotNull Throwable cause) {
    super(cause);
  }

}
