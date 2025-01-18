package top.auspice.nbt.tag;

import org.jetbrains.annotations.NotNull;
import top.auspice.nbt.stream.NBTStream;

public class NBTTagEnd extends NBTTag<Void> {
    private static final NBTTagEnd INSTANCE = new NBTTagEnd();

    public static NBTTagEnd instance() {
        return INSTANCE;
    }

    private NBTTagEnd() {
    }

    @NotNull
    public NBTTagType<NBTTagEnd> type() {
        return NBTTagType.END;
    }

    public Void value() {
        return null;
    }

    public void setValue(Void value) {
    }

    @NotNull
    public NBTStream stream() {
        return NBTStream.of();
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
