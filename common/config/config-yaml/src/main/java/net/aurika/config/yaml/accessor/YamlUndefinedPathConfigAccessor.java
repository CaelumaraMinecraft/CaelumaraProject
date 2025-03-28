package net.aurika.config.yaml.accessor;

import net.aurika.auspice.utils.arrays.ArrayUtils;
import net.aurika.auspice.utils.compiler.condition.ConditionCompiler;
import net.aurika.auspice.utils.compiler.math.MathCompiler;
import net.aurika.common.snakeyaml.nodes.interpret.NodeInterpretContext;
import net.aurika.common.snakeyaml.nodes.interpret.NodeInterpreter;
import net.aurika.config.accessor.UndefinedConfigPathAccessException;
import net.aurika.config.accessor.UndefinedPathConfigAccessor;
import net.aurika.config.yaml.adapter.YamlContainer;
import net.aurika.config.yaml.adapter.YamlWithDefaults;
import net.aurika.config.path.ConfigPath;
import net.aurika.config.path.ConfigPathBuilder;
import net.aurika.config.sections.YamlNodeSection;
import net.aurika.util.Checker;
import org.snakeyaml.engine.v2.nodes.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class YamlUndefinedPathConfigAccessor implements Cloneable, UndefinedPathConfigAccessor {

  private final YamlNodeSection currentConfigRoot;
  private final YamlNodeSection defaultConfigRoot;
  private final ConfigPathBuilder configPathBuilder;
  private boolean noDefault;

  public YamlUndefinedPathConfigAccessor(YamlContainer yamlContainer, ConfigPath configPath) {
    this(
        yamlContainer == null ? null : yamlContainer.getConfig(),
        !(yamlContainer instanceof YamlWithDefaults) ? null : ((YamlWithDefaults) yamlContainer).getDefaults(),
        configPath
    );
  }

  public YamlUndefinedPathConfigAccessor(YamlNodeSection currentConfigRoot, YamlNodeSection defaultConfigRoot, ConfigPath configPath) {
    Checker.Arg.notNull(configPath, "configPath", "option for undefined config accessor cannot be null");
    this.configPathBuilder = new ConfigPathBuilder(configPath);
    this.currentConfigRoot = currentConfigRoot;
    this.defaultConfigRoot = defaultConfigRoot;
  }

  private YamlUndefinedPathConfigAccessor(YamlNodeSection var1, YamlNodeSection var2, ConfigPathBuilder configPathBuilder, boolean noDefault) {
    this.currentConfigRoot = var1;
    this.defaultConfigRoot = var2;
    this.configPathBuilder = configPathBuilder;
    this.noDefault = noDefault;
  }

  public YamlUndefinedPathConfigAccessor withProperty(String property) {
    this.configPathBuilder.withProperty(property);
    return this;
  }

  public YamlUndefinedPathConfigAccessor clearExtras() {
    this.configPathBuilder.clearExtras();
    return this;
  }

  public String getOptionPath() {
    return this.configPathBuilder.getOptionPath();
  }

  public YamlUndefinedPathConfigAccessor noDefault() {
    this.noDefault = true;
    return this;
  }

  public YamlUndefinedPathConfigAccessor withOption(String option, String replacement) {
    this.configPathBuilder.replace(option, replacement);
    return this;
  }

  public YamlUndefinedPathConfigAccessor forWorld(String property) {
    return this.withProperty(property).isSet(new String[0]) ? this : this.clearExtras().withProperty("default");
  }

  public YamlUndefinedPathConfigAccessor applyProperties() throws UndefinedConfigPathAccessException {
    return new YamlUndefinedPathConfigAccessor(
        this.currentConfigRoot, this.defaultConfigRoot, new ConfigPath(this.configPathBuilder.build()));
  }

  public Node getNode(String[] path2) throws UndefinedConfigPathAccessException {
    String[] path1;
    try {
      path1 = this.configPathBuilder.build();
    } catch (Exception e) {
      // TODO
      throw new UndefinedConfigPathAccessException(e);
    }
    String[] path = ArrayUtils.merge(path1, path2);
    Node currentNode = this.currentConfigRoot.findNode(path);
    if (currentNode != null) {
      return currentNode;
    } else {
      return !this.noDefault && this.defaultConfigRoot != null ? this.defaultConfigRoot.findNode(path) : null;
    }
  }

  public boolean isSet(String[] path) {
    return this.getNode(path) != null;
  }

  public <T> T get(String[] path, NodeInterpreter<T> nodeInterpreter) {
    return nodeInterpreter.parse(this.getNode(path));
  }

  public <T> T get(String[] path, NodeInterpreter<T> interpreter, NodeInterpretContext<T> context) {
    return interpreter.parse(context.withNode(this.getNode(path)));
  }

  public Set<String> getKeys() {
    YamlClearlyConfigAccessor var1 = this.getSection();
    return (var1 == null ? new HashSet<>() : var1.getKeys());
  }

  public Boolean getBoolean(String[] path) throws UndefinedConfigPathAccessException {
    return NodeInterpreter.BOOLEAN.parse(this.getNode(path));
  }

  public Integer getInteger(String[] path) throws UndefinedConfigPathAccessException {
    return this.get(path, NodeInterpreter.INT);
  }

  public Long getLong(String[] path) throws UndefinedConfigPathAccessException {
    return NodeInterpreter.LONG.parse(this.getNode(path));
  }

  public Double getDouble(String[] path) throws UndefinedConfigPathAccessException {
    return NodeInterpreter.DOUBLE.parse(this.getNode(path));
  }

  public String getString(String[] path) throws UndefinedConfigPathAccessException {
    return NodeInterpreter.STRING.parse(this.getNode(path));
  }

  public List<Integer> getIntegerList(String[] path) throws UndefinedConfigPathAccessException {
    return this.get(path, NodeInterpreter.INT_LIST);
  }

  public List<String> getStringList(String[] path) throws UndefinedConfigPathAccessException {
    return NodeInterpreter.STRING_LIST.parse(this.getNode(path));
  }

  public MathCompiler.Expression getMath(String[] path) throws UndefinedConfigPathAccessException {
    return NodeInterpreter.MATH.parse(this.getNode(path));
  }

  public ConditionCompiler.LogicalOperand getCondition(String[] path) throws UndefinedConfigPathAccessException {
    return NodeInterpreter.CONDITION.parse(this.getNode(path));
  }

  public YamlClearlyConfigAccessor getSection() throws UndefinedConfigPathAccessException {
    YamlNodeSection currentSection = this.currentConfigRoot.getSection(this.configPathBuilder.build());
    YamlNodeSection defaultSection = this.defaultConfigRoot == null ? null : this.defaultConfigRoot.getSection(
        this.configPathBuilder.build());
    if (currentSection == null) {
      if (!this.noDefault && defaultSection != null) {
        YamlClearlyConfigAccessor clearly = new YamlClearlyConfigAccessor(defaultSection, defaultSection);
        if (this.noDefault) {
          clearly.noDefault();
        }

        return clearly;
      } else {
        return null;
      }
    } else {
      return new YamlClearlyConfigAccessor(
          currentSection, !this.noDefault && defaultSection != null ? defaultSection : currentSection);
    }
  }

  public YamlUndefinedPathConfigAccessor clone() throws CloneNotSupportedException {
    return new YamlUndefinedPathConfigAccessor(
        this.currentConfigRoot, this.defaultConfigRoot, this.configPathBuilder.clone(), this.noDefault);
  }

  public String toString() {
    return "KeyedYamlConfigAccessor( " + this.getOptionPath() + " )";
  }

  public YamlUndefinedPathConfigAccessor getManager() {
    return this;
  }

}
