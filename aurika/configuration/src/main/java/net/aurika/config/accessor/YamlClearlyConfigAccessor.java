package net.aurika.config.accessor;

import org.jetbrains.annotations.NotNull;
import net.aurika.config.path.ConfigEntry;
import net.aurika.config.path.ConfigPath;
import net.aurika.config.sections.YamlNodeSection;
import net.aurika.snakeyaml.extension.nodes.interpret.NodeInterpreter;
import net.aurika.utils.Checker;
import top.auspice.utils.compiler.condition.ConditionCompiler;
import top.auspice.utils.compiler.math.MathCompiler;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class YamlClearlyConfigAccessor implements ClearlyConfigAccessor {
    protected final YamlNodeSection currentSection;
    protected final YamlNodeSection defaultSection;
    protected boolean noDefault = false;

    public YamlClearlyConfigAccessor(YamlNodeSection currentSection, YamlNodeSection defaultSection) {
        Checker.Arg.notNull(currentSection, "currentSection");
        Checker.Arg.notNull(defaultSection, "defaultSection");
        this.currentSection = currentSection;
        this.defaultSection = defaultSection;
    }

    public ConfigEntry getPath() {
        return this.defaultSection.getPath();
    }

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
        T var3 = this.currentSection.get(path, interpreter);
        if (var3 == null) {
            var3 = this.defaultSection.get(path, interpreter);
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
        return !this.noDefault && !this.isSet(path) ? this.defaultSection.get(path, NodeInterpreter.MATH) : this.currentSection.get(path, NodeInterpreter.MATH);
    }

    public ConditionCompiler.LogicalOperand getCondition(String... path) {
        return !this.noDefault && !this.isSet(path) ? this.defaultSection.get(path, NodeInterpreter.CONDITION) : this.currentSection.get(path, NodeInterpreter.CONDITION);
    }

    public YamlNodeSection getSection() {
        return this.currentSection;
    }

    public Map<String, Object> getEntries() {
        return this.currentSection.getSets(false);
    }

    public YamlClearlyConfigAccessor gotoSection(@NotNull String @NotNull [] path) {
        YamlNodeSection newCurrentSection = this.currentSection.getSection(path);
        YamlNodeSection newDefaultSection = this.defaultSection.getSection(path);
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
