package top.auspice.gui;

import top.auspice.config.abstraction.ConfigurationObject;
import top.auspice.config.sections.ConfigSection;

public abstract class GUITemplate implements ConfigurationObject {
    protected ConfigSection configSection;

    @Override
    public ConfigSection getConfigSection() {
        return this.configSection;
    }
}
