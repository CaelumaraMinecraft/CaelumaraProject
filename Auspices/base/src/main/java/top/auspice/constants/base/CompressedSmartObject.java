package top.auspice.constants.base;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;
import top.auspice.data.compressor.DataCompressor;
import top.auspice.data.database.compressor.CompressorDataProvider;
import top.auspice.data.handlers.abstraction.DataHandler;
import top.auspice.utils.internal.Fn;
import top.auspice.utils.internal.io.ByteArrayOutputStream;

public abstract class CompressedSmartObject implements SmartObject {
    private static final ByteArrayOutputStream a = new ByteArrayOutputStream(0);
    private static final ByteArrayOutputStream b = new ByteArrayOutputStream(0);
    private static final ByteArrayOutputStream c = new ByteArrayOutputStream(0);
    @Internal
    protected transient @Nullable ByteArrayOutputStream objectState;

    public CompressedSmartObject() {
    }

    public void invalidateObject() {
        this.objectState = c;
    }

    public boolean hasObjectExpired() {
        return this.objectState == c;
    }

    public void ensureObjectExpiration() {
        if (this.hasObjectExpired()) {
            throw new IllegalStateException("This object instance has been unloaded from data but is being used: " + this);
        }
    }

    protected DataHandler<?> getDataHandler() {
        throw new UnsupportedOperationException();
    }

    public ByteArrayOutputStream getCompressedData() {
        Class var2 = this.getClass();
        int var1;
        if (this.objectState == null) {
            var1 = DataCompressor.REGISTRY.getAvgSize(var2, 100);
        } else {
            var1 = this.objectState.size();
        }

        DataCompressor var4 = new DataCompressor(var1);
        this.getDataHandler().save(new CompressorDataProvider((String)null, var4), Fn.cast(this));
        ByteArrayOutputStream var3 = var4.result();
        var4.registerSize(var2);
        return var3;
    }

    public final void saveObjectState(boolean var1) {
        this.ensureObjectExpiration();
        this.ensureObjectExpiration();
        if (this.objectState != a) {
            if (var1) {
                this.objectState = b;
            } else if (this.objectState != null) {
                throw new IllegalStateException("Save meta already set " + this);
            } else {
                this.objectState = a;
                this.objectState = this.getCompressedData();
            }
        }
    }

    public final boolean isObjectStateSaved() {
        this.ensureObjectExpiration();
        return this.objectState != null;
    }

    public boolean shouldSave() {
        this.ensureObjectExpiration();
        ByteArrayOutputStream var1 = this.getCompressedData();
        if (this.objectState != null && this.objectState != b && var1.equals(this.objectState)) {
            return false;
        } else {
            this.objectState = var1;
            return true;
        }
    }
}
