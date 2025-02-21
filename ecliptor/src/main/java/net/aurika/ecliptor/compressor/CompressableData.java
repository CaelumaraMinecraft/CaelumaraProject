package net.aurika.ecliptor.compressor;

public interface CompressableData<T> {
    void compress(DataCompressor compressor, T data);
}
