package top.auspice.data.compressor;

import top.auspice.utils.unsafe.Fn;
import top.auspice.utils.reflection.Reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ReflectedObjectCompressor<T> implements CompressableData<T> {
    private final Class<T> a;
    private final List<a<?>> b = new ArrayList<>();

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
                    this.b.add(new a<>(var2.unreflectGetter(var3), var7));
                } catch (IllegalAccessException var5) {
                    throw new RuntimeException(var5);
                }
            }
        }

    }

    public static <T> ReflectedObjectCompressor<T> of(Class<T> type) {
        return new ReflectedObjectCompressor<>(type);
    }

    public final void compress(DataCompressor var1, T var2) {
        if (var2.getClass() != this.a) {
            throw new IllegalArgumentException();
        } else {

            for (ReflectedObjectCompressor.a<?> value : this.b) {

                try {
                    Object var5 = value.a.invokeExact(var2);
                    value.b.compress(var1, Fn.cast(var5));
                } catch (Throwable var6) {
                    throw new RuntimeException("Error while compressing reflected object " + var2 + " field " + value.a, var6);
                }
            }

        }
    }

    private static final class a<T> {
        private final MethodHandle a;
        private final CompressableData<T> b;

        private a(MethodHandle var1, CompressableData<T> var2) {
            this.a = var1;
            this.b = var2;
        }
    }


}
