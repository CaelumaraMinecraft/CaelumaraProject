package net.aurika.config.path;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import top.auspice.utils.Pair;
import top.auspice.utils.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigPathBuilder implements Cloneable {
    private final ConfigPath configPath;
    private List<Pair<String, String>> replaceVars;
    private List<String> properties;

    public ConfigPathBuilder(ConfigPath configPath) {
        this.configPath = Objects.requireNonNull(configPath);
    }

    @Contract("_ -> this")
    public ConfigPathBuilder withProperty(@NotNull String property) {
        Objects.requireNonNull(property);
        if (this.properties == null) {
            this.properties = new ArrayList<>(2);
        }

        this.properties.add(property);
        return this;
    }

    @Contract("-> this")
    public ConfigPathBuilder clearExtras() {
        this.replaceVars = null;
        this.properties = null;
        return this;
    }

    @Contract("_, _ -> this")
    public ConfigPathBuilder replace(String variable, String replacement) {
        Validate.notEmpty(variable, "Variable cannot be null or empty");
        Validate.notEmpty(replacement, "Replacement cannot be null or empty");
        if (this.replaceVars == null) {
            this.replaceVars = new ArrayList<>(2);
        }

        this.replaceVars.add(Pair.of(variable, replacement));
        return this;
    }

    public String[] build() {
        return this.configPath.build(this.replaceVars, this.properties);
    }

    public ConfigPathBuilder clone() {
        ConfigPathBuilder var1 = new ConfigPathBuilder(this.configPath);
        var1.properties = new ArrayList<>(this.properties);
        var1.replaceVars = new ArrayList<>(this.replaceVars);
        return var1;
    }

    public String getOptionPath() {
        return String.join(".", this.build());
    }
}