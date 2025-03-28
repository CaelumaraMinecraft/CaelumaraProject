package net.aurika.config.abstraction;

import net.aurika.config.sections.ConfigSection;

/**
 * 代表这个对象是与配置有关系的(如从配置文件中解析出来的对象, )
 */
public interface ConfigurationObject {

  ConfigSection configSection();

}
