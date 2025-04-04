package net.aurika.config.accessor;

import net.aurika.config.path.ConfigEntry;
import net.aurika.config.sections.ConfigSection;

import java.util.Map;
import java.util.Set;

/**
 * 配置读取器, 拥有确定的配置路径 ({@linkplain ConfigEntry})
 */
@Deprecated
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
