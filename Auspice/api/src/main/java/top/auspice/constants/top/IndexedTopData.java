package top.auspice.constants.top;

import net.aurika.validate.Validate;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import net.aurika.util.collection.IndexedHashMap;

import java.util.*;
import java.util.function.Predicate;

public abstract class IndexedTopData<K, V> implements TopData<V> {
    private final IndexedHashMap<K, Integer> a = new IndexedHashMap<>(this.createKeyArray(0));
    private final Comparator<V> b = this.reversed();

    public IndexedTopData() {
    }

    public Optional<V> getTopPosition(int var1) {
        Validate.Arg.require(var1 > 0, "Kingdom top positions start at 1");
        K var2 = this.a.at(var1 - 1);
        return var2 == null ? Optional.empty() : Optional.ofNullable(this.fetchData(var2));
    }

    public @IntRange(from = 1L) Optional<Integer> getPositionOf(@NotNull V var1) {
        Objects.requireNonNull(var1, "Cannot get position of null data");
        Integer var2;
        return (var2 = this.a.get(this.getKey(var1))) == null ? Optional.empty() : Optional.of(var2 + 1);
    }

    @SuppressWarnings("unchecked")
    public @NotNull List<V> getTop(int var1, int var2, Predicate<V> vPredicate) {
        return (List<V>) Collections.unmodifiableList(this.a.subList(var1, var2, (K var2x) -> {   //TODO
            V fs = this.fetchData(var2x);
            if (fs == null) {
                return null;
            } else {
                return vPredicate != null && !vPredicate.test(fs) ? null : var2x;
            }
        }));
    }

    public void update(@NotNull Collection<V> var1) {
        if (!var1.isEmpty()) {
            K[] var2 = this.createKeyArray(var1.size());
            V[] var4;
            Arrays.sort(var4 = var1.toArray(this.createValueArray(var2.length)), this.b);

            for (int var3 = 0; var3 < var4.length; ++var3) {
                var2[var3] = this.getKey(var4[var3]);
            }

            this.a.set(var2, (var0) -> var0);
        }
    }

    @NotNull
    @Unmodifiable
    public List<V> getTop() {
        ArrayList<V> var1 = new ArrayList<>(this.a.size());
        K[] var2 = this.a.asArray();

        for (K var5 : var2) {
            var1.add(this.fetchData(var5));
        }

        return Collections.unmodifiableList(var1);
    }

    public int size() {
        return this.a.size();
    }

    protected abstract K[] createKeyArray(int var1);

    protected abstract V[] createValueArray(int var1);

    protected abstract K getKey(V var1);

    protected abstract V fetchData(K var1);
}

