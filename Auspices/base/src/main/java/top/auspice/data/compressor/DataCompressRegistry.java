package top.auspice.data.compressor;

import top.auspice.utils.HierarchalClassMap;
import top.auspice.utils.math.Avg;
import top.auspice.utils.unsafe.Fn;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.UUID;

public class DataCompressRegistry implements CompressableData<Object> {
    private final HierarchalClassMap<CompressableData<?>> a = new HierarchalClassMap<>();
    private final Map<Class<?>, Avg> b = new IdentityHashMap<>();

    public DataCompressRegistry() {
    }

    public final void registerDefaults() {
        this.register(byte.class, DataCompressor::compressByte);
        this.register(boolean.class, DataCompressor::compress);
        this.register(char.class, DataCompressor::compress);
        this.register(short.class, DataCompressor::compress);
        this.register(int.class, DataCompressor::compress);
        this.register(long.class, DataCompressor::compress);
        this.register(float.class, DataCompressor::compress);
        this.register(double.class, DataCompressor::compress);
        this.register(byte[].class, DataCompressor::compress);
        this.register(ByteBuffer.class, DataCompressor::compress);
        this.register(String.class, DataCompressor::compress);
        this.register(UUID.class, DataCompressor::compress);
        this.register(Color.class, DataCompressor::compress);
        this.register(Enum.class, DataCompressor::compress);
        this.register(Map.class, DataCompressor::compress);
        this.register(Collection.class, DataCompressor::compress);
    }

    public final void registerSize(Class<?> var1, int var2) {
        this.b.compute(var1, (var1x, var2x) -> var2x == null ? new Avg(var2, 1L) : var2x.plus(var2));
    }

    public final int getAvgSize(Class<?> var1, int var2) {
        return (int) this.b.getOrDefault(var1, new Avg(var2, 1L)).getAverage();
    }

    public final <T> void register(Class<T> type, CompressableData<T> var2) {
        this.a.put(type, var2);
    }

    public final <D> CompressableData<D> getCompressor(Class<D> type) {
        return (CompressableData<D>) this.a.get(type);
    }

    public final void compress(DataCompressor var1, Object var2) {
        if (var2 == null) {
            var1.compressNull();
        }

        Class<?> var3 = var2.getClass();
        CompressableData<?> var4 = this.a.get(var3);
        if (var4 == null) {
            ReflectedObjectCompressor<?> var5 = ReflectedObjectCompressor.of(var3);
            this.a.put(var3, var5);
            var4 = var5;
        }

        var4.compress(var1, Fn.cast(var2));
    }
}

