package net.aurika.configuration.validator;

import net.aurika.configuration.part.ConfigPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class ValidationContext {

  /**
   * 配置节
   */
  protected final @NotNull ConfigPart config;
  /**
   * {@linkplain ValidationContext#config} 的子配置节的键, 是可选的
   */
  protected final @Nullable String mappingKey;
  @NotNull
  protected final Map<String, ConfigValidator> validatorMap;
  @NotNull
  protected final
  Collection<ValidationFailure> exceptions;

  public ValidationContext(@NotNull ConfigPart config, @NotNull Map<String, ConfigValidator> validatorMap, @NotNull Collection<ValidationFailure> exceptions) {
    this(config, null, validatorMap, exceptions);
  }

  public ValidationContext(@NotNull ConfigPart config, String mappingKey, @NotNull Map<String, ConfigValidator> validatorMap, @NotNull Collection<ValidationFailure> exceptions) {
    Objects.requireNonNull(config);
    Objects.requireNonNull(validatorMap);
    Objects.requireNonNull(exceptions);
    this.config = config;
    this.mappingKey = mappingKey;
    this.validatorMap = validatorMap;
    this.exceptions = exceptions;
  }

  public ValidationContext delegate(ConfigPart config) {
    return new ValidationContext(config, this.validatorMap, this.exceptions);
  }

  /**
   * @param mappingKey
   * @return
   */
  public ValidationContext delegate(String mappingKey) {
    return new ValidationContext(this.config, mappingKey, this.validatorMap, this.exceptions);
  }

  /**
   * 获取要被验证的配置节
   *
   * @return 在验证配置的时候要被验证的配置节, 不是作为模板的配置节
   */
  @NotNull
  public ConfigPart config() {
    return this.config;
  }

  public String mappingKey() {
    return this.mappingKey;
  }

  @NotNull
  public Map<String, ConfigValidator> getValidatorMap() {
    return this.validatorMap;
  }

  @Nullable
  public ConfigValidator getValidator(String name) {
    return this.validatorMap.get(name);
  }

  @NotNull
  public Collection<ValidationFailure> getExceptions() {
    return this.exceptions;
  }

  @NotNull
  public ValidationFailure fail(@NotNull ValidationFailure failure) {
    Objects.requireNonNull(failure);
    this.exceptions.add(failure);
    return failure;
  }

  /**
   * 增加一个 {@linkplain ValidationFailure}, 因为 {@linkplain ValidationFailure.Severity#ERROR}, 所以方法应立即返回
   * <p>
   * {@code return context.err(message)}
   *
   * @param message 消息
   */
  @NotNull
  public ValidationFailure err(String message) {
    return this.fail(new ValidationFailure(ValidationFailure.Severity.ERROR, this.config, message));
  }

  public void warn(String message) {
    this.fail(new ValidationFailure(ValidationFailure.Severity.WARNING, this.config, message));
  }

}
