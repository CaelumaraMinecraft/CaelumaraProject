package net.aurika.ecliptor.api;

import net.aurika.ecliptor.internal.ByteArrayOutputStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DataObject extends CompressedSmartObject {
    class Impl extends CompressedSmartObject.Impl implements DataObject {
    }

    interface WrapperImpl extends DataObject {

        @NotNull DataObject getWrapped();

        @Override
        default @Nullable ByteArrayOutputStream getObjectState() {
            return this.getWrapped().getObjectState();
        }

        @Override
        default void setObjectState(@Nullable ByteArrayOutputStream objectState) {
            this.getWrapped().setObjectState(objectState);
        }
    }
}
