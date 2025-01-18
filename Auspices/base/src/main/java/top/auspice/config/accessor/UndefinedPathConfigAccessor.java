package top.auspice.config.accessor;

import top.auspice.config.path.ConfigPath;
import top.auspice.utils.compiler.condition.ConditionCompiler;
import top.auspice.utils.compiler.math.MathCompiler;

import java.util.List;
import java.util.Set;

/**
 * 代表由一个 {@link ConfigPath} 读取的配置读取器. 就是说这个配置读取可能没有一个明确的路径
 */
public interface UndefinedPathConfigAccessor extends ConfigAccessor, DefaultableConfigAccessor {
    UndefinedPathConfigAccessor noDefault();

    UndefinedPathConfigAccessor withProperty(String property);

    UndefinedPathConfigAccessor applyProperties() ;

    UndefinedPathConfigAccessor withOption(String option, String replacement);

    UndefinedPathConfigAccessor clearExtras();

    ClearlyConfigAccessor getSection() throws UndefinedConfigPathAccessException;

    Set<String> getKeys() throws UndefinedConfigPathAccessException;

    boolean isSet(String[] path) throws UndefinedConfigPathAccessException;

    Boolean getBoolean(String[] path) throws UndefinedConfigPathAccessException;

    Integer getInteger(String[] path) throws UndefinedConfigPathAccessException;

    Long getLong(String[] path) throws UndefinedConfigPathAccessException;

    Double getDouble(String[] path) throws UndefinedConfigPathAccessException;

    String getString(String[] path) throws UndefinedConfigPathAccessException;

    List<Integer> getIntegerList(String[] path) throws UndefinedConfigPathAccessException;

    List<String> getStringList(String[] path) throws UndefinedConfigPathAccessException;

    default <T extends Enum<T>> T getEnum(String[] path, Class<T> enumClass) throws UndefinedConfigPathAccessException {
        return ConfigAccessor.super.getEnum(path, enumClass);
    }

    MathCompiler.Expression getMath(String[] path) throws UndefinedConfigPathAccessException;

    ConditionCompiler.LogicalOperand getCondition(String[] path) throws UndefinedConfigPathAccessException;
}