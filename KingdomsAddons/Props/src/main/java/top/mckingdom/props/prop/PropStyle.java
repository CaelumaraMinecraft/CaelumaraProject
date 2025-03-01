package top.mckingdom.props.prop;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.config.accessor.KeyedConfigAccessor;
import org.kingdoms.config.implementation.KeyedYamlConfigAccessor;
import org.kingdoms.utils.config.ConfigPath;
import org.kingdoms.utils.config.adapters.YamlResource;

public class PropStyle {
    private final YamlResource yaml;
    private final String name;
    private final PropType type;

    public PropStyle(String name, YamlResource yamlResource) {
        this.name = name;
        this.yaml = yamlResource;
        this.type = PropRegistry.get().getType(yamlResource.getConfig().getString("type"));
    }

    public @NonNull KeyedConfigAccessor getOption(String... var1) {
        return (new KeyedYamlConfigAccessor(this.yaml, new ConfigPath(var1))).noDefault();
    }

    public void loadSettings() {
    }

    public String getName() {
        return this.name;
    }
}
