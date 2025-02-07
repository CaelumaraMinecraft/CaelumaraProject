package top.auspice.gui;

import net.aurika.config.abstraction.ConfigurationObject;
import net.aurika.config.sections.ConfigSection;

public abstract class GUITemplate implements ConfigurationObject {
    protected ConfigSection configSection;

    @Override
    public ConfigSection getConfigSection() {
        return this.configSection;
    }
}
