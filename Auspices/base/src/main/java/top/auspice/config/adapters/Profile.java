package top.auspice.config.adapters;

import top.auspice.config.accessor.ClearlyConfigAccessor;
import top.auspice.config.sections.ConfigSection;

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
