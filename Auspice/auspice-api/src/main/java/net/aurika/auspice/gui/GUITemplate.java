package net.aurika.auspice.gui;

import net.aurika.config.abstraction.ConfigurationObject;
import net.aurika.config.sections.ConfigSection;
import org.jetbrains.annotations.NotNull;

public abstract class GUITemplate implements ConfigurationObject {
    protected @NotNull ConfigSection configSection;

    @Override
    public @NotNull ConfigSection configSection() {
        return this.configSection;
    }
}
