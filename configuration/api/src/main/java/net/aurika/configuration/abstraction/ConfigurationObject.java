package net.aurika.configuration.abstraction;

import net.aurika.configuration.sections.ConfigSection;
import org.jetbrains.annotations.ApiStatus;

/**
 * 代表这个对象是与配置有关系的(如从配置文件中解析出来的对象, )
 */
@ApiStatus.Obsolete
@Deprecated
public interface ConfigurationObject {

  ConfigSection configSection();

}
