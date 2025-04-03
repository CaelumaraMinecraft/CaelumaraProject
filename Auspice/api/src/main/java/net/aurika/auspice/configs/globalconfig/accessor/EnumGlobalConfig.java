package net.aurika.auspice.configs.globalconfig.accessor;

import net.aurika.auspice.utils.compiler.condition.ConditionCompiler;
import net.aurika.auspice.utils.compiler.math.MathCompiler;
import net.aurika.config.accessor.ClearlyConfigAccessor;
import net.aurika.config.accessor.UndefinedPathConfigAccessor;

import java.util.List;

public interface EnumGlobalConfig {

  UndefinedPathConfigAccessor getManager();

  default String getString(String... path) {
    return this.getManager().getString(path);
  }

  default int getInt(String... path) {
    return this.getManager().getInteger(path);
  }

  default long getLong(String... path) {
    return this.getManager().getLong(path);
  }

  default boolean getBoolean(String... path) {
    return this.getManager().getBoolean(path);
  }

  default double getDouble(String... path) {
    return this.getManager().getDouble(path);
  }

  default List<Integer> getIntegerList(String... path) {
    return this.getManager().getIntegerList(path);
  }

  default List<String> getStringList(String... path) {
    return this.getManager().getStringList(path);
  }

  default MathCompiler.Expression getMath(String... path) {
    return this.getManager().getMath(path);
  }

  default ConditionCompiler.LogicalOperand getCondition(String... path) {
    return this.getManager().getCondition(path);
  }

  default <T extends Enum<T>> Enum<T> getEnum(String[] path, Class<T> type) {
    return this.getManager().getEnum(path, type);
  }

  default ClearlyConfigAccessor getSection() {
    return this.getManager().getSection();
  }

}
