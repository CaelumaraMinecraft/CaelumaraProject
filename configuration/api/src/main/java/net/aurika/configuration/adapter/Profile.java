package net.aurika.configuration.adapter;

import net.aurika.configuration.accessor.ClearlyConfigAccessor;
import net.aurika.configuration.sections.ConfigSection;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.io.IOException;

@ApiStatus.Obsolete
@Deprecated
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
