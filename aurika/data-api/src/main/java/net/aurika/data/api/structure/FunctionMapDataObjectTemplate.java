package net.aurika.data.api.structure;

import net.aurika.checker.Checker;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class FunctionMapDataObjectTemplate<T> extends SimpleDataObjectTemplate<T> {
    private final @NotNull Function<SimpleData, T> entriesToObject;

    protected FunctionMapDataObjectTemplate(@NotNull Class<T> clazz, @NotNull Map<String, DataMetaType> template, @NotNull Function<SimpleData, T> entriesToObject) {
        super(clazz, template);
        Checker.Arg.notNull(entriesToObject, "entriesToObject");
        this.entriesToObject = entriesToObject;
    }

    public T toObject(@NotNull SimpleData entries) {
        return entriesToObject.apply(entries);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FunctionMapDataObjectTemplate<?> that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(entriesToObject, that.entriesToObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), entriesToObject);
    }
}
