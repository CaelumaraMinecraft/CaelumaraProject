package top.auspice.config.accessor;

import top.auspice.config.path.ConfigEntry;
import top.auspice.config.sections.ConfigSection;

import java.util.Map;
import java.util.Set;

/**
 * 配置读取器, 拥有确定的配置路径 ({@linkplain ConfigEntry})
 */
public interface ClearlyConfigAccessor extends ConfigAccessor, DefaultableConfigAccessor {
    ConfigEntry getPath();

//    String getStringPath();

    ClearlyConfigAccessor noDefault();

    boolean isSet(String[] path);

    Set<String> getKeys();

    ClearlyConfigAccessor gotoSection(String[] path);

    ConfigSection getSection();

    UndefinedPathConfigAccessor get(String[] path);

    Map<String, Object> getEntries();
}
