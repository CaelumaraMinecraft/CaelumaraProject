package net.aurika.dyanasis.api.executing.result;

import org.jetbrains.annotations.NotNull;

public interface DyanasisExecuteResult {

  DyanasisExecuteResultVoid VOID = new DyanasisExecuteResultVoid() {
  };

  static boolean isSuccessful(@NotNull DyanasisExecuteResult result) {
    return result instanceof DyanasisExecuteResultSuccess;
  }

  static boolean isFailed(@NotNull DyanasisExecuteResult result) {
    return result instanceof DyanasisExecuteResultFailed;
  }

  static boolean isVoid(@NotNull DyanasisExecuteResult result) {
    return result instanceof DyanasisExecuteResultVoid;
  }

}
