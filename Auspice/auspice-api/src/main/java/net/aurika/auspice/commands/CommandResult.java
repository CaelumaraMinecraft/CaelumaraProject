package net.aurika.auspice.commands;

import org.jetbrains.annotations.ApiStatus.Internal;

public enum CommandResult {
  SUCCESS,
  FAILED,
  PARTIAL,
  @Internal
  ERROR
}