package net.aurika.config.validation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.config.sections.ConfigSection;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class ValidationContext {
    /**
     * 配置节
     */
    @NotNull
    protected final ConfigSection section;
    /**
     * {@linkplain ValidationContext#section} 的子配置节的键, 是可选的
     */
    @Nullable
    protected final String mappingKey;
    @NotNull
    protected final Map<String, ConfigValidator> validatorMap;
    @NotNull
    protected final
    Collection<ValidationFailure> exceptions;

    public ValidationContext(@NotNull ConfigSection section, @NotNull Map<String, ConfigValidator> validatorMap, @NotNull Collection<ValidationFailure> exceptions) {
        this(section, null, validatorMap, exceptions);
    }

    public ValidationContext(@NotNull ConfigSection section, String mappingKey, @NotNull Map<String, ConfigValidator> validatorMap, @NotNull Collection<ValidationFailure> exceptions) {
        Objects.requireNonNull(section);
        Objects.requireNonNull(validatorMap);
        Objects.requireNonNull(exceptions);
        this.section = section;
        this.mappingKey = mappingKey;
        this.validatorMap = validatorMap;
        this.exceptions = exceptions;
    }

    public ValidationContext delegate(ConfigSection section) {
        return new ValidationContext(section, this.validatorMap, this.exceptions);
    }

    /**
     *
     * @param mappingKey
     * @return
     */
    public ValidationContext delegate(String mappingKey) {
        return new ValidationContext(this.section, mappingKey, this.validatorMap, this.exceptions);
    }

    /**
     * 获取要被验证的配置节
     *
     * @return 在验证配置的时候要被验证的配置节, 不是作为模板的配置节
     */
    @NotNull
    public ConfigSection getSection() {
        return this.section;
    }

    public String getMappingKey() {
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
        return this.fail(new ValidationFailure(ValidationFailure.Severity.ERROR, this.section, message));
    }

    public void warn(String message) {
        this.fail(new ValidationFailure(ValidationFailure.Severity.WARNING, this.section, message));
    }
}
