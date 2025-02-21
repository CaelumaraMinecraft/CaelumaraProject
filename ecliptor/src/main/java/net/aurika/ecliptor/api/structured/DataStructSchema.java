package net.aurika.ecliptor.api.structured;

import net.aurika.ecliptor.api.structured.scalars.DataScalar;
import net.aurika.ecliptor.api.structured.scalars.DataScalarType;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public abstract class DataStructSchema<T extends StructuredDataObject> {

    protected final @NotNull Class<T> type;
    @Unmodifiable
    protected final @NotNull Map<String, DataScalarType> template;
    private final @NotNull String @NotNull [] keys;

    protected DataStructSchema(@NotNull Class<T> type, @NotNull Map<String, DataScalarType> template) {
        Validate.Arg.notNull(type, "type");
        Validate.Arg.notNull(template, "template");
        this.type = type;
        this.template = Collections.unmodifiableMap(template);
        this.keys = template.keySet().toArray(new String[0]);
    }

    public @NotNull Class<T> type() {
        return type;
    }

    public @Unmodifiable @NotNull Map<String, DataScalarType> template() {
        return template;
    }

    public int size() {
        return template.size();
    }

    public @NotNull String @NotNull [] keys() {
        return keys.clone();
    }

    public boolean isValidate(@NotNull T object) {
        Validate.Arg.notNull(object, "object");
        StructuredData data = object.structuredData();
        Objects.requireNonNull(data, "data");
        if (template.size() != data.size()) return false;

        Iterator<Map.Entry<String, DataScalarType>> templateIterator = template.entrySet().iterator();
        Iterator<Map.Entry<String, DataScalar>> objectIterator = object.structuredData().data().entrySet().iterator();
        while (templateIterator.hasNext()) {
            Map.Entry<String, DataScalarType> templateNext = templateIterator.next();
            Map.Entry<String, DataScalar> objectNext = objectIterator.next();
            if (templateNext.getKey().equals(objectNext.getKey()) && templateNext.getValue().equals(objectNext.getValue().type())) {  // 键 和 值类型 都符合模板
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public abstract @NotNull T structToObject(@NotNull StructuredData struct);

    public abstract @NotNull T plainToObject(@NotNull String plain);

    public abstract @NotNull String objectToPlain(@NotNull T object);

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DataStructSchema<?> that)) return false;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }
}
