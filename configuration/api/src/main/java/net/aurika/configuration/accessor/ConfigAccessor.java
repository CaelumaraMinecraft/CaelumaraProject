package net.aurika.configuration.accessor;

import com.google.common.base.Enums;
import net.aurika.auspice.utils.compiler.condition.ConditionCompiler;
import net.aurika.auspice.utils.compiler.math.MathCompiler;

import java.util.List;

public interface ConfigAccessor extends DefaultableConfigAccessor {

  ConfigAccessor noDefault();

  boolean isSet(String[] path);

  Boolean getBoolean(String[] path);

  Integer getInteger(String[] path);

  Long getLong(String[] path);

  Double getDouble(String[] path);

  String getString(String[] path);

  List<Integer> getIntegerList(String[] path);

  List<String> getStringList(String[] path);

  default <T extends Enum<T>> T getEnum(String[] path, Class<T> enumClass) {
    return Enums.getIfPresent(enumClass, this.getString(path)).orNull();
  }

  MathCompiler.Expression getMath(String[] path);

  ConditionCompiler.LogicalOperand getCondition(String[] path);

}
