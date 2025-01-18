package top.auspice.utils.internal.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class ByteArrayOutputStream extends OutputStream {
    private int size;
    private byte[] buf;
    private static final int SOFT_MAX_ARRAY_LENGTH = 2147483639;

    public ByteArrayOutputStream(int size) {
        this.buf = new byte[size];
    }

    private static int newLength(int oldLength, int minGrowth, int prefGrowth) {
        int prefLength = oldLength + Math.max(minGrowth, prefGrowth);
        if (0 < prefLength && prefLength <= 2147483639) {
            return prefLength;
        } else {
            int minLength = oldLength + minGrowth;
            if (minLength < 0) {
                throw new OutOfMemoryError("Required array length " + oldLength + " + " + minGrowth + " is too large");
            } else {
                return Math.max(minLength, 2147483639);
            }
        }
    }

    public void ensureCapacity(int minCapacity) {
        int oldCapacity = this.buf.length;
        int minGrowth = minCapacity - oldCapacity;
        if (minGrowth > 0) {
            this.buf = Arrays.copyOf(this.buf, newLength(oldCapacity, minGrowth, oldCapacity));
        }

    }

    public void write(int b) {
        this.ensureCapacity(this.size + 1);
        this.buf[this.size] = (byte)b;
        ++this.size;
    }

    public void write(byte[] b, int off, int len) {
        this.ensureCapacity(this.size + len);
        System.arraycopy(b, off, this.buf, this.size, len);
        this.size += len;
    }

    public void writeBytes(byte[] b) {
        this.write(b, 0, b.length);
    }

    public int size() {
        return this.size;
    }

    public String toString() {
        return "ByteArrayOutputStream(" + this.size + '/' + this.buf.length + ')';
    }

    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    public byte[] getArray() {
        return this.buf.length == this.size ? this.buf : Arrays.copyOf(this.buf, this.size);
    }

    public boolean equals(ByteArrayOutputStream obj) {
        if (this.size != obj.size) {
            return false;
        } else if (this == obj) {
            return true;
        } else {
            for(int i = 0; i < this.size; ++i) {
                if (this.buf[i] != obj.buf[i]) {
                    return false;
                }
            }

            return true;
        }
    }

    public void close() throws IOException {
    }
}
