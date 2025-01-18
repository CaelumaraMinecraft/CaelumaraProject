package top.auspice.config.accessor;

import org.jetbrains.annotations.Contract;
import top.auspice.config.path.ConfigEntry;
import top.auspice.config.path.ConfigPath;
import top.auspice.config.sections.YamlConfigSection;
import top.auspice.config.yaml.snakeyaml.NodeInterpreter;
import top.auspice.utils.Checker;
import top.auspice.utils.compiler.condition.ConditionCompiler;
import top.auspice.utils.compiler.math.MathCompiler;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class YamlClearlyConfigAccessor implements ClearlyConfigAccessor {
    protected final YamlConfigSection currentSection;
    protected final YamlConfigSection defaultSection;
    protected boolean noDefault = false;

    public YamlClearlyConfigAccessor(YamlConfigSection currentSection, YamlConfigSection defaultSection) {
        Checker.Argument.checkNotNull(currentSection, "currentSection");
        Checker.Argument.checkNotNull(defaultSection, "defaultSection");
        this.currentSection = currentSection;
        this.defaultSection = defaultSection;
    }

    public ConfigEntry getPath() {
        return this.defaultSection.getPath();
    }

    @Contract("-> this")
    public YamlClearlyConfigAccessor noDefault() {
        this.noDefault = true;
        return this;
    }

    public boolean isSet(String... path) {
        return this.currentSection.isSet(path);
    }

    public String getString(String... path) {
        return !this.noDefault && !this.isSet(path) ? this.defaultSection.getString(path) : this.currentSection.getString(path);
    }

    public <T> T get(NodeInterpreter<T> interpreter, String... path) {
        T var3 = this.currentSection.get(interpreter, path);
        if (var3 == null) {
            var3 = this.defaultSection.get(interpreter, path);
        }

        return var3;
    }

    public Set<String> getKeys() {
        return this.currentSection.getKeys();
    }

    public YamlUndefinedPathConfigAccessor get(String... path) {
        YamlUndefinedPathConfigAccessor accessor = new YamlUndefinedPathConfigAccessor(this.currentSection, this.defaultSection, new ConfigPath(path));
        if (this.noDefault) {
            accessor.noDefault();
        }

        return accessor;
    }

    public Boolean getBoolean(String... path) {
        return !this.noDefault && !this.isSet(path) ? this.defaultSection.getBoolean(path) : this.currentSection.getBoolean(path);
    }

    public Integer getInteger(String... path) {
        return !this.noDefault && !this.isSet(path) ? this.defaultSection.getInteger(path) : this.currentSection.getInteger(path);
    }

    public Long getLong(String... path) {
        return !this.noDefault && !this.isSet(path) ? this.defaultSection.getLong(path) : this.currentSection.getLong(path);
    }

    public Double getDouble(String... path) {
        return !this.noDefault && !this.isSet(path) ? this.defaultSection.getDouble(path) : this.currentSection.getDouble(path);
    }

    public List<Integer> getIntegerList(String... path) {
        return !this.noDefault && !this.isSet(path) ? this.defaultSection.getIntegerList(path) : this.currentSection.getIntegerList(path);
    }

    public List<String> getStringList(String... path) {
        return !this.noDefault && !this.isSet(path) ? this.defaultSection.getStringList(path) : this.currentSection.getStringList(path);
    }

    public MathCompiler.Expression getMath(String... path) {
        return !this.noDefault && !this.isSet(path) ? this.defaultSection.get(NodeInterpreter.MATH, path) : this.currentSection.get(NodeInterpreter.MATH, path);
    }

    public ConditionCompiler.LogicalOperand getCondition(String... path) {
        return !this.noDefault && !this.isSet(path) ? this.defaultSection.get(NodeInterpreter.CONDITION, path) : this.currentSection.get(NodeInterpreter.CONDITION, path);
    }

    public YamlConfigSection getSection() {
        return this.currentSection;
    }

    public Map<String, Object> getEntries() {
        return this.currentSection.getSets(false);
    }

    public YamlClearlyConfigAccessor gotoSection(String[] path) {
        YamlConfigSection newCurrentSection = this.currentSection.getSection(path);
        YamlConfigSection newDefaultSection = this.defaultSection.getSection(path);
        YamlClearlyConfigAccessor newClearly;
        if (newCurrentSection != null) {
            newClearly = new YamlClearlyConfigAccessor(newCurrentSection, this.noDefault ? newCurrentSection : newDefaultSection);
            if (this.noDefault) {
                newClearly.noDefault();
            }

            return newClearly;
        } else if (!this.noDefault && newDefaultSection != null) {
            newClearly = new YamlClearlyConfigAccessor(newDefaultSection, newDefaultSection);
            if (this.noDefault) {
                newClearly.noDefault();
            }

            return newClearly;
        } else {
            return null;
        }
    }
}
