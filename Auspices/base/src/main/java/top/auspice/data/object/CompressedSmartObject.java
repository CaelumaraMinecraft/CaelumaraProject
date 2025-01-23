package top.auspice.data.object;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Nullable;
import top.auspice.data.compressor.DataCompressor;
import top.auspice.data.database.compressor.CompressorDataProvider;
import top.auspice.data.handlers.abstraction.DataHandler;
import top.auspice.utils.unsafe.Fn;
import top.auspice.utils.unsafe.io.ByteArrayOutputStream;

import static top.auspice.data.object.CompressedSmartObject.Impl.*;

public interface CompressedSmartObject extends SmartObject {
    @Internal
    @Nullable ByteArrayOutputStream getObjectState();

    @Internal
    void setObjectState(@Nullable ByteArrayOutputStream objectState);

    @Internal
    default void invalidateObject() {
        this.setObjectState(c);
    }

    @Internal
    default boolean hasObjectExpired() {
        return this.getObjectState() == c;
    }

    @Internal
    default void ensureObjectExpiration() {
        if (this.hasObjectExpired()) {
            throw new IllegalStateException("This object instance has been unloaded from data but is being used: " + this);
        }
    }

    @Internal
    default DataHandler<?> getDataHandler() {
        throw new UnsupportedOperationException();
    }

    @Internal
    default ByteArrayOutputStream getCompressedData() {
        Class<? extends CompressedSmartObject> var2 = this.getClass();
        int var1;
        if (this.getObjectState() == null) {
            var1 = DataCompressor.REGISTRY.getAvgSize(var2, 100);
        } else {
            var1 = this.getObjectState().size();
        }

        DataCompressor var4 = new DataCompressor(var1);
        this.getDataHandler().save(new CompressorDataProvider(null, var4), Fn.cast(this));
        ByteArrayOutputStream var3 = var4.result();
        var4.registerSize(var2);
        return var3;
    }

    @Internal
    @NonExtendable
    default /*final*/ void saveObjectState(boolean var1) {
        this.ensureObjectExpiration();
        this.ensureObjectExpiration();
        if (this.getObjectState() != a) {
            if (var1) {
                this.setObjectState(b);
            } else if (this.getObjectState() != null) {
                throw new IllegalStateException("Save meta already set " + this);
            } else {
                this.setObjectState(a);
                this.setObjectState(this.getCompressedData());
            }
        }
    }

    @Internal
    @NonExtendable
    default /*final*/ boolean isObjectStateSaved() {
        this.ensureObjectExpiration();
        return this.getObjectState() != null;
    }

    @Internal
    default boolean shouldSave() {
        this.ensureObjectExpiration();
        ByteArrayOutputStream var1 = this.getCompressedData();
        if (this.getObjectState() != null && this.getObjectState() != b && var1.equals(this.getObjectState())) {
            return false;
        } else {
            this.setObjectState(var1);
            return true;
        }
    }

    class Impl implements CompressedSmartObject {
        static final ByteArrayOutputStream a = new ByteArrayOutputStream(0);
        static final ByteArrayOutputStream b = new ByteArrayOutputStream(0);
        static final ByteArrayOutputStream c = new ByteArrayOutputStream(0);
        @Internal
        protected transient @Nullable ByteArrayOutputStream objectState;

        public Impl() {
        }

        @Override
        public @Nullable ByteArrayOutputStream getObjectState() {
            return this.objectState;
        }

        @Override
        public void setObjectState(@Nullable ByteArrayOutputStream objectState) {
            this.objectState = objectState;
        }
    }
}
