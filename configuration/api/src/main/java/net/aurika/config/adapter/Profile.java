package net.aurika.config.adapter;

import net.aurika.config.accessor.ClearlyConfigAccessor;
import net.aurika.config.sections.ConfigSection;

import java.io.File;
import java.io.IOException;

public interface Profile {

  ConfigSection getConfig();

  ClearlyConfigAccessor accessor();

  void saveConfig();

  default boolean isLoaded() {
    return this.getConfig() != null;
  }

  File getFile();

  /**
   * 生成对应的配置文件
   */
  default void createFile() {
    try {
      this.getFile().createNewFile();
    } catch (IOException var2) {
      var2.printStackTrace();
    }
  }

  Profile load();

}
