package net.aurika.data.compressor;

public interface CompressableData<T> {
    void compress(DataCompressor compressor, T data);
}
