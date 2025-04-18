package net.aurika.ecliptor.compressor;

import net.aurika.auspice.utils.HierarchalClassMap;
import net.aurika.util.math.Avg;
import net.aurika.util.unsafe.fn.Fn;

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

  public void registerDefaults() {
    register(boolean.class, DataCompressor::compress);
    register(char.class, DataCompressor::compress);
    register(byte.class, DataCompressor::compressByte);
    register(short.class, DataCompressor::compress);
    register(int.class, DataCompressor::compress);
    register(long.class, DataCompressor::compress);
    register(float.class, DataCompressor::compress);
    register(double.class, DataCompressor::compress);
    register(byte[].class, DataCompressor::compress);
    register(ByteBuffer.class, DataCompressor::compress);
    register(String.class, DataCompressor::compress);
    register(UUID.class, DataCompressor::compress);
    register(Color.class, DataCompressor::compress);
    register(Enum.class, DataCompressor::compress);
    register(Map.class, DataCompressor::compress);
    register(Collection.class, DataCompressor::compress);
  }

  public void registerSize(Class<?> var1, int var2) {
    this.b.compute(var1, (var1x, var2x) -> var2x == null ? new Avg(var2, 1L) : var2x.plus(var2));
  }

  public int getAvgSize(Class<?> var1, int var2) {
    return (int) this.b.getOrDefault(var1, new Avg(var2, 1L)).average();
  }

  public <T> void register(Class<T> type, CompressableData<T> var2) {
    this.a.put(type, var2);
  }

  public <D> CompressableData<D> getCompressor(Class<D> type) {
    return (CompressableData<D>) this.a.get(type);
  }

  public void compress(DataCompressor compressor, Object data) {
    if (data == null) {
      compressor.compressNull();
    }

    Class<?> var3 = data.getClass();
    CompressableData<?> var4 = this.a.get(var3);
    if (var4 == null) {
      ReflectedObjectCompressor<?> var5 = ReflectedObjectCompressor.of(var3);
      this.a.put(var3, var5);
      var4 = var5;
    }

    var4.compress(compressor, Fn.cast(data));
  }

}

