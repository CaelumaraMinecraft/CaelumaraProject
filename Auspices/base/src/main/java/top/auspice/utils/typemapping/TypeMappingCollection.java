package top.auspice.utils.typemapping;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

public class TypeMappingCollection<From, To> extends AbstractCollection<To> implements Collection<To> {

    private final Collection<From> original;
    private final Function<From, ? extends To> converter;
    private final Function<To, ? extends From> reverseConverter;

    public TypeMappingCollection(Collection<From> original, Function<From, ? extends To> converter, Function<To, ? extends From> reverseConverter) {
        this.original = original;
        this.converter = converter;
        this.reverseConverter = reverseConverter;
    }

    @Override
    public int size() {
        return this.original.size();
    }

    @Override
    public boolean isEmpty() {
        return this.original.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.original.contains(this.reverseConverter.apply((To) o));
    }

    @Override
    public @NotNull Iterator<To> iterator() {
        return new Itr();
    }

    @Override
    public @NotNull Object[] toArray() {
        Object[] array = this.original.toArray();
        for (int i = 0; i < array.length; i++) {
            Object o = array[i];
            array[i] = this.reverseConverter.apply((To) o);
        }
        return array;
    }

    @Override
    public boolean add(To p) {
        return this.original.add(this.reverseConverter.apply(p));
    }

    @Override
    public boolean remove(Object o) {
        return this.original.remove(this.reverseConverter.apply((To) o));
    }

    @Override
    public void clear() {
        this.original.clear();
    }

    protected class Itr implements Iterator<To> {

        @Override
        public boolean hasNext() {
            return TypeMappingCollection.this.original.iterator().hasNext();
        }

        @Override
        public To next() {
            return TypeMappingCollection.this.converter.apply(TypeMappingCollection.this.original.iterator().next());
        }

        @Override
        public void remove() {
            TypeMappingCollection.this.original.iterator().remove();
        }
    }

}
