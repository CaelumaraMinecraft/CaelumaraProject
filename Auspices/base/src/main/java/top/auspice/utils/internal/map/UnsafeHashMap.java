package top.auspice.utils.internal.map;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class UnsafeHashMap<K, V> implements Cloneable, Map<K, V> {
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final int MAXIMUM_CAPACITY = 1073741824;
    public static final float DEFAULT_LOAD_FACTOR = 0.75F;
    public static final int TREEIFY_THRESHOLD = 8;
    public static final int UNTREEIFY_THRESHOLD = 6;
    public static final int MIN_TREEIFY_CAPACITY = 64;
    public final float loadFactor;
    public transient Set<K> keySet;
    public transient Collection<V> values;
    public transient Node<K, V>[] table;
    public transient Set<Map.Entry<K, V>> entrySet;
    private transient int a;
    private transient int b;
    private int c;

    public UnsafeHashMap(int var1, float var2) {
        if (var1 < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + var1);
        } else {
            if (var1 > MAXIMUM_CAPACITY) {
                var1 = MAXIMUM_CAPACITY;
            }

            if (!(var2 <= 0.0F) && !Float.isNaN(var2)) {
                this.loadFactor = var2;
                this.c = tableSizeFor(var1);
            } else {
                throw new IllegalArgumentException("Illegal load factor: " + var2);
            }
        }
    }

    public UnsafeHashMap(int var1) {
        this(var1, DEFAULT_LOAD_FACTOR);
    }

    public UnsafeHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    public UnsafeHashMap(Map<? extends K, ? extends V> var1) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.putMapEntries(var1, false);
    }

    @SafeVarargs
    public static <K, V> UnsafeHashMap<K, V> of(Map.Entry<? extends K, ? extends V>... var0) {
        UnsafeHashMap<K, V> var1 = new UnsafeHashMap<>();
        var1.putEntries(false, var0);
        return var1;
    }

    public static int hash(Object var0) {
        if (var0 == null) {
            throw new NullPointerException("Cannot hash null key to map");
        } else {
            int var1;
            return (var1 = var0.hashCode()) ^ var1 >>> 16;
        }
    }

    public static Class<?> comparableClassFor(Object var0) {
        if (var0 instanceof Comparable) {
            Class<?> var5 = var0.getClass();
            if (var5 == String.class) {
                return var5;
            }

            Type[] var1 = var5.getGenericInterfaces();
            if (var1 != null) {
                Type[] var2 = var1;
                int var3 = var1.length;

                for (int var4 = 0; var4 < var3; ++var4) {
                    Type var6;
                    ParameterizedType var7;
                    if ((var6 = var2[var4]) instanceof ParameterizedType && (var7 = (ParameterizedType) var6).getRawType() == Comparable.class && (var1 = var7.getActualTypeArguments()) != null && var1.length == 1 && var1[0] == var5) {
                        return var5;
                    }
                }
            }
        }

        return null;
    }

    public static int compareComparables(Class<?> var0, Object var1, Object var2) {
        return var2 != null && var2.getClass() == var0 ? ((Comparable) var1).compareTo(var2) : 0;
    }

    public static int tableSizeFor(int var0) {
        if ((var0 = (var0 = (var0 = (var0 = (var0 = --var0 | var0 >>> 1) | var0 >>> 2) | var0 >>> 4) | var0 >>> 8) | var0 >>> 16) < 0) {
            return 1;
        } else {
            return var0 >= MAXIMUM_CAPACITY ? MAXIMUM_CAPACITY : var0 + 1;
        }
    }

    public final void putMapEntries(Map<? extends K, ? extends V> var1, boolean var2) {
        this.putMapEntries(var1, false, var2);
    }

    public final void putMapEntries(Map<? extends K, ? extends V> var1, boolean var2, boolean var3) {
        int var4;
        if ((var4 = var1.size()) > 0) {
            if (this.table == null) {
                int var5;
                float var7;
                if ((var5 = (var7 = (float) var4 / this.loadFactor + 1.0F) < 1.0737418E9F ? (int) var7 : MAXIMUM_CAPACITY) > this.c) {
                    this.c = tableSizeFor(var5);
                }
            } else if (var4 > this.c) {
                this.resize();
            }

            for (Map.Entry<? extends K, ? extends V> entry : var1.entrySet()) {
                K var6 = entry.getKey();
                V var10 = entry.getValue();
                this.putVal(hash(var6), var6, var10, var2, var3);
            }
        }

    }

    @SafeVarargs
    public final void putEntries(boolean var1, Map.Entry<? extends K, ? extends V>... var2) {
        int var3;
        if ((var3 = var2.length) > 0) {
            int var4;
            if (this.table == null) {
                float var8;
                if ((var4 = (var8 = (float) var3 / this.loadFactor + 1.0F) < 1.0737418E9F ? (int) var8 : MAXIMUM_CAPACITY) > this.c) {
                    this.c = tableSizeFor(var4);
                }
            } else if (var3 > this.c) {
                this.resize();
            }

            var4 = var2.length;

            for (int var7 = 0; var7 < var4; ++var7) {
                Map.Entry<? extends K, ? extends V> var5 = var2[var7];
                K var6 = (var5).getKey();
                V var10 = var5.getValue();
                this.putVal(hash(var6), var6, var10, false, var1);
            }
        }

    }

    public int size() {
        return this.a;
    }

    public boolean isEmpty() {
        return this.a == 0;
    }

    public V get(Object var1) {
        Node<K, V> var2 = this.getNode(hash(var1), var1);
        return var2 == null ? null : var2.value;
    }

    public @Nullable Node<K, V> getNode(int var1, @NonNull Object var2) {
        if (this.table != null && this.table.length != 0) {
            Node<K, V> var3 = this.table[this.table.length - 1 & var1];
            if (var3 == null) {
                return null;
            } else {
                Object var4;
                if (var3.hash == var1 && ((var4 = var3.key) == var2 || var2.equals(var4))) {
                    return var3;
                } else {
                    Node<K, V> var5 = var3.next;
                    if (var5 == null) {
                        return null;
                    } else if (var3 instanceof TreeNode<K, V>) {
                        return ((TreeNode<K, V>) var3).getTreeNode(var1, var2);
                    } else {
                        while (var5.hash != var1 || (var4 = var5.key) != var2 && !var2.equals(var4)) {
                            if ((var5 = var5.next) == null) {
                                return null;
                            }
                        }

                        return var5;
                    }
                }
            }
        } else {
            return null;
        }
    }

    public boolean containsKey(@NonNull Object var1) {
        return this.getNode(hash(var1), var1) != null;
    }

    public @Nullable V put(@NonNull K var1, @Nullable V var2) {
        return this.putVal(hash(var1), var1, var2, false, true);
    }

    public final V putVal(int var1, @NonNull K var2, @Nullable V var3, boolean var4, boolean var5) {
        int var6;
        Node<K, V>[] var10 = this.table;
        if (var10 == null || (var6 = var10.length) == 0) {
            var6 = (var10 = this.resize()).length;
        }

        var6 = var6 - 1 & var1;
        Node<K, V> var7 = var10[var6];
        if (var7 == null) {
            var10[var6] = this.newNode(var1, var2, var3, null);
        } else {
            Object var8;
            Node<K, V> var11;
            if (var7.hash == var1 && ((var8 = var7.key) == var2 || var2.equals(var8))) {
                var11 = var7;
            } else if (var7 instanceof TreeNode<K, V>) {
                var11 = ((TreeNode<K, V>) var7).putTreeVal(this, var10, var1, var2, var3);
            } else {
                int var9 = 0;

                while (true) {
                    if ((var11 = var7.next) == null) {
                        var7.next = this.newNode(var1, var2, var3, null);
                        if (var9 >= 7) {
                            this.treeifyBin(var10, var1);
                        }
                        break;
                    }

                    if (var11.hash == var1 && ((var8 = var11.key) == var2 || var2.equals(var8))) {
                        break;
                    }

                    var7 = var11;
                    ++var9;
                }
            }

            if (var11 != null) {
                V var12 = var11.value;
                if (!var4 || var12 == null) {
                    var11.value = var3;
                }

                return var12;
            }
        }

        ++this.b;
        if (++this.a > this.c) {
            this.resize();
        }

        return null;
    }

    public Node<K, V>[] resize() {
        Node<K, V>[] var1 = this.table;
        int var2 = var1 == null ? 0 : var1.length;
        int var3 = this.c;
        int var5 = 0;
        int var4;
        if (var2 > 0) {
            if (var2 >= MAXIMUM_CAPACITY) {
                this.c = Integer.MAX_VALUE;
                return var1;
            }

            if ((var4 = var2 << 1) < MAXIMUM_CAPACITY && var2 >= 16) {
                var5 = var3 << 1;
            }
        } else if (var3 > 0) {
            var4 = var3;
        } else {
            var4 = 16;
            var5 = 12;
        }

        if (var5 == 0) {
            float var12 = (float) var4 * this.loadFactor;
            var5 = var4 < MAXIMUM_CAPACITY && var12 < 1.0737418E9F ? (int) var12 : Integer.MAX_VALUE;
        }

        this.c = var5;
        Node<K, V>[] var13 = new Node[var4];
        this.table = var13;
        if (var1 != null) {
            for (var5 = 0; var5 < var2; ++var5) {
                Node<K, V> var6 = var1[var5];
                if ((var6) != null) {
                    var1[var5] = null;
                    if (var6.next == null) {
                        var13[var6.hash & var4 - 1] = var6;
                    } else if (var6 instanceof TreeNode<K, V>) {
                        ((TreeNode<K, V>) var6).split(this, var13, var5, var2);
                    } else {
                        Node<K, V> var7 = null;
                        Node<K, V> var8 = null;
                        Node<K, V> var9 = null;
                        Node<K, V> var10 = null;

                        Node<K, V> var11;
                        do {
                            var11 = var6.next;
                            if ((var6.hash & var2) == 0) {
                                if (var8 == null) {
                                    var7 = var6;
                                } else {
                                    var8.next = var6;
                                }

                                var8 = var6;
                            } else {
                                if (var10 == null) {
                                    var9 = var6;
                                } else {
                                    var10.next = var6;
                                }

                                var10 = var6;
                            }

                            var6 = var11;
                        } while (var11 != null);

                        if (var8 != null) {
                            var8.next = null;
                            var13[var5] = var7;
                        }

                        if (var10 != null) {
                            var10.next = null;
                            var13[var5 + var2] = var9;
                        }
                    }
                }
            }
        }

        return var13;
    }

    public void treeifyBin(Node<K, V>[] var1, int var2) {
        int var3;
        if (var1 != null && (var3 = var1.length) >= 64) {
            Node<K, V> var7;
            if ((var7 = var1[var2 &= var3 - 1]) != null) {
                TreeNode<K, V> var4 = null;
                TreeNode<K, V> var5 = null;

                do {
                    TreeNode<K, V> var6 = this.replacementTreeNode(var7, null);
                    if (var5 == null) {
                        var4 = var6;
                    } else {
                        var6.a = var5;
                        var5.next = var6;
                    }

                    var5 = var6;
                } while ((var7 = var7.next) != null);

                if ((var1[var2] = var4) != null) {
                    var4.treeify(var1);
                }
            }

        } else {
            this.resize();
        }
    }

    public void putAll(@NonNull Map<? extends K, ? extends V> var1) {
        this.putMapEntries(var1, true);
    }

    public void putAllIfAbsent(@NonNull Map<? extends K, ? extends V> var1) {
        this.putMapEntries(var1, true, true);
    }

    public V remove(@NonNull Object var1) {
        Node<K, V> var2;
        return (var2 = this.removeNode(hash(var1), var1, null, false, true)) == null ? null : var2.value;
    }

    public final @Nullable Node<K, V> removeNode(int var1, @NonNull Object var2, @Nullable Object var3, boolean var4, boolean var5) {
        if (this.table != null && this.table.length != 0) {
            int var6 = this.table.length - 1 & var1;
            Node<K, V> var7;
            if ((var7 = this.table[var6]) == null) {
                return null;
            } else {
                Node<K, V>[] var8 = this.table;
                Node<K, V> var9 = null;
                Object var11;
                if (var7.hash != var1 || (var11 = var7.key) != var2 && !var2.equals(var11)) {
                    Node<K, V> var10;
                    if ((var10 = var7.next) != null) {
                        if (var7 instanceof TreeNode<K, V>) {
                            var9 = ((TreeNode<K, V>) var7).getTreeNode(var1, var2);
                        } else {
                            label77:
                            {
                                while (var10.hash != var1 || (var11 = var10.key) != var2 && !var2.equals(var11)) {
                                    var7 = var10;
                                    if ((var10 = var10.next) == null) {
                                        break label77;
                                    }
                                }

                                var9 = var10;
                            }
                        }
                    }
                } else {
                    var9 = var7;
                }

                Object var12;
                if (var9 == null || var4 && (var12 = var9.value) != var3 && (var3 == null || !var3.equals(var12))) {
                    return null;
                } else {
                    if (var9 instanceof TreeNode) {
                        ((TreeNode<K, V>) var9).removeTreeNode(this, var8, var5);
                    } else if (var9 == var7) {
                        var8[var6] = var9.next;
                    } else {
                        var7.next = var9.next;
                    }

                    ++this.b;
                    --this.a;
                    return var9;
                }
            }
        } else {
            return null;
        }
    }

    public void clear() {
        ++this.b;
        if (this.table != null && this.a > 0) {
            Node<K, V>[] var1 = this.table;
            this.a = 0;

            for (int var2 = 0; var2 < var1.length; ++var2) {
                var1[var2] = null;
            }
        }

    }

    public boolean containsValue(@NonNull Object var1) {
        if (this.table != null && this.a > 0) {
            Node<K, V>[] var10000 = this.table;
            Node<K, V>[] var2;
            int var3 = (var2 = this.table).length;

            for (int var4 = 0; var4 < var3; ++var4) {
                for (Node<K, V> var5 = var2[var4]; var5 != null; var5 = var5.next) {
                    Object var6;
                    if ((var6 = var5.value) == var1 || var1.equals(var6)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public @NonNull Set<K> keySet() {
        return this.keySet == null ? (this.keySet = new KeySet()) : this.keySet;
    }

    public @NonNull Collection<V> values() {
        return this.values == null ? (this.values = new b()) : this.values;
    }

    public void initValues() {
        this.values = new b();
    }

    public @NonNull Set<Map.Entry<K, V>> entrySet() {
        return this.entrySet == null ? (this.entrySet = new a()) : this.entrySet;
    }

    public @Nullable V getOrDefault(@NonNull Object var1, @Nullable V var2) {
        Node<K, V> var3 = this.getNode(hash(var1), var1);
        return var3 == null ? var2 : var3.value;
    }

    public @Nullable V putIfAbsent(@NonNull K var1, @Nullable V var2) {
        return this.putVal(hash(var1), var1, var2, true, true);
    }

    public boolean remove(@NonNull Object var1, @Nullable Object var2) {
        return this.removeNode(hash(var1), var1, var2, true, true) != null;
    }

    public boolean replace(@NonNull K var1, V var2, V var3) {
        Node<K, V> var4 = this.getNode(hash(var1), var1);
        if (var4 == null) {
            return false;
        } else if (Objects.equals(var4.value, var2)) {
            var4.value = var3;
            return true;
        } else {
            return false;
        }
    }

    public @Nullable V replace(@NonNull K var1, @Nullable V var2) {
        Node<K, V> var4 = this.getNode(hash(var1), var1);
        if (var4 == null) {
            return null;
        } else {
            V var3 = var4.value;
            var4.value = var2;
            return var3;
        }
    }

    public @Nullable V computeIfAbsent(@NonNull K var1, @NonNull Function<? super K, ? extends V> var2) {
        Objects.requireNonNull(var2);
        int var3 = hash(var1);
        int var7 = 0;
        TreeNode<K, V> var8 = null;
        Node<K, V> var9 = null;
        Node<K, V>[] var4;
        int var5;
        if (this.a > this.c || (var4 = this.table) == null || (var5 = var4.length) == 0) {
            var5 = (var4 = this.resize()).length;
        }

        int var6;
        V var12;
        Node<K, V> var13;
        if ((var13 = var4[var6 = var5 - 1 & var3]) != null) {
            if (var13 instanceof TreeNode) {
                var9 = (var8 = (TreeNode<K, V>) var13).getTreeNode(var3, var1);
            } else {
                label67:
                {
                    Node<K, V> var10 = var13;

                    Object var11;
                    while (var10.hash != var3 || (var11 = var10.key) != var1 && !var1.equals(var11)) {
                        ++var7;
                        if ((var10 = var10.next) == null) {
                            break label67;
                        }
                    }

                    var9 = var10;
                }
            }

            if (var9 != null && (var12 = var9.value) != null) {
                return var12;
            }
        }

        if ((var12 = var2.apply(var1)) == null) {
            return null;
        } else if (var9 != null) {
            var9.value = var12;
            return var12;
        } else {
            if (var8 != null) {
                var8.putTreeVal(this, var4, var3, var1, var12);
            } else {
                var4[var6] = this.newNode(var3, var1, var12, var13);
                if (var7 >= 7) {
                    this.treeifyBin(var4, var3);
                }
            }

            ++this.b;
            ++this.a;
            return var12;
        }
    }

    public V computeIfPresent(@NonNull K var1, @NonNull BiFunction<? super K, ? super V, ? extends V> var2) {
        Objects.requireNonNull(var2);
        int var3 = hash(var1);
        Node<K, V> var4;
        if ((var4 = this.getNode(var3, var1)) == null) {
            return null;
        } else {
            V var5;
            if ((var5 = var4.value) == null) {
                return null;
            } else if ((var5 = var2.apply(var1, var5)) != null) {
                var4.value = var5;
                return var5;
            } else {
                this.removeNode(var3, var1, null, false, true);
                return null;
            }
        }
    }

    public V compute(@NonNull K key, @NonNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int var3 = hash(key);
        Node<K, V>[] var4;
        int var5;
        if (this.a <= this.c && this.table != null && (var5 = this.table.length) != 0) {
            var4 = this.table;
        } else {
            var5 = (var4 = this.resize()).length;
        }

        TreeNode<K, V> var7 = null;
        Node<K, V> var8 = null;
        int var9 = 0;
        int var6;
        V v;
        Node<K, V> var12;
        if ((var12 = this.table[var6 = var5 - 1 & var3]) != null) {
            if (var12 instanceof TreeNode) {
                var8 = (var7 = (TreeNode<K, V>) var12).getTreeNode(var3, key);
            } else {
                label66:
                {
                    Node<K, V> kvNode = var12;

                    while (kvNode.hash != var3 || (v = (V) kvNode.key) != key && !key.equals(v)) {   //TODO cast
                        ++var9;
                        if ((kvNode = kvNode.next) == null) {
                            break label66;
                        }
                    }

                    var8 = kvNode;
                }
            }
        }

        V var13 = var8 == null ? null : var8.value;
        v = remappingFunction.apply(key, var13);
        if (var8 != null) {
            if (v != null) {
                var8.value = v;
            } else {
                this.removeNode(var3, key, null, false, true);
            }
        } else if (v != null) {
            if (var7 != null) {
                var7.putTreeVal(this, var4, var3, key, v);
            } else {
                var4[var6] = this.newNode(var3, key, v, var12);
                if (var9 >= 7) {
                    this.treeifyBin(var4, var3);
                }
            }

            ++this.b;
            ++this.a;
        }

        return v;
    }

    public V merge(@NonNull K var1, @Nullable V var2, @NonNull BiFunction<? super V, ? super V, ? extends V> var3) {
        Objects.requireNonNull(var2);
        Objects.requireNonNull(var3);

        {
            int var4 = hash(var1);
            int var8 = 0;
            TreeNode<K, V> var9 = null;
            Node<K, V> var10 = null;
            Node<K, V>[] var5;
            int var6;
            if (this.a > this.c || (var5 = this.table) == null || (var6 = var5.length) == 0) {
                var6 = (var5 = this.resize()).length;
            }

            int var7;
            Node<K, V> var14;
            if ((var14 = var5[var7 = var6 - 1 & var4]) != null) {
                if (var14 instanceof TreeNode) {
                    var10 = (var9 = (TreeNode<K, V>) var14).getTreeNode(var4, var1);
                } else {
                    label68:
                    {
                        Node<K, V> var11 = var14;

                        Object var12;
                        while (var11.hash != var4 || (var12 = var11.key) != var1 && !var1.equals(var12)) {
                            ++var8;
                            if ((var11 = var11.next) == null) {
                                break label68;
                            }
                        }

                        var10 = var11;
                    }
                }
            }

            if (var10 != null) {
                V var13;
                if (var10.value != null) {
                    var13 = var3.apply(var10.value, var2);
                } else {
                    var13 = var2;
                }

                if (var13 != null) {
                    var10.value = var13;
                } else {
                    this.removeNode(var4, var1, null, false, true);
                }

                return var13;
            } else {
                if (var9 != null) {
                    var9.putTreeVal(this, var5, var4, var1, var2);
                } else {
                    var5[var7] = this.newNode(var4, var1, var2, var14);
                    if (var8 >= 7) {
                        this.treeifyBin(var5, var4);
                    }
                }

                ++this.b;
                ++this.a;
                return var2;
            }
        }
    }

    public void forEach(BiConsumer<? super K, ? super V> var1) {
        if (var1 == null) {
            throw new NullPointerException();
        } else {
            if (this.a > 0 && this.table != null) {
                Node<K, V>[] var2 = this.table;
                int var3 = this.b;

                for (Node<K, V> kvNode : var2) {
                    for (Node<K, V> var6 = kvNode; var6 != null; var6 = var6.next) {
                        var1.accept(var6.key, var6.value);
                    }
                }

                if (this.b != var3) {
                    throw new ConcurrentModificationException();
                }
            }

        }
    }

    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> var1) {
        if (var1 == null) {
            throw new NullPointerException();
        } else {
            if (this.a > 0 && this.table != null) {
                Node<K, V>[] var2 = this.table;
                int var3 = this.b;

                for (Node<K, V> kvNode : var2) {
                    for (Node<K, V> var6 = kvNode; var6 != null; var6 = var6.next) {
                        var6.value = var1.apply(var6.key, var6.value);
                    }
                }

                if (this.b != var3) {
                    throw new ConcurrentModificationException();
                }
            }

        }
    }

    public Object clone() {
        UnsafeHashMap<K, V> var1;
        try {
            var1 = (UnsafeHashMap<K, V>) super.clone();
        } catch (CloneNotSupportedException var2) {
            throw new InternalError(var2);
        }

        var1.reinitialize();
        var1.putMapEntries(this, false);
        return var1;
    }

    public final int capacity() {
        if (this.table != null) {
            return this.table.length;
        } else {
            return this.c > 0 ? this.c : DEFAULT_INITIAL_CAPACITY;
        }
    }

    public Node<K, V> newNode(int var1, K var2, V var3, Node<K, V> var4) {
        return new Node<>(var1, var2, var3, var4);
    }

    public Node<K, V> replacementNode(Node<K, V> var1, Node<K, V> var2) {
        return new Node<K, V>(var1.hash, var1.key, var1.value, var2);
    }

    public TreeNode<K, V> newTreeNode(int var1, K var2, V var3, Node<K, V> var4) {
        return new TreeNode<K, V>(var1, var2, var3, var4);
    }

    public TreeNode<K, V> replacementTreeNode(Node<K, V> var1, Node<K, V> var2) {
        return new TreeNode<K, V>(var1.hash, var1.key, var1.value, var2);
    }

    public void reinitialize() {
        this.table = null;
        this.entrySet = null;
        this.keySet = null;
        this.values = null;
        this.b = 0;
        this.c = 0;
        this.a = 0;
    }

    public static class Node<K, V> implements Map.Entry<K, V> {
        public final int hash;
        public final K key;
        public V value;
        public Node<K, V> next;

        public Node(int var1, K var2, V var3, Node<K, V> var4) {
            this.hash = var1;
            this.key = var2;
            this.value = var3;
            this.next = var4;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public String toString() {
            return this.key + "=" + this.value;
        }

        public int hashCode() {
            return Objects.hashCode(this.key) ^ Objects.hashCode(this.value);
        }

        public V setValue(V var1) {
            V var2 = this.value;
            this.value = var1;
            return var2;
        }

        public boolean equals(Object var1) {
            if (var1 == this) {
                return true;
            } else if (var1 instanceof Map.Entry<?, ?> var2) {
                return Objects.equals(this.key, var2.getKey()) && Objects.equals(this.value, var2.getValue());
            } else {
                return false;
            }
        }
    }

    public static final class TreeNode<K, V> extends Entry<K, V> {
        private TreeNode<K, V> b;
        private TreeNode<K, V> c;
        private TreeNode<K, V> d;
        TreeNode<K, V> a;
        private boolean e;
        // $FF: synthetic field
        private static boolean f = !UnsafeHashMap.class.desiredAssertionStatus();

        TreeNode(int var1, K var2, V var3, Node<K, V> var4) {
            super(var1, var2, var3, var4);
        }

        public static <K, V> void moveRootToFront(Node<K, V>[] var0, TreeNode<K, V> var1) {
            int var2;
            if (var1 != null && var0 != null && (var2 = var0.length) > 0) {
                var2 = var2 - 1 & var1.hash;
                TreeNode<K, V> var3 = (TreeNode<K, V>) var0[var2];
                if (var1 != var3) {
                    var0[var2] = var1;
                    TreeNode<K, V> var5 = var1.a;
                    Node<K, V> var4;
                    if ((var4 = var1.next) != null) {
                        ((TreeNode<K, V>) var4).a = var5;
                    }

                    if (var5 != null) {
                        var5.next = var4;
                    }

                    if (var3 != null) {
                        var3.a = var1;
                    }

                    var1.next = var3;
                    var1.a = null;
                }

                if (!f && !checkInvariants(var1)) {
                    throw new AssertionError();
                }
            }

        }

        public static int tieBreakOrder(Object var0, Object var1) {
            int var2;
            if (var0 == null || var1 == null || (var2 = var0.getClass().getName().compareTo(var1.getClass().getName())) == 0) {
                var2 = System.identityHashCode(var0) <= System.identityHashCode(var1) ? -1 : 1;
            }

            return var2;
        }

        public static <K, V> TreeNode<K, V> rotateLeft(TreeNode<K, V> var0, TreeNode<K, V> var1) {
            TreeNode<K, V> var2;
            if (var1 != null && (var2 = var1.d) != null) {
                TreeNode<K, V> var3;
                if ((var3 = var1.d = var2.c) != null) {
                    var3.b = var1;
                }

                if ((var3 = var2.b = var1.b) == null) {
                    var0 = var2;
                    var2.e = false;
                } else if (var3.c == var1) {
                    var3.c = var2;
                } else {
                    var3.d = var2;
                }

                var2.c = var1;
                var1.b = var2;
            }

            return var0;
        }

        public static <K, V> TreeNode<K, V> rotateRight(TreeNode<K, V> var0, TreeNode<K, V> var1) {
            TreeNode<K, V> var2;
            if (var1 != null && (var2 = var1.c) != null) {
                TreeNode<K, V> var3;
                if ((var3 = var1.c = var2.d) != null) {
                    var3.b = var1;
                }

                if ((var3 = var2.b = var1.b) == null) {
                    var0 = var2;
                    var2.e = false;
                } else if (var3.d == var1) {
                    var3.d = var2;
                } else {
                    var3.c = var2;
                }

                var2.d = var1;
                var1.b = var2;
            }

            return var0;
        }

        public static <K, V> TreeNode<K, V> balanceInsertion(TreeNode<K, V> var0, TreeNode<K, V> var1) {
            var1.e = true;

            TreeNode<K, V> var2;
            while ((var2 = var1.b) != null) {
                TreeNode<K, V> var3;
                if (!var2.e || (var3 = var2.b) == null) {
                    return var0;
                }

                TreeNode<K, V> var4;
                if (var2 == (var4 = var3.c)) {
                    if ((var4 = var3.d) != null && var4.e) {
                        var4.e = false;
                        var2.e = false;
                        var3.e = true;
                        var1 = var3;
                    } else {
                        if (var1 == var2.d) {
                            var1 = var2;
                            var0 = rotateLeft(var0, var2);
                            var3 = (var2 = var2.b) == null ? null : var2.b;
                        }

                        if (var2 != null) {
                            var2.e = false;
                            if (var3 != null) {
                                var3.e = true;
                                var0 = rotateRight(var0, var3);
                            }
                        }
                    }
                } else if (var4 != null && var4.e) {
                    var4.e = false;
                    var2.e = false;
                    var3.e = true;
                    var1 = var3;
                } else {
                    if (var1 == var2.c) {
                        var1 = var2;
                        var0 = rotateRight(var0, var2);
                        var3 = (var2 = var2.b) == null ? null : var2.b;
                    }

                    if (var2 != null) {
                        var2.e = false;
                        if (var3 != null) {
                            var3.e = true;
                            var0 = rotateLeft(var0, var3);
                        }
                    }
                }
            }

            var1.e = false;
            return var1;
        }

        public static <K, V> TreeNode<K, V> balanceDeletion(TreeNode<K, V> var0, TreeNode<K, V> var1) {
            while (var1 != null && var1 != var0) {
                TreeNode<K, V> var2;
                if ((var2 = var1.b) == null) {
                    var1.e = false;
                    return var1;
                }

                if (var1.e) {
                    var1.e = false;
                    return var0;
                }

                TreeNode<K, V> var3;
                TreeNode<K, V> var4;
                TreeNode<K, V> var5;
                if ((var3 = var2.c) == var1) {
                    if ((var3 = var2.d) != null && var3.e) {
                        var3.e = false;
                        var2.e = true;
                        var0 = rotateLeft(var0, var2);
                        var3 = (var2 = var1.b) == null ? null : var2.d;
                    }

                    if (var3 == null) {
                        var1 = var2;
                    } else {
                        var4 = var3.c;
                        if ((var5 = var3.d) != null && var5.e || var4 != null && var4.e) {
                            if (var5 == null || !var5.e) {
                                if (var4 != null) {
                                    var4.e = false;
                                }

                                var3.e = true;
                                var0 = rotateRight(var0, var3);
                                var3 = (var2 = var1.b) == null ? null : var2.d;
                            }

                            if (var3 != null) {
                                var3.e = var2 != null && var2.e;
                                if ((var5 = var3.d) != null) {
                                    var5.e = false;
                                }
                            }

                            if (var2 != null) {
                                var2.e = false;
                                var0 = rotateLeft(var0, var2);
                            }

                            var1 = var0;
                        } else {
                            var3.e = true;
                            var1 = var2;
                        }
                    }
                } else {
                    if (var3 != null && var3.e) {
                        var3.e = false;
                        var2.e = true;
                        var0 = rotateRight(var0, var2);
                        var3 = (var2 = var1.b) == null ? null : var2.c;
                    }

                    if (var3 == null) {
                        var1 = var2;
                    } else {
                        var4 = var3.c;
                        var5 = var3.d;
                        if (var4 != null && var4.e || var5 != null && var5.e) {
                            if (var4 == null || !var4.e) {
                                if (var5 != null) {
                                    var5.e = false;
                                }

                                var3.e = true;
                                var0 = rotateLeft(var0, var3);
                                var3 = (var2 = var1.b) == null ? null : var2.c;
                            }

                            if (var3 != null) {
                                var3.e = var2 != null && var2.e;
                                if ((var4 = var3.c) != null) {
                                    var4.e = false;
                                }
                            }

                            if (var2 != null) {
                                var2.e = false;
                                var0 = rotateRight(var0, var2);
                            }

                            var1 = var0;
                        } else {
                            var3.e = true;
                            var1 = var2;
                        }
                    }
                }
            }

            return var0;
        }

        public static <K, V> boolean checkInvariants(TreeNode<K, V> var0) {
            TreeNode<K, V> var1;
            if ((var1 = var0.a) != null && var1.next != var0) {
                return false;
            } else if ((var1 = (TreeNode<K, V>) var0.next) != null && var1.a != var0) {
                return false;
            } else if ((var1 = var0.b) != null && var0 != var1.c && var0 != var1.d) {
                return false;
            } else if ((var1 = var0.c) != null && (var1.b != var0 || var1.hash > var0.hash)) {
                return false;
            } else {
                TreeNode<K, V> var2;
                if ((var2 = var0.d) == null || var2.b == var0 && var2.hash >= var0.hash) {
                    if (var0.e && var1 != null && var1.e && var2 != null && var2.e) {
                        return false;
                    } else if (var1 != null && !checkInvariants(var1)) {
                        return false;
                    } else {
                        return var2 == null || checkInvariants(var2);
                    }
                } else {
                    return false;
                }
            }
        }

        public TreeNode<K, V> root() {
            TreeNode<K, V> var1;
            TreeNode<K, V> var2;
            for (var1 = this; (var2 = var1.b) != null; var1 = var2) {
            }

            return var1;
        }

        public TreeNode<K, V> find(int var1, Object var2, Class<?> var3) {
            TreeNode<K, V> var4 = this;

            do {
                TreeNode<K, V> var6 = var4.c;
                TreeNode<K, V> var7 = var4.d;
                int var5;
                if ((var5 = var4.hash) <= var1) {
                    if (var5 < var1) {
                        var4 = var7;
                        continue;
                    }

                    Object var9;
                    if ((var9 = var4.key) == var2 || var2 != null && var2.equals(var9)) {
                        return var4;
                    }

                    if (var6 == null) {
                        var4 = var7;
                        continue;
                    }

                    if (var7 != null) {
                        int var8;
                        if ((var3 != null || (var3 = UnsafeHashMap.comparableClassFor(var2)) != null) && (var8 = UnsafeHashMap.compareComparables(var3, var2, var9)) != 0) {
                            var4 = var8 < 0 ? var6 : var7;
                            continue;
                        }

                        if ((var4 = var7.find(var1, var2, var3)) != null) {
                            return var4;
                        }
                    }
                }

                var4 = var6;
            } while (var4 != null);

            return null;
        }

        public TreeNode<K, V> getTreeNode(int var1, Object var2) {
            return (this.b != null ? this.root() : this).find(var1, var2, null);
        }

        public void treeify(Node<K, V>[] var1) {
            TreeNode<K, V> var2 = null;

            TreeNode<K, V> var4;
            for (TreeNode<K, V> var3 = this; var3 != null; var3 = var4) {
                var4 = (TreeNode<K, V>) var3.next;
                var3.c = var3.d = null;
                if (var2 == null) {
                    var3.b = null;
                    var3.e = false;
                    var2 = var3;
                } else {
                    Object var5 = var3.key;
                    int var6 = var3.hash;
                    Class<?> var7 = null;
                    TreeNode<K, V> var8 = var2;

                    int var9;
                    TreeNode<K, V> var11;
                    do {
                        Object var10 = var8.key;
                        if ((var9 = var8.hash) > var6) {
                            var9 = -1;
                        } else if (var9 < var6) {
                            var9 = 1;
                        } else if (var7 == null && (var7 = UnsafeHashMap.comparableClassFor(var5)) == null || (var9 = UnsafeHashMap.compareComparables(var7, var5, var10)) == 0) {
                            var9 = tieBreakOrder(var5, var10);
                        }

                        var11 = var8;
                    } while ((var8 = var9 <= 0 ? var8.c : var8.d) != null);

                    var3.b = var11;
                    if (var9 <= 0) {
                        var11.c = var3;
                    } else {
                        var11.d = var3;
                    }

                    var2 = balanceInsertion(var2, var3);
                }
            }

            moveRootToFront(var1, var2);
        }

        public Node<K, V> untreeify(UnsafeHashMap<K, V> var1) {
            Node<K, V> var2 = null;
            Node<K, V> var3 = null;

            for (Node<K, V> var4 = this; var4 != null; var4 = ((Node<K, V>) var4).next) {
                Node<K, V> var5 = var1.replacementNode((Node<K, V>) var4, null);
                if (var3 == null) {
                    var2 = var5;
                } else {
                    var3.next = var5;
                }

                var3 = var5;
            }

            return var2;
        }

        public @Nullable TreeNode<K, V> putTreeVal(@NonNull UnsafeHashMap<K, V> var1, @NonNull UnsafeHashMap.Node<K, V>[] var2, int var3, @NonNull K var4, @Nullable V var5) {
            Class<?> var6 = null;
            boolean var7 = false;
            TreeNode<K, V> var10000 = this.b != null ? this.root() : this;
            TreeNode<K, V> var9 = var10000;

            int var10;
            TreeNode<K, V> var12;
            do {
                if ((var10 = var9.hash) > var3) {
                    var10 = -1;
                } else if (var10 < var3) {
                    var10 = 1;
                } else {
                    Object var11;
                    if ((var11 = var9.key) == var4 || var4.equals(var11)) {
                        return var9;
                    }

                    if (var6 == null && (var6 = UnsafeHashMap.comparableClassFor(var4)) == null || (var10 = UnsafeHashMap.compareComparables(var6, var4, var11)) == 0) {
                        if (!var7) {
                            var7 = true;
                            TreeNode<K, V> var13;
                            if ((var13 = var9.c) != null && (var12 = var13.find(var3, var4, var6)) != null || (var13 = var9.d) != null && (var12 = var13.find(var3, var4, var6)) != null) {
                                return var12;
                            }
                        }

                        var10 = tieBreakOrder(var4, var11);
                    }
                }

                var12 = var9;
            } while ((var9 = var10 <= 0 ? var9.c : var9.d) != null);

            Node<K, V> var15 = var12.next;
            TreeNode<K, V> var14 = var1.newTreeNode(var3, var4, var5, var15);
            if (var10 <= 0) {
                var12.c = var14;
            } else {
                var12.d = var14;
            }

            var12.next = var14;
            var14.b = var14.a = var12;
            if (var15 != null) {
                ((TreeNode<K, V>) var15).a = var14;
            }

            moveRootToFront(var2, balanceInsertion(var10000, var14));
            return null;
        }

        public void removeTreeNode(UnsafeHashMap<K, V> var1, Node<K, V>[] var2, boolean var3) {
            if (var2 != null && var2.length != 0) {
                int var4 = var2.length - 1 & super.hash;
                TreeNode<K, V> var5;
                TreeNode<K, V> var6 = var5 = (TreeNode<K, V>) var2[var4];
                TreeNode<K, V> var7 = (TreeNode<K, V>) super.next;
                TreeNode<K, V> var8;
                if ((var8 = this.a) == null) {
                    var5 = var7;
                    var2[var4] = var7;
                } else {
                    var8.next = var7;
                }

                if (var7 != null) {
                    var7.a = var8;
                }

                if (var5 != null) {
                    if (var6.b != null) {
                        var6 = var6.root();
                    }

                    if (var6 != null && var6.d != null && (var7 = var6.c) != null && var7.c != null) {
                        TreeNode<K, V> var11 = this.c;
                        var5 = this.d;
                        if (var11 != null && var5 != null) {
                            for (var7 = var5; (var8 = var7.c) != null; var7 = var8) {
                            }

                            boolean var12 = var7.e;
                            var7.e = this.e;
                            this.e = var12;
                            var8 = var7.d;
                            TreeNode<K, V> var9 = this.b;
                            if (var7 == var5) {
                                this.b = var7;
                                var7.d = this;
                            } else {
                                TreeNode<K, V> var10 = var7.b;
                                if ((this.b = var10) != null) {
                                    if (var7 == var10.c) {
                                        var10.c = this;
                                    } else {
                                        var10.d = this;
                                    }
                                }

                                if ((var7.d = var5) != null) {
                                    var5.b = var7;
                                }
                            }

                            this.c = null;
                            if ((this.d = var8) != null) {
                                var8.b = this;
                            }

                            if ((var7.c = var11) != null) {
                                var11.b = var7;
                            }

                            if ((var7.b = var9) == null) {
                                var6 = var7;
                            } else if (this == var9.c) {
                                var9.c = var7;
                            } else {
                                var9.d = var7;
                            }

                            if (var8 != null) {
                                var11 = var8;
                            } else {
                                var11 = this;
                            }
                        } else if (var11 != null) {
                        } else if (var5 != null) {
                            var11 = var5;
                        } else {
                            var11 = this;
                        }

                        if (var11 != this) {
                            if ((var7 = var11.b = this.b) == null) {
                                var6 = var11;
                            } else if (this == var7.c) {
                                var7.c = var11;
                            } else {
                                var7.d = var11;
                            }

                            this.c = this.d = this.b = null;
                        }

                        var7 = this.e ? var6 : balanceDeletion(var6, var11);
                        if (var11 == this) {
                            var8 = this.b;
                            this.b = null;
                            if (var8 != null) {
                                if (this == var8.c) {
                                    var8.c = null;
                                } else if (this == var8.d) {
                                    var8.d = null;
                                }
                            }
                        }

                        if (var3) {
                            moveRootToFront(var2, var7);
                        }

                    } else {
                        var2[var4] = var5.untreeify(var1);
                    }
                }
            }
        }

        public void split(UnsafeHashMap<K, V> var1, Node<K, V>[] var2, int var3, int var4) {
            TreeNode<K, V> var6 = null;
            TreeNode<K, V> var7 = null;
            TreeNode<K, V> var8 = null;
            TreeNode<K, V> var9 = null;
            int var10 = 0;
            int var11 = 0;

            TreeNode<K, V> var12;
            for (TreeNode<K, V> var5 = this; var5 != null; var5 = var12) {
                var12 = (TreeNode<K, V>) var5.next;
                var5.next = null;
                if ((var5.hash & var4) == 0) {
                    if ((var5.a = var7) == null) {
                        var6 = var5;
                    } else {
                        var7.next = var5;
                    }

                    var7 = var5;
                    ++var10;
                } else {
                    if ((var5.a = var9) == null) {
                        var8 = var5;
                    } else {
                        var9.next = var5;
                    }

                    var9 = var5;
                    ++var11;
                }
            }

            if (var6 != null) {
                if (var10 <= 6) {
                    var2[var3] = var6.untreeify(var1);
                } else {
                    var2[var3] = var6;
                    if (var8 != null) {
                        var6.treeify(var2);
                    }
                }
            }

            if (var8 != null) {
                if (var11 <= 6) {
                    var2[var3 + var4] = var8.untreeify(var1);
                    return;
                }

                var2[var3 + var4] = var8;
                if (var6 != null) {
                    var8.treeify(var2);
                }
            }

        }
    }

    public final class KeySet extends AbstractSet<K> {
        public KeySet() {
        }

        public int size() {
            return UnsafeHashMap.this.a;
        }

        public void clear() {
            UnsafeHashMap.this.clear();
        }

        public @NotNull Iterator<K> iterator() {
            return UnsafeHashMap.this.new KeyIterator();
        }

        public boolean contains(Object var1) {
            return UnsafeHashMap.this.containsKey(var1);
        }

        public boolean remove(Object var1) {
            return UnsafeHashMap.this.removeNode(UnsafeHashMap.hash(var1), var1, null, false, true) != null;
        }

        public Spliterator<K> spliterator() {
            return new KeySpliterator<>(UnsafeHashMap.this, 0, -1, 0, 0);
        }

        public void forEach(Consumer<? super K> var1) {
            if (var1 == null) {
                throw new NullPointerException();
            } else {
                if (UnsafeHashMap.this.a > 0 && UnsafeHashMap.this.table != null) {
                    Node<K, V>[] var2 = UnsafeHashMap.this.table;
                    int var3 = UnsafeHashMap.this.b;
                    int var4 = (var2).length;

                    for (int var5 = 0; var5 < var4; ++var5) {
                        for (Node<K, V> var6 = var2[var5]; var6 != null; var6 = var6.next) {
                            var1.accept(var6.key);
                        }
                    }

                    if (UnsafeHashMap.this.b != var3) {
                        throw new ConcurrentModificationException();
                    }
                }

            }
        }
    }

    private final class b extends AbstractCollection<V> {
        private b() {
        }

        public int size() {
            return UnsafeHashMap.this.a;
        }

        public void clear() {
            UnsafeHashMap.this.clear();
        }

        @NotNull
        public Iterator<V> iterator() {
            return UnsafeHashMap.this.new ValueIterator();
        }

        public boolean contains(Object var1) {
            return UnsafeHashMap.this.containsValue(var1);
        }

        public Spliterator<V> spliterator() {
            return new ValueSpliterator<>(UnsafeHashMap.this, 0, -1, 0, 0);
        }

        public void forEach(Consumer<? super V> var1) {
            if (var1 == null) {
                throw new NullPointerException();
            } else {
                if (UnsafeHashMap.this.a > 0 && UnsafeHashMap.this.table != null) {
                    Node<K, V>[] var2 = UnsafeHashMap.this.table;
                    int var3 = UnsafeHashMap.this.b;

                    for (Node<K, V> kvNode : var2) {
                        for (Node<K, V> var6 = kvNode; var6 != null; var6 = var6.next) {
                            var1.accept(var6.value);
                        }
                    }

                    if (UnsafeHashMap.this.b != var3) {
                        throw new ConcurrentModificationException();
                    }
                }

            }
        }
    }

    private final class a extends AbstractSet<Map.Entry<K, V>> {
        private a() {
        }

        public int size() {
            return UnsafeHashMap.this.a;
        }

        public void clear() {
            UnsafeHashMap.this.clear();
        }

        @NotNull
        public Iterator<Map.Entry<K, V>> iterator() {
            return UnsafeHashMap.this.new EntryIterator();
        }

        public boolean contains(Object var1) {
            if (!(var1 instanceof Map.Entry<?, ?>)) {
                return false;
            } else {
                Map.Entry<K, V> var3;
                Object var2 = (var3 = (Map.Entry<K, V>) var1).getKey();
                Node<K, V> var4 = UnsafeHashMap.this.getNode(UnsafeHashMap.hash(var2), var2);
                return var3.equals(var4);
            }
        }

        public boolean remove(Object var1) {
            if (var1 instanceof Map.Entry<?, ?>) {
                Map.Entry<K, V> var3;
                Object var2 = (var3 = (Map.Entry<K, V>) var1).getKey();
                var1 = var3.getValue();
                return UnsafeHashMap.this.removeNode(UnsafeHashMap.hash(var2), var2, var1, true, true) != null;
            } else {
                return false;
            }
        }

        public Spliterator<Map.Entry<K, V>> spliterator() {
            return new EntrySpliterator<>(UnsafeHashMap.this, 0, -1, 0, 0);
        }

        public void forEach(Consumer<? super Map.Entry<K, V>> var1) {
            if (var1 == null) {
                throw new NullPointerException();
            } else {
                if (UnsafeHashMap.this.a > 0 && UnsafeHashMap.this.table != null) {
                    Node<K, V>[] var2 = UnsafeHashMap.this.table;
                    int var3 = UnsafeHashMap.this.b;

                    for (Node<K, V> kvNode : var2) {
                        for (Node<K, V> var6 = kvNode; var6 != null; var6 = var6.next) {
                            var1.accept(var6);
                        }
                    }

                    if (UnsafeHashMap.this.b != var3) {
                        throw new ConcurrentModificationException();
                    }
                }

            }
        }
    }

    public final class EntryIterator extends HashIterator implements Iterator<Map.Entry<K, V>> {
        public EntryIterator() {
            super();
        }

        public Map.Entry<K, V> next() {
            return this.nextNode();
        }
    }

    public final class ValueIterator extends HashIterator implements Iterator<V> {
        public ValueIterator() {
            super();
        }

        public V next() {
            return this.nextNode().value;
        }
    }

    public final class KeyIterator extends HashIterator implements Iterator<K> {
        public KeyIterator() {
            super();
        }

        public K next() {
            return this.nextNode().key;
        }
    }

    public abstract class HashIterator {
        public Node<K, V> next;
        public Node<K, V> current;
        public int expectedModCount;
        public int index;

        public HashIterator() {
            this.expectedModCount = UnsafeHashMap.this.b;
            this.current = this.next = null;
            this.index = 0;
            if (UnsafeHashMap.this.table != null && UnsafeHashMap.this.a > 0) {
                Node<K, V>[] var2 = UnsafeHashMap.this.table;

                while (this.index < var2.length && (this.next = var2[this.index++]) == null) {
                }
            }

        }

        public final boolean hasNext() {
            return this.next != null;
        }

        public final Node<K, V> nextNode() {
            if (UnsafeHashMap.this.b != this.expectedModCount) {
                throw new ConcurrentModificationException();
            } else if (this.next == null) {
                throw new NoSuchElementException();
            } else if (UnsafeHashMap.this.table == null) {
                return this.next;
            } else {
                Node<K, V> var1 = this.next;
                if ((this.next = (this.current = var1).next) == null) {
                    Node<K, V>[] var2 = UnsafeHashMap.this.table;

                    while (this.index < var2.length && (this.next = var2[this.index++]) == null) {
                    }
                }

                return var1;
            }
        }

        public final void remove() {
            if (UnsafeHashMap.this.b != this.expectedModCount) {
                throw new ConcurrentModificationException();
            } else if (this.current == null) {
                throw new IllegalStateException();
            } else {
                UnsafeHashMap.this.removeNode(this.current.hash, this.current.key, null, false, false);
                this.expectedModCount = UnsafeHashMap.this.b;
                this.current = null;
            }
        }
    }

    public static class Entry<K, V> extends Node<K, V> {
        Entry(int var1, K var2, V var3, Node<K, V> var4) {
            super(var1, var2, var3, var4);
        }
    }

    public static final class EntrySpliterator<K, V> extends HashMapSpliterator<K, V> implements Spliterator<Map.Entry<K, V>> {
        EntrySpliterator(UnsafeHashMap<K, V> var1, int var2, int var3, int var4, int var5) {
            super(var1, var2, var3, var4, var5);
        }

        public EntrySpliterator<K, V> trySplit() {
            int var1 = this.getFence();
            int var2;
            var1 = (var2 = super.index) + var1 >>> 1;
            return var2 < var1 && super.current == null ? new EntrySpliterator<>(super.map, var2, super.index = var1, super.est >>>= 1, super.expectedModCount) : null;
        }

        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> var1) {
            if (var1 == null) {
                throw new NullPointerException();
            } else {
                UnsafeHashMap<K, V> var5;
                Node<K, V>[] var6 = (var5 = super.map).table;
                int var3;
                int var4;
                if ((var3 = super.fence) < 0) {
                    var4 = super.expectedModCount = var5.b;
                    var3 = super.fence = var6 == null ? 0 : var6.length;
                } else {
                    var4 = super.expectedModCount;
                }

                int var2;
                if (var6 != null && var6.length >= var3 && (var2 = super.index) >= 0 && (var2 < (super.index = var3) || super.current != null)) {
                    Node<K, V> var7 = super.current;
                    super.current = null;

                    do {
                        do {
                            if (var7 == null) {
                                var7 = var6[var2++];
                            } else {
                                var1.accept(var7);
                                var7 = var7.next;
                            }
                        } while (var7 != null);
                    } while (var2 < var3);

                    if (var5.b != var4) {
                        throw new ConcurrentModificationException();
                    }
                }

            }
        }

        public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> var1) {
            if (var1 == null) {
                throw new NullPointerException();
            } else {
                int var2;
                Node<K, V>[] var3;
                if ((var3 = super.map.table) != null && var3.length >= (var2 = this.getFence()) && super.index >= 0) {
                    while (super.current != null || super.index < var2) {
                        if (super.current != null) {
                            Node<K, V> var4 = super.current;
                            super.current = super.current.next;
                            var1.accept(var4);
                            if (super.map.b != super.expectedModCount) {
                                throw new ConcurrentModificationException();
                            }

                            return true;
                        }

                        super.current = var3[super.index++];
                    }
                }

                return false;
            }
        }

        public int characteristics() {
            return (super.fence >= 0 && super.est != super.map.a ? 0 : 64) | 1;
        }
    }

    public static final class ValueSpliterator<K, V> extends HashMapSpliterator<K, V> implements Spliterator<V> {
        ValueSpliterator(UnsafeHashMap<K, V> var1, int var2, int var3, int var4, int var5) {
            super(var1, var2, var3, var4, var5);
        }

        public ValueSpliterator<K, V> trySplit() {
            int var1 = this.getFence();
            int var2;
            var1 = (var2 = super.index) + var1 >>> 1;
            return var2 < var1 && super.current == null ? new ValueSpliterator<>(super.map, var2, super.index = var1, super.est >>>= 1, super.expectedModCount) : null;
        }

        public void forEachRemaining(Consumer<? super V> var1) {
            if (var1 == null) {
                throw new NullPointerException();
            } else {
                UnsafeHashMap<K, V> var5;
                Node<K, V>[] var6 = (var5 = super.map).table;
                int var3;
                int var4;
                if ((var3 = super.fence) < 0) {
                    var4 = super.expectedModCount = var5.b;
                    var3 = super.fence = var6 == null ? 0 : var6.length;
                } else {
                    var4 = super.expectedModCount;
                }

                int var2;
                if (var6 != null && var6.length >= var3 && (var2 = super.index) >= 0 && (var2 < (super.index = var3) || super.current != null)) {
                    Node<K, V> var7 = super.current;
                    super.current = null;

                    do {
                        do {
                            if (var7 == null) {
                                var7 = var6[var2++];
                            } else {
                                var1.accept(var7.value);
                                var7 = var7.next;
                            }
                        } while (var7 != null);
                    } while (var2 < var3);

                    if (var5.b != var4) {
                        throw new ConcurrentModificationException();
                    }
                }

            }
        }

        public boolean tryAdvance(Consumer<? super V> var1) {
            if (var1 == null) {
                throw new NullPointerException();
            } else {
                int var2;
                Node<K, V>[] var3;
                if ((var3 = super.map.table) != null && var3.length >= (var2 = this.getFence()) && super.index >= 0) {
                    while (super.current != null || super.index < var2) {
                        if (super.current != null) {
                            V var4 = super.current.value;
                            super.current = super.current.next;
                            var1.accept(var4);
                            if (super.map.b != super.expectedModCount) {
                                throw new ConcurrentModificationException();
                            }

                            return true;
                        }

                        super.current = var3[super.index++];
                    }
                }

                return false;
            }
        }

        public int characteristics() {
            return super.fence >= 0 && super.est != super.map.a ? 0 : 64;
        }
    }

    public static final class KeySpliterator<K, V> extends HashMapSpliterator<K, V> implements Spliterator<K> {
        KeySpliterator(UnsafeHashMap<K, V> var1, int var2, int var3, int var4, int var5) {
            super(var1, var2, var3, var4, var5);
        }

        public KeySpliterator<K, V> trySplit() {
            int var1 = this.getFence();
            var1 = super.index + var1 >>> 1;
            return super.index < var1 && super.current == null ? new KeySpliterator<>(super.map, super.index, super.index = var1, super.est >>>= 1, super.expectedModCount) : null;
        }

        public void forEachRemaining(Consumer<? super K> var1) {
            if (var1 == null) {
                throw new NullPointerException();
            } else {
                UnsafeHashMap<K, V> var5;
                Node<K, V>[] var6 = (var5 = super.map).table;
                int var3;
                int var4;
                if ((var3 = super.fence) < 0) {
                    var4 = super.expectedModCount = var5.b;
                    var3 = super.fence = var6 == null ? 0 : var6.length;
                } else {
                    var4 = super.expectedModCount;
                }

                int var2;
                if (var6 != null && var6.length >= var3 && (var2 = super.index) >= 0 && (var2 < (super.index = var3) || super.current != null)) {
                    Node<K, V> var7 = super.current;
                    super.current = null;

                    do {
                        do {
                            if (var7 == null) {
                                var7 = var6[var2++];
                            } else {
                                var1.accept(var7.key);
                                var7 = var7.next;
                            }
                        } while (var7 != null);
                    } while (var2 < var3);

                    if (var5.b != var4) {
                        throw new ConcurrentModificationException();
                    }
                }

            }
        }

        public boolean tryAdvance(Consumer<? super K> var1) {
            if (var1 == null) {
                throw new NullPointerException();
            } else {
                int var2;
                Node<K, V>[] var3;
                if ((var3 = super.map.table) != null && var3.length >= (var2 = this.getFence()) && super.index >= 0) {
                    while (super.current != null || super.index < var2) {
                        if (super.current != null) {
                            K var4 = super.current.key;
                            super.current = super.current.next;
                            var1.accept(var4);
                            if (super.map.b != super.expectedModCount) {
                                throw new ConcurrentModificationException();
                            }

                            return true;
                        }

                        super.current = var3[super.index++];
                    }
                }

                return false;
            }
        }

        public int characteristics() {
            return (super.fence >= 0 && super.est != super.map.a ? 0 : 64) | 1;
        }
    }

    public static class HashMapSpliterator<K, V> {
        public final UnsafeHashMap<K, V> map;
        public Node<K, V> current;
        public int index;
        public int fence;
        public int est;
        public int expectedModCount;

        public HashMapSpliterator(UnsafeHashMap<K, V> var1, int var2, int var3, int var4, int var5) {
            this.map = var1;
            this.index = var2;
            this.fence = var3;
            this.est = var4;
            this.expectedModCount = var5;
        }

        public final int getFence() {
            if (this.fence >= 0) {
                return this.fence;
            } else {
                this.est = this.map.a;
                this.expectedModCount = this.map.b;
                Node<K, V>[] var1 = this.map.table;
                return this.fence = var1 == null ? 0 : var1.length;
            }
        }

        public final long estimateSize() {
            this.getFence();
            return this.est;
        }
    }
}

