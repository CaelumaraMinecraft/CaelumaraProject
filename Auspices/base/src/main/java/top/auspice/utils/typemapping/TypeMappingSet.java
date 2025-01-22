package top.auspice.utils.typemapping;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

@Deprecated
public class TypeMappingSet<From, To> extends AbstractSet<To> implements Set<To> {
    private final Set<From> original;
    private final Function<From, To> converter;
    private final Function<To, From> reverseConverter;

    public TypeMappingSet(Set<From> original, Function<From, To> converter, Function<To, From> reverseConverter) {
        this.original = original;
        this.converter = converter;
        this.reverseConverter = reverseConverter;
    }

    @Override
    public boolean add(To p) {
        return this.original.add(reverseConverter.apply(p));
    }

    @Override
    public @NotNull Iterator<To> iterator() {
        return new Itr();
    }

    @Override
    public int size() {
        return this.original.size();
    }

    protected class Itr implements Iterator<To> {

        @Override
        public boolean hasNext() {
            return TypeMappingSet.this.original.iterator().hasNext();
        }

        @Override
        public To next() {
            return converter.apply(TypeMappingSet.this.original.iterator().next());
        }

        @Override
        public void remove() {
            TypeMappingSet.this.original.iterator().remove();
        }

    }
}
