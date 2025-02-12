package net.aurika.data.api;

import net.aurika.data.compressor.DataCompressor;
import net.aurika.data.database.compressor.CompressorDataProvider;
import net.aurika.data.handler.DataHandler;
import net.aurika.data.internal.ByteArrayOutputStream;
import net.aurika.utils.unsafe.fn.Fn;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Nullable;

import static net.aurika.data.api.CompressedSmartObject.Impl.*;

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
        Class<? extends CompressedSmartObject> clazz = this.getClass();
        int var1;
        if (this.getObjectState() == null) {
            var1 = DataCompressor.REGISTRY.getAvgSize(clazz, 100);
        } else {
            var1 = this.getObjectState().size();
        }

        DataCompressor var4 = new DataCompressor(var1);
        this.getDataHandler().save(new CompressorDataProvider(null, var4), Fn.cast(this));
        ByteArrayOutputStream var3 = var4.result();
        var4.registerSize(clazz);
        return var3;
    }

    @Internal
    @NonExtendable
    default /*final*/ void saveObjectState(boolean var1) {
        this.ensureObjectExpiration();
        this.ensureObjectExpiration();
        if (this.getObjectState() != a) {
            if (var1) {
                setObjectState(b);
            } else if (this.getObjectState() != null) {
                throw new IllegalStateException("Save meta already set " + this);
            } else {
                setObjectState(a);
                setObjectState(getCompressedData());
            }
        }
    }

    @Internal
    @NonExtendable
    default /*final*/ boolean isObjectStateSaved() {
        this.ensureObjectExpiration();
        return getObjectState() != null;
    }

    @Internal
    default boolean shouldSave() {
        this.ensureObjectExpiration();
        ByteArrayOutputStream compressedData = this.getCompressedData();
        if (this.getObjectState() != null && this.getObjectState() != b && compressedData.equals(this.getObjectState())) {
            return false;
        } else {
            this.setObjectState(compressedData);
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
