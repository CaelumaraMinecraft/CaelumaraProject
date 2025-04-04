package net.aurika.config.scope;

import net.aurika.config.CompleteConfigPath;

import java.io.File;

public class FileConfigScope implements ConfigScope {

  private final File file;

  public FileConfigScope(File file) {
    this.file = file;
  }

  public boolean isAvailable(CompleteConfigPath completePath) {
    String[]
    return completePath.
  }

}
