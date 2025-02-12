package net.aurika.data.compressor;

import net.aurika.util.reflection.Reflect;
import net.aurika.util.unsafe.fn.Fn;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ReflectedObjectCompressor<T> implements CompressableData<T> {
    private final Class<T> a;
    private final List<Compress<?>> b = new ArrayList<>();

    private ReflectedObjectCompressor(Class<T> var1) {
        this.a = var1;
        MethodHandles.Lookup var2 = MethodHandles.lookup();

        for (Field field : Reflect.getFields(var1)) {
            Field var3;
            int var4;
            if (!Modifier.isTransient(var4 = (var3 = field).getModifiers()) && !Modifier.isStatic(var4)) {
                var3.setAccessible(true);

                try {
                    CompressableData<?> var7 = DataCompressor.REGISTRY.getCompressor(var3.getType());
                    this.b.add(new Compress<>(var2.unreflectGetter(var3), var7));
                } catch (IllegalAccessException var5) {
                    throw new RuntimeException(var5);
                }
            }
        }
    }

    public static <T> ReflectedObjectCompressor<T> of(Class<T> type) {
        return new ReflectedObjectCompressor<>(type);
    }

    public final void compress(DataCompressor compressor, T data) {
        if (data.getClass() != this.a) {
            throw new IllegalArgumentException();
        } else {
            for (Compress<?> value : this.b) {
                try {
                    Object var5 = value.handle.invokeExact(data);
                    value.data.compress(compressor, Fn.cast(var5));
                } catch (Throwable var6) {
                    throw new RuntimeException("Error while compressing reflected object " + data + " field " + value.handle, var6);
                }
            }
        }
    }

    private static final class Compress<T> {
        private final MethodHandle handle;
        private final CompressableData<T> data;

        private Compress(MethodHandle var1, CompressableData<T> var2) {
            this.handle = var1;
            this.data = var2;
        }
    }
}
